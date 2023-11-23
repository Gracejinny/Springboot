package com.github.supercoding.web.dto.airline;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReservationRequest {
    private Integer userId;
    private Integer airlineTicketId;

    public Integer getUserId() {
        return userId;
    }

    public Integer getAirlineTicketId() {
        return airlineTicketId;
    }

    public ReservationRequest() {
    }
}
