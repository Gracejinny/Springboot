package com.github.supercoding.respository.storeSales;

public interface StoreSalesRepository {
    StoreSales findStoreSalesById(Integer storeId);

    void updateSalesAmount(Integer storeId, Integer amount);
}
