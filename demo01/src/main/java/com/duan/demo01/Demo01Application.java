package com.duan.demo01;

import com.duan.demo01.models.Device;
import com.duan.demo01.repositories.DeviceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class Demo01Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Demo01Application.class, args);
    }

    private final Path storageFolder = Paths.get("uploads");

    @Autowired
    DeviceRepo deviceRepo;

    @Override
    public void run(String... args) throws Exception {

    }
}
