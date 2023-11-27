package com.github.supercoding.respository.airlineTicket;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
@Entity
@Table(name = "airline_ticket")
@EqualsAndHashCode(of = "ticketId")
public class AirlineTicket {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Integer ticketId;

    @Column(name = "ticket_type", length = 5, columnDefinition = "CHECK(ticket_type) IN ('편도', '왕복')")
    private String ticketType;

    @Column(name = "departure_loc", length = 20)
    private String departureLoc;

    @Column(name = "arrival_loc", length = 20)
    private String arrivalLoc;

    @Column(name = "departure_at")
    private LocalDateTime departureAt;

    @Column(name = "return_at")
    private LocalDateTime returnAt;
    @Column(name = "tax")
    private Double tax;
    @Column(name = "total_price")
    private Double totalPrice;

    public AirlineTicket(Integer ticketId, String ticketType, String departureLoc, String arrivalLoc, Date departureAt, Date returnAt, Double tax, Double totalPrice) {
        this.ticketId = ticketId;
        this.ticketType = ticketType;
        this.departureLoc = departureLoc;
        this.arrivalLoc = arrivalLoc;
        this.departureAt = departureAt.toLocalDate().atStartOfDay();
        this.returnAt = returnAt.toLocalDate().atStartOfDay();
        this.tax = tax;
        this.totalPrice = totalPrice;
    }
}
