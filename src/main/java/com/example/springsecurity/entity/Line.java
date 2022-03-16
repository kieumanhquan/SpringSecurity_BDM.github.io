package com.example.springsecurity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Line implements Serializable {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "line5_seq")
    @SequenceGenerator(name = "line5_seq", sequenceName = "line5_seq", allocationSize = 1, initialValue = 100)
    private int id;

    private float distance;

    @Column(name = "stop_point")
    private int stopPoint;

}
