package com.wift.lab3.model;


import com.wift.lab3.db.Point.PointDAO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultsBean implements Serializable {
    private List<PointBean> results;
    private PointDAO dao;

    public PointDAO getDao() {
        return dao;
    }

    public void setDao(PointDAO dao) {
        this.dao = dao;
    }

    public ResultsBean() {
        results = new ArrayList<>();
    }

    public List<PointBean> getResults() {
        results = dao.getAllPoints();
        return results;
    }

    public void addPoint(PointBean point) {
        dao.addPoint(point);
    }

    public void setResults(List<PointBean> results) {
        this.results = results;
    }
}
