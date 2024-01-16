package com.wift.controllers;

import com.wift.dto.AuthorizedUserCredentials;
import com.wift.dto.UserCredentials;
import com.wift.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthorizedUserCredentials> login(@Valid @RequestBody UserCredentials userCredentials) {
        AuthorizedUserCredentials credentials = userService.login(userCredentials);

        return ResponseEntity.ok(credentials);
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthorizedUserCredentials> signup(@Valid @RequestBody UserCredentials userCredentials) {
        AuthorizedUserCredentials credentials = userService.signUp(userCredentials);

        return ResponseEntity.ok(credentials);
    }
}
