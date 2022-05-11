package com.codewithpp.JavaDriver.entity;

import com.codewithpp.JavaDriver.otp.OtpDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @GeneratedValue(generator = "uuid2")
//    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
//    @Column(name = "id", columnDefinition = "VARCHAR(255)")
    private int id;

    private String name;
    private String phoneNumber;
    private String location;
    private String userName;
    private String password;
    private String email;
}
