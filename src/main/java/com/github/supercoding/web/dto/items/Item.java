package com.github.supercoding.web.dto.items;

import com.github.supercoding.respository.Items.ItemEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Item {
    @ApiModelProperty (name = "id", value = "Item Id", example = "1") private String id;
    @ApiModelProperty (name = "name", value = "Item 이름", example = "Dell XPS 15") private String name;
    @ApiModelProperty (name = "type", value = "Item 기기타입", example = "Laptop") private String type;
    @ApiModelProperty (name = "price", value = "Item 가격", example = "1250000") private Integer price;
    private Spec spec;
}
