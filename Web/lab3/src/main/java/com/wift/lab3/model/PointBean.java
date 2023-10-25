package com.wift.lab3.model;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PointBean implements Serializable {
    private double x;
    private double y;
    private int r;
    private boolean isInArea;
    private List<Integer> selectedR;

    public PointBean() {
        selectedR = new ArrayList<>();
        selectedR.add(1);
    }

    public boolean isInArea() {
        if(x < 0 && y < 0) {
            return -2*x - y <= r && 2*x >= -r && y >= -r;
        } else if (x>= 0 && y >= 0) {
            return 4*(x*x + y*y) <= r;
        } else if (x>=0 && y <= 0) {
            return x <= r && 2*y >= -r;
        }
        return false;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public List<Integer> getSelectedR() {
        return selectedR;
    }

    public void setSelectedR(List<Integer> selectedR) {
        this.selectedR = selectedR;
        if(selectedR == null) {
            this.selectedR = new ArrayList<>();
            this.selectedR.add(1);
        }
        this.r = selectedR.get(0);
    }

    public void setInArea(boolean inArea) {
        isInArea = inArea;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointBean pointBean = (PointBean) o;
        return Double.compare(pointBean.x, x) == 0 && Double.compare(pointBean.y, y) == 0 && r == pointBean.r && isInArea == pointBean.isInArea && Objects.equals(selectedR, pointBean.selectedR);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, r, isInArea, selectedR);
    }

    @Override
    public String toString() {
        return "PointBean{" +
                "x=" + x +
                ", y=" + y +
                ", r=" + r +
                ", isInArea=" + isInArea +
                ", selectedX=" + selectedR +
                '}';
    }
}
