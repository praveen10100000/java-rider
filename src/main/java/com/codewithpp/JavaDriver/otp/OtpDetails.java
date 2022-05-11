package com.codewithpp.JavaDriver.otp;

import com.codewithpp.JavaDriver.entity.Driver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OtpDetails {
    private int otp;
    private LocalDateTime expire = LocalDateTime.now().plusMinutes(20);
    private boolean loggedIn = false;
}
