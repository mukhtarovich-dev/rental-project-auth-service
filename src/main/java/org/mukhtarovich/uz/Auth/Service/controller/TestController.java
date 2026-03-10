package org.mukhtarovich.uz.Auth.Service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

        @RequestMapping("/")
        @ResponseStatus(HttpStatus.OK)
        public ResponseEntity<String> hi(){
            return ResponseEntity.ok("hi");
        }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/hello")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> Hello(){
        return ResponseEntity.ok("Helo world");
    }
}
