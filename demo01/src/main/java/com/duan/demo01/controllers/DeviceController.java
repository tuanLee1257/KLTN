package com.duan.demo01.controllers;

import com.duan.demo01.models.Device;
import com.duan.demo01.repositories.DeviceRepo;
import com.duan.demo01.servies.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/device")
public class DeviceController {
    private DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("")
    public ResponseEntity<List<Device>> getAll() {
        return ResponseEntity.ok(deviceService.getAllDevices());
    }

    @PostMapping("/add")
    public ResponseEntity<String> addDevice(@RequestPart("device") Device device,@RequestPart("file") MultipartFile file) {
        deviceService.addDevice(device,file);
        return ResponseEntity.ok("Complete");
    }

    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestBody Device device,MultipartFile file) {
        deviceService.updateDevice(device,file);
        return ResponseEntity.ok("");
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        deviceService.removeDevice(Integer.valueOf(id));
        return ResponseEntity.ok("");
    }

    @GetMapping("/qr/{fileName:.+}")
    public ResponseEntity<byte[]> getQRImage(@PathVariable String fileName) {
        try {
            byte[] image = deviceService.getDeviceQRCode(fileName);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<byte[]> getDeviceImage(@PathVariable String fileName) {
        try {
            byte[] image = deviceService.getDeviceImage(fileName);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

