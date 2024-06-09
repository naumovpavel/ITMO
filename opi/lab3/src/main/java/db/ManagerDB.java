package db;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import org.primefaces.PrimeFaces;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Named("manager")
@SessionScoped
public class ManagerDB implements Serializable {
    private static List<Point> points = new ArrayList<>();
    @Transactional
    public void addPointToTable(Point point) {
        points.add(point);
    }
    @Transactional
    public void addPointToTableFromJSON() {
        final Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        try {
            var x = Float.parseFloat(params.get("X"));
            var y = Float.parseFloat(params.get("Y"));
            var graphR = Integer.parseInt(params.get("R"));

            final Point point = new Point(
                    x,
                    y,
                    graphR
            );
            addPointToTable(point);
            PrimeFaces.current().ajax().addCallbackParam("isHit", point.isHit());

        } catch (IllegalArgumentException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public List<Point> getPoints() {
        return points;
    }

    public int getPointsCount() {
        return points.size();
    }


}
