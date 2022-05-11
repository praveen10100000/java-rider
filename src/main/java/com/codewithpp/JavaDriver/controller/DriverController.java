package com.codewithpp.JavaDriver.controller;

import com.codewithpp.JavaDriver.entity.Driver;
import com.codewithpp.JavaDriver.entity.Ride;
import com.codewithpp.JavaDriver.otp.OtpDetails;
import com.codewithpp.JavaDriver.repository.DriverRepo;
import com.codewithpp.JavaDriver.repository.RideRepo;
import com.codewithpp.JavaDriver.service.Service;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@Slf4j
public class DriverController {

    @Autowired
    private DriverRepo driverRepo;

    @Autowired
    private Service service;

    @Autowired
    private RideRepo rideRepo;

    public static Map<String, Object> otpInCache = new HashMap<>();


    @RequestMapping()
    public String normalMessage() {
        return "welcome";
    }

    @PostMapping("/register")
    public String registerAsCaptain(@RequestBody Driver driver) throws IOException {
        log.info(driver.toString());
        if (driverRepo.existsByPhoneNumber(driver.getPhoneNumber()) == true) return "PhoneNumber is Already Exists";
        else {
            OtpDetails otpDetails = new OtpDetails();
            Driver driver1 = driverRepo.save(driver);
            otpDetails.setOtp(service.generateOtp());
            otpInCache.put(String.valueOf(driver1.getId()), otpDetails);

            ExecutorService executor = Executors.newCachedThreadPool();
            executor.submit(() -> {
                sendOtp(String.valueOf(otpDetails.getOtp()), driver1.getName(), driver1.getEmail());
            });
            executor.shutdown();
        }
        return "Register Successfully";
    }


    @GetMapping("/getData")
    public Map<?, ?> getdata() {
        return otpInCache;
    }

    @PostMapping("/loginAsCaptain")
    public String loginAsCaptain(@RequestBody Driver driver) throws IOException {
        if (driverRepo.existsByUserName(driver.getUserName()) == true && driverRepo.existsByPassword(driver.getPassword()) == true) {
            OtpDetails otpDetails = new OtpDetails();
            Driver driver1 = driverRepo.findByUserName(driver.getUserName());
            otpDetails.setOtp(service.generateOtp());

            otpDetails.setLoggedIn(new Random().nextBoolean());//ramndom loggin status
            otpInCache.put(String.valueOf(driver1.getId()), otpDetails);
            ExecutorService executor = Executors.newCachedThreadPool();
            executor.submit(() -> {
                sendOtp(String.valueOf(otpDetails.getOtp()), driver1.getName(), driver1.getEmail());
            });
            executor.shutdown();
            return "Login Successfully";
        } else {
            return "Invalid Credential";
        }


    }

    @GetMapping("/getLoginData")
    public List<?> getLoginData() {
        return null;
    }

    @PostMapping("/bookRide")
    public ResponseEntity<?> bookRide(@RequestBody Ride ride) {
        double distance = CalculateDistance(ride.getSource(), ride.getDestination());
        if (distance < 3) {
            return ResponseEntity.ok("Minimum 3km ride is required");
        } else {
            ride.setDistance(distance);
            ride.setFair(fairAmount(distance));
             Ride savedRide = rideRepo.save(ride);
            return ResponseEntity.ok(savedRide);
        }
    }

    public void sendOtp(String otp, String name, String email) {
        FileReader fr = null;
        try {
            fr = new FileReader("C:\\Users\\HashStudioz\\Downloads\\JavaDriver\\JavaDriver\\src\\main" + "\\java\\com\\codewithpp\\JavaDriver\\util\\emailTemplate.html");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        BufferedReader br = new BufferedReader(fr);  //creates a buffering character input stream
        StringBuffer sb = new StringBuffer();    //constructs a string buffer with no characters
        String line;
        while (true) {
            try {
                if (!((line = br.readLine()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            sb.append(line);      //appends line to string buffer
            sb.append("\n");     //line feed
        }
        try {
            fr.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int i = sb.indexOf("{{otp}}");
        sb.replace(i, i + 7, otp);
        i = sb.indexOf("{{name}}");
        sb.replace(i, i + 8, name);
        String content = sb.toString();
//        content = content.replace("{{otp}}", otp);
//        content = content.replace("{{name}}", name);
        service.sendMail("your OTP is here", "praveenpandey.uttarakhand@gmail.com", email, content);
    }

    public double CalculateDistance(String source ,String destination) {

        Double sLat = Double.parseDouble(source.split(",")[0]);
        Double sLong = Double.parseDouble(source.split(",")[1]);
        Double dLat  = Double.parseDouble(destination.split(",")[0]);
        Double dLong = Double.parseDouble(destination.split(",")[1]);
        final double earthRadius = 6371e3;
        final double A = sLat * Math.PI / 180;
        final double B = dLat * Math.PI / 180;
        final double calA = (dLat - sLat) * Math.PI / 180;
        final double calB = (dLong - sLong) * Math.PI / 180;

        final double calculate_A = Math.sin(calA / 2) * Math.sin(calA / 2) +
                Math.cos(A) * Math.cos(B) *
                        Math.sin(calB / 2) * Math.sin(calB / 2);

        final double calcuate_B = 2 * Math.atan2(Math.sqrt(calculate_A), Math.sqrt(1 - calculate_A));

        final double distance = earthRadius * calcuate_B;//in meters
        return distance/1000; //in km

    }

    public double fairAmount (double distance)
    {
        double fair = 30 ;
        fair += ((distance - 3) * 11);
        return fair ;


    }


}
