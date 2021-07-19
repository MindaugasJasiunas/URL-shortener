package com.example.demo.controller;

import com.example.demo.entity.URLLink;
import com.example.demo.service.URLService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class MainController {
    private final URLService service;

    public MainController(URLService service) {
        this.service = service;
    }

    @RequestMapping("/")
    public String getIndexPage(Model model){
        model.addAttribute("urlLink", new URLLink());
        return "shorten";
    }

    @GetMapping("/{shortenedUrl}")
    public String redirectToFullLink(@PathVariable("shortenedUrl") String shortenedUrl){
        String fullLink= service.getFullLinkFromDBByShortenedLink(shortenedUrl);
        if(fullLink!=null) {
            return "redirect:"+fullLink;
        }
        return "redirect:/";
    }

    @PostMapping("/shortenURL")
    public String shortenUrl(@Valid @ModelAttribute("urlLink") URLLink urlLink, BindingResult br, Model model){
        if(br.hasErrors()){
            return "shorten";
        }
        URLLink urlLinkSaved= service.saveURLToDB(urlLink);
        if(urlLinkSaved==null){
            br.rejectValue("fullLink", "urlLink.fullLink", "Error occurred. Try again");
            return "shorten";
        }

        model.addAttribute("shortenedURL", service.getFormattedURLByShortenedURL(urlLinkSaved.getShortenedLink()));
        return "shortened";
    }
}
