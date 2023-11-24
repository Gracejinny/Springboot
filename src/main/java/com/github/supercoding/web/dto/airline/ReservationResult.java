package com.github.supercoding.web.dto.airline;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReservationResult {
    private List<Integer> prices;
    private List<Integer> charges;
    private Integer tax;
    private Integer totalPrice;
    private Boolean success;

    public ReservationResult(List<Integer> prices, List<Integer> charges, Integer tax, Integer totalPrice, Boolean success) {
        this.prices = prices;
        this.charges = charges;
        this.tax = tax;
        this.totalPrice = totalPrice;
        this.success = success;
    }
}
