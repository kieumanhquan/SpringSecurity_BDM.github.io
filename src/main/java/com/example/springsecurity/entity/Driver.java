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
public class Driver implements Serializable {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "driver5_seq")
    @SequenceGenerator(name = "driver5_seq", sequenceName = "driver5_seq", allocationSize = 1, initialValue = 10000)
    private int id;

    @Column(name = "fullname")
    private String fullName;

    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "drive_level")
    private String driveLevel;

}
