package com.github.supercoding.respository.airlineTicket;

import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "ticketId")
public class AirlineTicket {
    private Integer ticketId;
    private String ticketType;
    private String departureLoc;
    private String arrivalLoc;
    private LocalDateTime departureAt;
    private LocalDateTime returnAt;
    private Double tax;
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
