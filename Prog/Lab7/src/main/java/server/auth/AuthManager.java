package server.auth;

import client.network.TcpClient;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import common.models.User;
import org.apache.commons.lang3.RandomStringUtils;
import server.database.ConnectionFactory;
import server.main.Main;
import server.network.Server;
import server.network.TcpServer;

import java.net.InetAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.Instant;
import java.time.Month;
import java.time.MonthDay;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentHashMap;

public class AuthManager {
    private final ConcurrentHashMap<SocketAddress, User> sessions = new ConcurrentHashMap<>();
    private final String pepper = "@^O0!*g";
    private final int SALT_LENGTH = 10;
    private final static String insertUser = "INSERT INTO Users (name, salt, passwordHash) values (?, ?, ?);";
    private final static String selectUser = "SELECT * FROM Users WHERE name = ?;";
    private final static String secret = "FDrecsrfer93p4jrelrmlFD;/o-=2";

    public AuthManager() {
    }

    public String registerUser(SocketAddress address,  String name, String password) throws RegisterException {
        User user = new User();
        HashMap<String, Object> values = new HashMap<>();
        String salt = generateSalt();
        values.put("salt", salt);
        byte[] passwordHash = generateHash(password, salt);
        values.put("passwordHash", passwordHash);
        try (var connection = ConnectionFactory.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(insertUser, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, name);
            statement.setString(2, salt);
            statement.setBytes(3, passwordHash);
            try {
                statement.execute();
                connection.commit();
            } catch (SQLException e) {
                throw new RegisterException("Пользователь с таким именем уже зарегистрирован", address);
            }

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            values.put("id", resultSet.getInt("id"));

            user.init(values);
            return creatJwt(user);
        } catch (SQLException e) {
            Main.logger.warn("ошибка при регистрации пользователя {}", name);
            throw new RegisterException("Ошибка при регистрации пользователя", address);
        }
    }

    public String loginUser(SocketAddress address, String name, String password) throws LoginException {
        var user = findUserInDB(address, name, password);
        return creatJwt(user);
    }

    public void logoutUser(SocketAddress address) {
        sessions.remove(address);
    }

    private User findUserInDB(SocketAddress address, String name, String password) throws LoginException {
        User user = new User();
        HashMap<String, Object> values = new HashMap<>();

        try (var connection = ConnectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(selectUser);
            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()) {
                throw new LoginException("Пользователь с таким именем не найден", address);
            }

            String salt = resultSet.getString("salt");
            byte[] passwordHash = resultSet.getBytes("passwordHash");
            if(!Arrays.equals(passwordHash, generateHash(password, salt))) {
                throw new LoginException("Введен неверный пароль", address);
            }
            values.put("id", resultSet.getInt("id"));
            values.put("passwordHash", passwordHash);
            values.put("salt", salt);

            user.init(values);
            Main.logger.info("пользователь с именем {} успешно авторизовался", name);
            return user;
        } catch (SQLException e) {
            Main.logger.warn("ошибка при регистрации пользователя с именем {} и адресом {}", name, address);
            throw new LoginException("Ошибка при авторизации пользователя", address);
        }
    }

    public String creatJwt(User user) {
        int month = 30*24*60*60;
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("auth0")
                    .withClaim("id", user.getId())
                    .withExpiresAt(Date.from(Instant.now().plusSeconds(month)))
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            return "";
            // Invalid Signing configuration / Couldn't convert Claims.
        }
    }

    public int verifyJwt(String token) {
        DecodedJWT decodedJWT;
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    // specify an specific claim validations
                    .withIssuer("auth0")
                    // reusable verifier instance
                    .build();
            decodedJWT = verifier.verify(token);
            Date expiresDate = decodedJWT.getExpiresAt();
            if(expiresDate.compareTo(Date.from(Instant.now())) < 0) {
                throw new JWTVerificationException("bad expires date");
            }
            return decodedJWT.getClaim("id").asInt();
        } catch (JWTVerificationException exception){
            return -1;
        }
    }

    public User findUserInSessions(SocketAddress address) {
        if(!sessions.containsKey(address)) {
            return null;
        }
        return sessions.get(address);
    }

    private String generateSalt() {
        return RandomStringUtils.randomAlphanumeric(SALT_LENGTH);
    }

    private byte[] generateHash(String password, String salt) {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-224");
            return sha.digest((pepper + password + salt).getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
