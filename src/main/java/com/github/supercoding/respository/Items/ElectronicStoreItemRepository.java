package com.github.supercoding.respository.Items;

import java.util.List;

public interface ElectronicStoreItemRepository {
    List<ItemEntity> findAllItems();

    Integer saveItem(ItemEntity itemEntity);

    ItemEntity updateItemEntity(Integer idInt, ItemEntity itemEntity);

    ItemEntity findItemByPathID(Integer idInt);

    List<ItemEntity> findItemsByIds();

    void deleteItem(int parseInt);

    void updateItemStock(Integer itemId, Integer i);
}
