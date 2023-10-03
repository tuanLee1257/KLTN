package com.duan.demo01.controllers;

import com.duan.demo01.models.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("")
public class HomeController {

    @GetMapping("")
    public String index(Model model){
        model.addAttribute("person", new Person());
        return "test";
    }

    @PostMapping("/post")
    public String submit(@RequestParam MultipartFile file, @ModelAttribute Person person) {
        String firstName = person.getFirst();
        String middleName = person.getMiddle();
        String lastName = person.getLast();


        return "redirect:/";
    }



}
