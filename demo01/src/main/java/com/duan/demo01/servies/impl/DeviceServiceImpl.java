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
import java.nio.file.Files;
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
            Integer id = deviceRepo.findMaxId()+1;

            if (file != null) {
                InputStream deviceImage = new ByteArrayInputStream(file.getBytes());
                String deviceImgName = ImageUtil.uploadFile(deviceImage, "devices");
                device.setImage(deviceImgName);
            }

            BufferedImage qrCodeImage = QRCodeGenerator.getQRCodeImage(id.toString());
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            ImageIO.write(qrCodeImage, "png", bytes);
            InputStream qrImage = new ByteArrayInputStream(bytes.toByteArray());
            String qrImgName = ImageUtil.uploadFile(qrImage, "qr");

            QR qr = new QR();
            qr.setName(qrImgName);

            device.setId(id);
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
    public void updateDevice(Device updateDevice, MultipartFile file) {
        try {
            String imageName = null;
            if (updateDevice.getImage() == null){
                InputStream deviceImage = new ByteArrayInputStream(file.getBytes());
                imageName = ImageUtil.uploadFile(deviceImage, "devices");
            }
            else {
                imageName = replaceImg(updateDevice.getImage(), file);
            }
            updateDevice.setImage(imageName);
            deviceRepo.save(updateDevice);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
    public byte[] getDeviceImage(String imageName) {
        try {
            Path storageFolder = Paths.get("devices");
            Path file = storageFolder.resolve(imageName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
                return bytes;
            } else {
                throw new RuntimeException("Could not read file: " + imageName);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not read file: " + imageName, e);
        }
    }


    String replaceImg(String imageName, MultipartFile newImage) throws IOException {
        Path storageFolder = Paths.get("devices");
        Path file = storageFolder.resolve(imageName);
        Files.delete(file);
        InputStream deviceImage = new ByteArrayInputStream(newImage.getBytes());
        return ImageUtil.uploadFile(deviceImage, "devices");
    }
}
