package com.github.supercoding.respository.reservations;

import com.github.supercoding.respository.airlineTicket.AirlineTicket;
import com.github.supercoding.respository.passenger.Passenger;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airline_ticket_id")
    private AirlineTicket airlineTicket;

    @Column(name = "reservation_status", length = 10)
    private String reservationStatus;
    @Column(name = "reserve_at")
    private LocalDateTime reserveAt;

    public Reservation(Passenger passenger, AirlineTicket airlineTicket) {
        this.passenger = passenger;
        this.airlineTicket = airlineTicket;
        this.reservationStatus = "대기";
        this.reserveAt = LocalDateTime.now();
    }
}
