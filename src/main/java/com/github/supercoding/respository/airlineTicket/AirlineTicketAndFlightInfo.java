package com.github.supercoding.respository.airlineTicket;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AirlineTicketAndFlightInfo {
    private Integer ticketId;
    private Integer price;
    private Integer charge;
    private Integer tax;
    private Integer totalPrice;

    public AirlineTicketAndFlightInfo(Integer ticketId, Double price, Double charge, Double tax, Double totalPrice) {
        this.ticketId = ticketId;
        this.price = price.intValue();
        this.charge = charge.intValue();
        this.tax = tax.intValue();
        this.totalPrice = totalPrice.intValue();
    }
}
