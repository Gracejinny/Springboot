package com.github.supercoding.web.dto.items;

import com.github.supercoding.respository.Items.ItemEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Item {
    private String id;
    private String name;
    private String type;
    private Integer price;
    private Spec spec;
}
