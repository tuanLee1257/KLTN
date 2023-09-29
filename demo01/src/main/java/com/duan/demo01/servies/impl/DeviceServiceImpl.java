package com.duan.demo01.servies.impl;

import com.duan.demo01.models.Device;
import com.duan.demo01.models.QR;
import com.duan.demo01.repositories.DeviceRepo;
import com.duan.demo01.repositories.QRRepo;
import com.duan.demo01.servies.DeviceService;
import com.duan.demo01.utils.ImageUtil;
import com.duan.demo01.utils.QRCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;


@Configuration
public class DeviceServiceImpl implements DeviceService {
    private DeviceRepo deviceRepo;
    private QRRepo qrRepo;

    @Autowired
    public DeviceServiceImpl(DeviceRepo deviceRepo, QRRepo qrRepo) {
        this.deviceRepo = deviceRepo;
        this.qrRepo = qrRepo;
    }

    @Override
    public List<Device> getAllDevices() {
        List<Device> deviceList = deviceRepo.findAll();
        return deviceList;
    }

    @Override
    public Device getDeviceByID(Integer id) {
        Optional<Device> device1 = deviceRepo.findById(id);
        if (device1.isPresent()) return device1.get();
        return null;
    }

    @Override
    public void addDevice(Device device, MultipartFile file) {
        try {
            Device deviceAdded = deviceRepo.save(device);

            if (!file.isEmpty()) {
                InputStream deviceImage = new ByteArrayInputStream(file.getBytes());
                String deviceImgName = ImageUtil.uploadFile(deviceImage, "devices");
                device.setImage(deviceImgName);
            }

            BufferedImage qrCodeImage = QRCodeGenerator.getQRCodeImage(deviceAdded.getId().toString());
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            ImageIO.write(qrCodeImage, "png", bytes);
            InputStream qrImage = new ByteArrayInputStream(bytes.toByteArray());
            String qrImgName = ImageUtil.uploadFile(qrImage, "qr");

            QR qr = new QR();
            qr.setName(qrImgName);
            device.setQr(qr);


            deviceRepo.save(device);

        } catch (Exception e) {
            e.getMessage();
        }

    }

    @Override
    public void removeDevice(Integer id) {
        deviceRepo.deleteById(id);
    }

    @Override
    public void updateDevice(Integer id, Device newDevice) {
        deviceRepo.findById(id)
                .ifPresent(device -> {
                    device.setName(newDevice.getName());
                    device.setStatus(newDevice.getStatus());
                    device.setQuantity(newDevice.getQuantity());
                    device.setDateAcquired(newDevice.getDateAcquired());
                    deviceRepo.save(device);
                });
    }

    @Override
    public byte[] getDeviceQRCode(String qrName) {
        try {
            Path storageFolder = Paths.get("qr");
            Path file = storageFolder.resolve(qrName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
                return bytes;
            } else {
                throw new RuntimeException("Could not read file: " + qrName);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not read file: " + qrName, e);
        }
    }

    @Override
    public byte[] getDeviceImage(String deviceName) {
        try {
            Path storageFolder = Paths.get("devices");
            Path file = storageFolder.resolve(deviceName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
                return bytes;
            } else {
                throw new RuntimeException("Could not read file: " + deviceName);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not read file: " + deviceName, e);
        }
    }
}
