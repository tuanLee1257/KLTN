package com.duan.demo01.controllers;

import com.duan.demo01.models.Device;
import com.duan.demo01.servies.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/device")
public class DeviceWebController {
    DeviceService deviceService;

    @Autowired
    public DeviceWebController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("")
    public String home(Model model) {
        try {
            List<Device> devices = deviceService.getAllDevices();
            model.addAttribute("devices", devices);
            return "device-page";
        } catch (Exception e) {
            return "404";
        }
    }

    @GetMapping("/add")
    public String addDevice(Model model) {
        try {
            Device device = new Device();
            model.addAttribute("device", device);
            return "device-add";
        } catch (Exception e) {
            return "404";
        }
    }

    // ACTION

    @PostMapping("/add")
    public String saveDevice(@ModelAttribute("device") Device device, @RequestParam MultipartFile file) {
        deviceService.addDevice(device, file);
        return "redirect:/";

    }

    @PostMapping("/delete/{id}")
    public String saveDeleteDevice(@PathVariable("id") Integer deviceId) {
        Device device = deviceService.getDeviceByID(deviceId);
        deviceService.removeDevice(device.getId());
        return "redirect:/";
    }

    @PostMapping("/update")
    public String updateDevice(@ModelAttribute("device")Device device,@ModelAttribute("device")MultipartFile file) {
        deviceService.updateDevice(device.getId(),device);
        return "redirect:/api/devices";
    }
}

