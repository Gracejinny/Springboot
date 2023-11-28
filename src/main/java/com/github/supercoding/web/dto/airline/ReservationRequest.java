package com.github.supercoding.web.dto.airline;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReservationRequest {
    @ApiModelProperty(name = "userId", value = "유저 ID") private Integer userId;
    @ApiModelProperty(name = "airlineTicketId", value = "항공편 ID") private Integer airlineTicketId;


}
