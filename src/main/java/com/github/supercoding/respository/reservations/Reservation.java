package com.github.supercoding.respository.reservations;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservation")
@EqualsAndHashCode(of = "reservationId")
public class Reservation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Integer reservationId;
    @Column(name = "passenger_id")
    private Integer passengerId;
    @Column(name = "airline_ticket_id")
    private Integer airlineTicketId;
    @Column(name = "reservation_status", length = 10)
    private String reservationStatus;
    @Column(name = "reserve_at")
    private LocalDateTime reserveAt;

    public Reservation(Integer passengerId, Integer airlineTicketId) {
        this.passengerId = passengerId;
        this.airlineTicketId = airlineTicketId;
        this.reservationStatus = "대기";
        this.reserveAt = LocalDateTime.now();
    }
}
