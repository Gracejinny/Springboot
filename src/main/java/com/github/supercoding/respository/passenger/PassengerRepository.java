package com.github.supercoding.respository.passenger;

public interface PassengerRepository {
    Passenger findPassengerByUserId(Integer userId);
}
