package servlets;

import models.Point;
import models.Points;

import com.google.gson.Gson;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("areaCheck")
public class AreaCheckServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Point point = buildPoint(req);
            Points points = (Points) req.getSession().getAttribute("points");
            if (points == null) {
                points = new Points();
                req.getSession().setAttribute("points", points);
            }
            points.getPoints().add(point);

            sendResponse(point, req, resp);
        } catch (NumberFormatException e) {
            resp.setStatus(403);
        } catch (IOException e) {
            resp.setStatus(500);
        }
    }

    private void sendResponse(Point point, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if("submit".equals(req.getParameter("action"))) {
            RequestDispatcher dispatcher = req.getRequestDispatcher("./results.jsp");
            dispatcher.forward(req, resp );
        } else {
            Gson gson = new Gson();
            Map<String, Object> json = new HashMap<>();
            json.put("x", point.getX());
            json.put("y", point.getY());
            json.put("r", point.getR());
            json.put("result", point.isInArea());

            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(json));
        }
    }

    protected Point buildPoint(HttpServletRequest req) throws NumberFormatException {
        int x = Integer.parseInt(req.getParameter("x"));
        double y = Double.parseDouble(req.getParameter("y"));
        double r = Double.parseDouble(req.getParameter("r"));
        return new Point(x, y, r);
    }
}
