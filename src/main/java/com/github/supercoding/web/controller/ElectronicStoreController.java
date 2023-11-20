package com.github.supercoding.web.controller;

import com.github.supercoding.respository.ElectronicStoreItemRepository;
import com.github.supercoding.respository.ItemEntity;
import com.github.supercoding.web.dto.Item;
import com.github.supercoding.web.dto.ItemBody;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ElectronicStoreController {
    private ElectronicStoreItemRepository electronicStoreItemRepository;

    public ElectronicStoreController(ElectronicStoreItemRepository electronicStoreItemRepository) {
        this.electronicStoreItemRepository = electronicStoreItemRepository;
    }

    private static int serialItemId = 1;
    private List<Item> items = new ArrayList<>(Arrays.asList(
            new Item(String.valueOf(serialItemId++),"Apple iphone 12 pro max", "스마트폰", 2120000, "A14", "512GB"),
            new Item(String.valueOf(serialItemId++),"Apple mini 15 pro max", "미니", 3220000, "A15", "256GB"),
            new Item(String.valueOf(serialItemId++),"Apple ipad 12 pro max", "패드", 2400000, "A14", "1TB SSD"),
            new Item(String.valueOf(serialItemId++),"Apple imax 12 pro max", "맥스", 2120000, "A14", "512GB")
    ));

    @GetMapping("/items")
    public List<Item> findAllItem(){
        List<ItemEntity> itemEntities = electronicStoreItemRepository.findAllItems();
        return itemEntities.stream().map(Item::new).collect(Collectors.toList());
    }

    @PostMapping("/items")
    public String registerItem(@RequestBody ItemBody itemBody){
        ItemEntity itemEntity = new ItemEntity(null, itemBody.getName(), itemBody.getType(),
                                                itemBody.getPrice(), itemBody.getSpec().getCpu(), itemBody.getSpec().getCapacity());
        Integer itemId = electronicStoreItemRepository.saveItem(itemEntity);
        return "ID : " + itemId;
    }

    @GetMapping("/items/{id}")
    public Item findItemByPathId(@PathVariable String id){
        Item itemFounded =items.stream()
                                .filter((item -> item.getId().equals(id)))
                                .findFirst()
                                .orElseThrow(() -> new RuntimeException());
        return itemFounded;
    }

    @GetMapping("/items-query")
    public Item findItemByQueryId(@RequestParam("id") String id){
        Item itemFounded =items.stream()
                .filter((item -> item.getId().equals(id)))
                .findFirst()
                .orElseThrow(() -> new RuntimeException());
        return itemFounded;
    }

    @GetMapping("/items-queries")
    public List<Item> findItemByQueryIds(@RequestParam("id") List<String> ids){
        Set<String> idSet = ids.stream().collect(Collectors.toSet());

        List<Item> itemsFound = items.stream()
                                    .filter((item -> idSet.contains(item.getId())))
                                    .collect(Collectors.toList());
        return itemsFound;
    }

    @DeleteMapping("/items/{id}")
    public String deleteItemByPathId(@PathVariable String id){
        Item itemFounded =items.stream()
                .filter((item -> item.getId().equals(id)))
                .findFirst()
                .orElseThrow(() -> new RuntimeException());

        items.remove(itemFounded);

        return "Object with id = " + itemFounded.getId() + " has been deleteed";
    }

    @PutMapping("/items/{id}")
    public Item updateItem(@PathVariable String id, @RequestBody ItemBody itemBody){

        Integer idInt = Integer.valueOf(id);
        ItemEntity itemEntity = new ItemEntity(idInt, itemBody.getName(), itemBody.getType(), itemBody.getPrice(),
                                                itemBody.getSpec().getCpu(), itemBody.getSpec().getCapacity());

        ItemEntity itemEntityUpdated = electronicStoreItemRepository.updateItemEntity(idInt, itemEntity);

        Item itemUpdated = new Item(itemEntityUpdated);

        return itemUpdated;
    }
}
