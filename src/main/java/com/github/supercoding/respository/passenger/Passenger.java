package com.github.supercoding.respository.passenger;

import com.github.supercoding.respository.users.UserEntity;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private UserEntity user;
    @Column(name = "passport_num", length = 50)
    private String passportNum;
}
