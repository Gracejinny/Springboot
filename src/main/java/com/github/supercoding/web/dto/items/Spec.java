package com.github.supercoding.web.dto.items;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Spec {
    private String cpu;
    private String capacity;

    public Spec(String cpu, String capacity) {
        this.cpu = cpu;
        this.capacity = capacity;
    }
}
