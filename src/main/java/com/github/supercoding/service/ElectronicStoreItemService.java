package com.github.supercoding.service;

import com.github.supercoding.respository.Items.ElectronicStoreItemJpaRepository;
import com.github.supercoding.respository.Items.ItemEntity;
import com.github.supercoding.respository.storeSales.StoreSales;
import com.github.supercoding.respository.storeSales.StoreSalesJpaRepository;
import com.github.supercoding.service.exceptions.NotFoundException;
import com.github.supercoding.service.mapper.ItemMapper;
import com.github.supercoding.web.dto.items.BuyOrder;
import com.github.supercoding.web.dto.items.Item;
import com.github.supercoding.web.dto.items.ItemBody;
import com.github.supercoding.web.dto.items.StoreInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ElectronicStoreItemService {

    private final ElectronicStoreItemJpaRepository electronicStoreItemJpaRepository;
    private final StoreSalesJpaRepository storeSalesJpaRepository;

    @Cacheable(value = "items", key = "#root.methodName")
    public List<Item> findAllItem() {
        List<ItemEntity> itemEntities = electronicStoreItemJpaRepository.findAll();
        return itemEntities.stream().map(ItemMapper.INSTANCE::itemEntityToItem).collect(Collectors.toList());
    }

    @CacheEvict(value = "items", allEntries = true)
    public Integer saveItem(ItemBody itemBody) {
        ItemEntity itemEntity = ItemMapper.INSTANCE.idAndItemBodyToItemEntity(null,itemBody);
        ItemEntity itemEntityCreated;
        itemEntityCreated = electronicStoreItemJpaRepository.save(itemEntity);
        return itemEntityCreated.getId();
    }

    @Cacheable(value = "items", key = "#id")
    public Item findItemByID(String id){
        Integer idInt = Integer.parseInt(id);
        ItemEntity itemEntity = electronicStoreItemJpaRepository.findById(idInt).orElseThrow(()->new NotFoundException("해당 아이디를 찾을 수 없습니다."));
        Item item = ItemMapper.INSTANCE.itemEntityToItem(itemEntity);
        return item;
    }

    @Cacheable(value = "items", key = "#ids")
    public List<Item> findItemsByIds(List<String> ids) {
        List<ItemEntity> itemEntities = electronicStoreItemJpaRepository.findAll();
        return itemEntities.stream()
                .map(ItemMapper.INSTANCE::itemEntityToItem)
                .filter((item -> ids.contains(item.getId())))
                .collect(Collectors.toList());
    }

    @CacheEvict(value = "items", allEntries = true)
    public void deleteItem(String id) {
        Integer idInt = Integer.parseInt(id);
        electronicStoreItemJpaRepository.deleteById(idInt);
    }

    @CacheEvict(value = "items", allEntries = true)
    @Transactional(transactionManager = "tmJpa1")
    public Item updateItem(String id, ItemBody itemBody) {
        Integer idInt = Integer.valueOf(id);

        ItemEntity itemEntityUpdated = electronicStoreItemJpaRepository.findById(idInt)
                .orElseThrow(()-> new NotFoundException("해당 ID : " + idInt + "의 Item을 찾을 수 없습니다."));
        itemEntityUpdated.setItemBody(itemBody);

        return ItemMapper.INSTANCE.itemEntityToItem(itemEntityUpdated);
    }

    @Transactional(transactionManager = "tmJpa1")
    public Integer buyItems(BuyOrder buyOrder) {
        // 1. BuyOrder에서 상품 ID와 수량을 얻어낸다.
        Integer itemId = buyOrder.getItemId();
        Integer itemNums = buyOrder.getItemNums();

        // 2. 상품을 조회하여 수량이 얼마나 있는지 확인한다.
        ItemEntity itemEntity = electronicStoreItemJpaRepository.findById(itemId)
                .orElseThrow(()-> new NotFoundException("해당 ID : " + itemId + "의 Item을 찾을 수 없습니다."));

        log.info("===================동작 확인 로그 1 ======================");

        // 단 재고가 없거나 매장을 찾을 수 없으면 살 수 없다.
        if(itemEntity.getStoreSales().isEmpty()) throw new RuntimeException("매장을 찾을 수 없습니다.");
        if(itemEntity.getStock() <= 0) throw new RuntimeException("상품의 재고가 없습니다.");

        // 3. 상품의 수량과 가격을 가지고 계산하여 총 가격을 구한다.
        Integer successBuyItemNums;
        if(itemNums >= itemEntity.getStock()) successBuyItemNums = itemEntity.getStock();
        else successBuyItemNums = itemNums;

        Integer totalPrice = successBuyItemNums * itemEntity.getPrice();

        // 4. 상품의 재고에 기존 계산한 재고를 구매하는 수량을 뺸다.
        itemEntity.setStock(itemEntity.getStock() -successBuyItemNums);

        if (successBuyItemNums == 4){
            log.error("4구매하는 건 허락하지 않습니다.");
            throw new RuntimeException("4개를 구매하는 건 허락하지 않습니다.");
        }

        log.info("===================동작 확인 로그 2 ======================");

        // 5. 상품 구매하는 수량 * 가격 만큼 가계 매상으로 올린다.
        StoreSales storeSales = itemEntity.getStoreSales()
                .orElseThrow(()-> new NotFoundException("요청하신 storeId에 해당하는 StoreSales를 찾을 수 없습니다."));
        storeSales.setAmount(storeSales.getAmount() + totalPrice);
        return successBuyItemNums;
    }

    public List<Item> findItemByTypes(List<String> types) {
        List<ItemEntity> itemEntities = electronicStoreItemJpaRepository.findItemEntitiesByTypeIn(types);
        return itemEntities.stream().map(ItemMapper.INSTANCE::itemEntityToItem).collect(Collectors.toList());
    }

    public List<Item> findItemsOrderByPrices(Integer maxValue) {
        List<ItemEntity> itemEntities = electronicStoreItemJpaRepository.findItemEntitiesByPriceLessThanEqualOrderByPriceAsc(maxValue);
        return itemEntities.stream().map(ItemMapper.INSTANCE::itemEntityToItem).collect(Collectors.toList());
    }

    public Page<Item> findAllWithPageable(Pageable pageable) {
        Page<ItemEntity> itemEntities = electronicStoreItemJpaRepository.findAll(pageable);
        return itemEntities.map(ItemMapper.INSTANCE::itemEntityToItem);
    }

    public Page<Item> findAllWithPageable(List<String> types, Pageable pageable) {
        Page<ItemEntity> itemEntities = electronicStoreItemJpaRepository.findAllByTypeIn(types,pageable);
        return itemEntities.map(ItemMapper.INSTANCE::itemEntityToItem);
    }

    @Transactional(transactionManager = "tmJpa1")
    public List<StoreInfo> findAllStoreInfo() {
        List<StoreSales> storeSales = storeSalesJpaRepository.findAllFetchJoin();
        log.info("======================= N + 1 확인용 로그 ================================");
        List<StoreInfo> storeInfos = storeSales.stream().map(StoreInfo::new).collect(Collectors.toList());
        return storeInfos;
    }
}
