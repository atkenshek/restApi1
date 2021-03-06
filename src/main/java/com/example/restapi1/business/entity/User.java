package com.example.restapi1.business.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user")
@Table(name = "users")
public class User {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    private String fullName;
    private String gender;
    private String phoneNumber;
    private String email;
    private String country;
    private String city;
    private String ISP;

    public User(String fullName, String gender, String phoneNumber, String email, String country, String city, String ISP) {
        this.fullName = fullName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.country = country;
        this.city = city;
        this.ISP = ISP;
    }

    public User(String fullName, String gender, String phoneNumber, String email) {
        this.fullName = fullName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public User(String fullName, String gender) {
        this.fullName = fullName;
        this.gender = gender;
    }

    public User(Long id, String fullName, String gender, String phoneNumber, String email) {
        this.id = id;
        this.fullName = fullName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
