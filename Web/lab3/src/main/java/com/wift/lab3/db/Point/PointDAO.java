package com.wift.lab3.db.Point;

import com.wift.lab3.model.PointBean;

import java.sql.SQLException;
import java.util.List;

public interface PointDAO {
    List<PointBean> getAllPoints();
    void addPoint(PointBean point);
}
