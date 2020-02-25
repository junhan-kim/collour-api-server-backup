package com.collour.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "")
public class IndexController {

    @GetMapping(value = "")
    public ResponseEntity<?> index() {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
