package com.codewithpp.JavaDriver.service;

public interface Service {

    public int generateOtp();

    public void sendMail(String sub, String kon, String kise, String kya);

}
