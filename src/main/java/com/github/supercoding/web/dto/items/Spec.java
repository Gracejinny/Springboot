package com.github.supercoding.web.dto.items;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Spec {
    @ApiModelProperty(name = "cpu", value = "Item CPU", example = "google Tensor") private String cpu;
    @ApiModelProperty (name = "capacity", value = "Item Spec", example = "25G") private String capacity;
}
