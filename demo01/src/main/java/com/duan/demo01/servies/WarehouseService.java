package com.duan.demo01.servies;

import com.duan.demo01.models.Warehouse;

import java.util.List;

public interface WarehouseService {

    List<Warehouse> listWarehouse();
    Warehouse getWarehouse(Integer id);
    Warehouse updateWarehouse(Integer id,Warehouse warehouse);
    Warehouse removeWarehouse(Integer id);

}
