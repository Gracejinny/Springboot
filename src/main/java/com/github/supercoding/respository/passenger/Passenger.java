package com.github.supercoding.respository.passenger;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "passengerId")
@Builder
public class Passenger {
    private Integer passengerId;
    private Integer userId;
    private String passportNum;
}
