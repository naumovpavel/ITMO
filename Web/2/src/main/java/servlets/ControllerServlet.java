package servlets;

import exceptions.EmptyFieldException;
import exceptions.ParseException;
import exceptions.UnallowedValueException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

@WebServlet("/controller")
public class ControllerServlet extends HttpServlet {
    private static final String emptyError = "параметр не может быть пустым";
    private static final double EPS = 1e-6;


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            validate(request.getParameter("x"),
                    request.getParameter("y"),
                    request.getParameter("r"));
            response.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
            response.setHeader("Location", "./areaCheck");
        } catch (Exception e) {
            sendError(response, e.toString());
        }
    }

    private void sendError(HttpServletResponse response, String message) {
        try {
            response.setStatus(422);
            response.getWriter().write(message);
        } catch (IOException e) {
            response.setStatus(500);
        }
    }

    protected boolean validate(String x, String y, String r) throws EmptyFieldException, UnallowedValueException, ParseException {
        validateX(x);
        validateY(y);
        validateR(r);
        return true;
    }

    protected boolean validateX(String x) throws EmptyFieldException, UnallowedValueException, ParseException {
        if (x == null || x.isEmpty()) {
            throw new EmptyFieldException("x");
        }
        try {
            int xValue = Integer.parseInt(x);
            List<Integer> values = Arrays.asList(-4, -3, -2, -1, 0, 1, 2, 3, 4);
            if (values.contains(xValue)) {
                return true;
            }
            throw new UnallowedValueException("x", x);
        } catch (NumberFormatException e) {
            throw new ParseException("x", x);
        }
    }

    protected boolean validateY(String y) throws EmptyFieldException, UnallowedValueException, ParseException {
        if (y == null || y.isEmpty()) {
            throw new EmptyFieldException("y");
        }
        try {
            double yValue = Double.parseDouble(y);
            if (yValue - 5.0 < EPS && yValue + 5.0 > -EPS) {
                return true;
            }
            throw new UnallowedValueException("y", y);
        } catch (NumberFormatException e) {
            throw new ParseException("y", y);
        }
    }

    protected boolean validateR(String r) throws EmptyFieldException, UnallowedValueException, ParseException {
        if (r == null || r.isEmpty()) {
            throw new EmptyFieldException("r");
        }
        try {
            double rValue = Double.parseDouble(r);
            List<Double> values = Arrays.asList(1.0, 1.5, 2.0, 2.5, 3.0);
            if (values.contains(rValue)) {
                return true;
            }
            throw new UnallowedValueException("r", r);
        } catch (NumberFormatException e) {
            throw new ParseException("r", r);
        }
    }
}