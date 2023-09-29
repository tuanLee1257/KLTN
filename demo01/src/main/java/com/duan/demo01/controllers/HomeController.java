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
@RequestMapping("")
public class HomeController {

    @GetMapping("")
    public String index(){
        return "index";
    }


}