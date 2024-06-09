package db;


import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;



@Getter
@Setter
@ToString
@NoArgsConstructor
@Named("point")
@SessionScoped
public class Point implements Serializable {

    public Point(float x, float y, int r, boolean isHit) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = isHit;
    }
    public Point(float x, float y, int r) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = isHit();
    }

    @NotNull
    private float x;

    @NotNull
    private float y;

    @NotNull
    private int r;
    @NotNull
    private boolean result;

    public float getX() {
        return x;
    }
    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y){
        this.y = y;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public boolean isHit() {
        return checkSquare() || checkCircle() || checkTriangle();
    }

    private boolean checkTriangle() {
        return 0 <= x && x <= (float) r / 2 && (float) -r / 2 <= y && y <= 0 && Math.abs(y) + x <= (float) r / 2;
    }

    private boolean checkCircle() {
        return x * x + y * y <= r * r && -r <= x && x <= 0 && 0 >= y && y >= -r;
    }

    private boolean checkSquare() {
        return -r <= x && x <= 0 && 0 <= y && y <= r;
    }
}
