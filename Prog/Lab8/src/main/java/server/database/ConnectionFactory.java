package server.database;

import org.postgresql.ds.PGConnectionPoolDataSource;
import server.main.Main;

import java.io.FileInputStream;
import org.postgresql.ds.PGPoolingDataSource;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ConnectionFactory {
    private static String url;
    private static String name;
    private static String password;
    private static PGConnectionPoolDataSource pool;


    public static void init(String config) {
        Properties info = new Properties();
        try {
            info.load(new FileInputStream(config));
        } catch (IOException e) {
            Main.logger.warn("Не найден конфигурационный файл");
        }
        name = info.getProperty("name");
        password = info.getProperty("password");
        url = info.getProperty("url");
        pool = new PGConnectionPoolDataSource();
        pool.setUser(name);
        pool.setPassword(password);
        pool.setURL(url);
        buildSchema(info.getProperty("schema"));
    }



    private static void buildSchema(String schema) {
        try(var connection = getConnection()) {
            FileReader reader = new FileReader(schema, StandardCharsets.UTF_8);

            StringBuilder query = new StringBuilder();
            CharBuffer buf = CharBuffer.allocate(1024);
            while (reader.read(buf) != -1) {
                buf.flip();
                query.append(buf);
                buf.clear();
            }

            Statement statement = connection.createStatement();
            statement.execute(query.toString());

            reader.close();
        } catch (SQLException e) {
            Main.logger.warn(e.getMessage());
            Main.logger.warn("Ошибка получения соединения или исполнения запроса при загрузке схемы");

        } catch (IOException e) {
            Main.logger.warn(e.getMessage());
            Main.logger.warn("Ошибка при чтении файла схемы");
        }
    }

    public static Connection getConnection() throws SQLException {
        return pool.getConnection();
        //return DriverManager.getConnection(url, name, password);
    }
}
