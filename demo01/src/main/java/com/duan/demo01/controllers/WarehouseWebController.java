package com.duan.demo01.controllers;

import com.duan.demo01.models.Warehouse;
import com.duan.demo01.servies.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/warehouse")
public class WarehouseWebController {
    WarehouseService warehouseService;

    @Autowired
    public WarehouseWebController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    //warehouse
    @GetMapping("")
    public String warehouse(Model model) {
        List<Warehouse> warehouseList = warehouseService.listWarehouse();
        model.addAttribute("warehouses",warehouseList);
        return "warehouse-page";
    }
}
