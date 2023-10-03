package com.duan.demo01.servies;

import com.duan.demo01.models.Device;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DeviceService {
    List<Device> getAllDevices();
    Device getDeviceByID(Integer id);
    void addDevice(Device device, MultipartFile file);
    void removeDevice(Integer id);
    void updateDevice( Device newDevice, MultipartFile file);
    byte[] getDeviceQRCode(String qrName);
    byte[] getDeviceImage(String deviceName);
}
