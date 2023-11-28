package com.github.supercoding.respository.airlineTicket;

import com.github.supercoding.respository.flight.Flight;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Entity
@Builder
@Table(name = "airline_ticket")
public class AirlineTicket {
    @Id
    @Column(name = "ticket_id")
    private Integer ticketId;
    @Column(name = "ticket_type", length = 5, columnDefinition = "CHECK (ticket_type in ('편도', '왕복')) ")
    private String ticketType;
    @Column(name = "departure_loc", length = 20)
    private String departureLoc;
    @Column(name = "arrival_loc", length = 20)
    private String arrivalLoc;

    @Column(name = "departure_at", nullable = false)
    private LocalDateTime departureAt;
    @Column(name = "return_at", nullable = false)
    private LocalDateTime returnAt;

    @Column(name = "tax")
    private Double tax;

    @Column(name = "total_price")
    private Double totalPrice;

    @OneToMany(mappedBy = "airlineTicket")
    private List<Flight> flightList;

}
