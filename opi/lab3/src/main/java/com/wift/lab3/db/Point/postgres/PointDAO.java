package com.wift.lab3.db.Point.postgres;

import com.wift.lab3.model.PointBean;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PointDAO implements com.wift.lab3.db.Point.PointDAO {
    private List<PointBean> points;

    public PointDAO() {
        points = new LinkedList<>();
    }

    @Override
    public List<PointBean> getAllPoints(String sessionId) {
        return points;
    }

    @Override
    public void addPoint(PointBean point) {
        this.points.add(point);
    }
}
