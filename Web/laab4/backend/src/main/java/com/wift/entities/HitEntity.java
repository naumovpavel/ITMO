package com.wift.entities;

import com.wift.dto.HitResult;
import lombok.*;

import javax.persistence.*;



@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hits_results")
public class HitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private Integer id;
    private Long ownerId;

    private double x;
    private double y;
    private double r;
    private String creationTime;
    private boolean result;

    private boolean removed = false;

    public HitEntity(Long ownerId, HitResult hitResult) {
        this.ownerId = ownerId;
        this.x = hitResult.getX();
        this.y = hitResult.getY();
        this.r = hitResult.getR();
        this.creationTime = hitResult.getCreationTime();
        this.result = hitResult.isResult();
    }

    public HitResult toHitResult() {
        return new HitResult(x, y, r, creationTime, result);
    }
}
