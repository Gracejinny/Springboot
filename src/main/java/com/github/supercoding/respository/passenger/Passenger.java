package com.github.supercoding.respository.passenger;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "passenger")
@EqualsAndHashCode(of = "passengerId")
@Builder
public class Passenger {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passenger_id")
    private Integer passengerId;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "passport_num", length = 50)
    private String passportNum;
}
