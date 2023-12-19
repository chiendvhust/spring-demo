package com.example.demo.controller;

import com.example.demo.entity.AirPort;
import com.example.demo.service.AirPortService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/")
@RequiredArgsConstructor
public class AirPortController {
    private final AirPortService airPortService;

    @GetMapping(value = "/airports/{code}")
    public ResponseEntity<AirPort> getAirportByCode(@PathVariable String code) {
        return new ResponseEntity<>(airPortService.getAirPort(code), HttpStatus.OK);
    }
}
