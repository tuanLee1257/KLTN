package com.duan.demo01.repositories;

import com.duan.demo01.models.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceRepo extends JpaRepository<Device, Integer> {
    List<Device> findByName(String name);
}
