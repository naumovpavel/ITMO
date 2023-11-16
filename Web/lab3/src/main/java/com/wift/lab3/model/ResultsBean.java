package com.wift.lab3.model;


import com.wift.lab3.db.Point.PointDAO;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

        results = dao.getAllPoints(getSessionId());
        return results;
    }

    private String getSessionId() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        // Get the ExternalContext
        ExternalContext externalContext = facesContext.getExternalContext();

        // Get the HttpServletRequest
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

        // Get the HttpSession
        HttpSession session = request.getSession();

        return session.getId();
    }

    public void addPoint(PointBean point) {
        dao.addPoint(point);
    }

    public void setResults(List<PointBean> results) {
        this.results = results;
    }
}
