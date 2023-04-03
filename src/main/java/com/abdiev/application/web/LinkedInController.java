package com.abdiev.application.web;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
@CrossOrigin()
public class LinkedInController {

    @GetMapping
    public ResponseEntity<List<String>> fetchTest() {
        return ResponseEntity.ok().body(List.of("AceL Secure API"));
    }
}
