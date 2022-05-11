package com.codewithpp.JavaDriver.repository;

import com.codewithpp.JavaDriver.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepo extends JpaRepository<Driver, Integer> {

    public boolean existsByPhoneNumber(String phoneNumber);

    public boolean existsByUserName(String userName);

    public boolean existsByPassword(String password);

    public Driver findByUserName(String userName);

}
