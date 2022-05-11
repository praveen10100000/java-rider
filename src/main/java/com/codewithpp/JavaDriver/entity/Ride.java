package com.codewithpp.JavaDriver.entity;

import com.codewithpp.JavaDriver.otp.OtpDetails;
import com.codewithpp.JavaDriver.util.RideStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String source;
    private String destination;
    private double fair;
    private int userId;
    private RideStatus status = RideStatus.Initiate;
    private double distance;
//    private OtpDetails

    @ManyToOne
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    private Driver driver;

}