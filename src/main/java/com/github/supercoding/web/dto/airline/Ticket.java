package com.github.supercoding.web.dto.airline;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.supercoding.respository.airlineTicket.AirlineTicket;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
@Setter
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Ticket {
    @ApiModelProperty(name = "depart", value = "DepartureLoc") private String depart;
    @ApiModelProperty(name = "arrival", value = "ArrivalLoc")private String arrival;
    @ApiModelProperty(name = "departureTime", value = "DepartureAt")private String departureTime;
    @ApiModelProperty(name = "returnTime", value = "ReturnAt")private String returnTime;
    @ApiModelProperty(name = "ticketId", value = "ticketId")private Integer ticketId;

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Ticket(AirlineTicket airlineTicket){
        this.ticketId = airlineTicket.getTicketId();
        this.depart = airlineTicket.getDepartureLoc();
        this.arrival = airlineTicket.getArrivalLoc();
        this.departureTime = airlineTicket.getDepartureAt().format(formatter);
        this.returnTime = airlineTicket.getReturnAt().format(formatter);
    }
}
