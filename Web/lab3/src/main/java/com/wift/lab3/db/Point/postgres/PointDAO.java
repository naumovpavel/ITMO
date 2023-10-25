package com.wift.lab3.db.Point.postgres;

import com.wift.lab3.model.PointBean;
import org.postgresql.ds.PGConnectionPoolDataSource;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PointDAO implements com.wift.lab3.db.Point.PointDAO {
    private PGConnectionPoolDataSource pool;
    private String name;
    private String password;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        pool.setUser(name);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        pool.setPassword(password);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        pool.setURL(url);
    }

    public PointDAO() {
        pool = new PGConnectionPoolDataSource();

    }

    @Override
    public List<PointBean> getAllPoints() {
        try(java.sql.Connection connection = pool.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select * from points");
            ResultSet resultSet = statement.executeQuery();
            List<PointBean> results = new ArrayList<>();
            while (resultSet.next()) {
                PointBean point = new PointBean();
                point.setX(resultSet.getDouble("x"));
                point.setY(resultSet.getDouble("y"));
                point.setR(resultSet.getInt("r"));
                point.setInArea(resultSet.getBoolean("is_in_area"));
                results.add(point);
            }
            return results;
        }  catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    @Override
    public void addPoint(PointBean point) {

        try(java.sql.Connection connection = pool.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("insert into points (x, y, r, is_in_area) values(?,?,?,?)");
            statement.setDouble(1, point.getX());
            statement.setDouble(2, point.getY());
            statement.setInt(3, point.getR());
            statement.setBoolean(4, point.isInArea());
            statement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        }
    }
}
