package com.github.supercoding.web.dto.airline;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponse {
    private List<Ticket> tickets;
}
