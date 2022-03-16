package com.example.springsecurity.registration;

import lombok.*;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest implements Serializable {
    private  String firstName;
    private  String lastName;
    private  String email;
    private  String password;
}