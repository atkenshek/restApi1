package com.example.restapi1.Business.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "user")
@Table()
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
}
