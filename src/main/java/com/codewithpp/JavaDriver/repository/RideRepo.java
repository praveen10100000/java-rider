package com.codewithpp.JavaDriver.repository;

import com.codewithpp.JavaDriver.entity.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRepo extends JpaRepository<Ride, Integer> {

}
