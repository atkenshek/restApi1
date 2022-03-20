package com.example.restapi1.Controller;

import com.example.restapi1.Entity.IPFinder;
import com.example.restapi1.Service.IPFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class IPFinderController {


    IPFinderService ipFinderService;

    @Autowired
    public IPFinderController(IPFinderService ipFinderService) {
        this.ipFinderService = ipFinderService;
    }

    @GetMapping("/users/country")
    public IPFinder getCountry() {
        return IPFinderService.getPublicIP();
    }


}
