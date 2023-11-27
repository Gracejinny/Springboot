package com.github.supercoding.respository.passenger;

import java.util.Optional;

public interface PassengerRepository {
    Optional<Passenger> findPassengerByUserId(Integer userId);
}
