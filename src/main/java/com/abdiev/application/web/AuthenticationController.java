package com.abdiev.application.web;


import com.abdiev.application.payload.AuthenticationRequest;
import com.abdiev.application.payload.JwtResponse;
import com.abdiev.application.payload.RegistrationRequest;
import com.abdiev.application.service.JwtService;
import com.abdiev.application.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthenticationController {
    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> signUp(@RequestBody  AuthenticationRequest authenticationRequest) {
        userService.login(authenticationRequest);
        return ResponseEntity.ok().body(new JwtResponse(jwtService.generateToken(authenticationRequest.getLogin())));
    }

    @PostMapping("/signup")
    public ResponseEntity<JwtResponse> register(@RequestBody RegistrationRequest registrationRequest) {
        userService.signUp(registrationRequest);
        return ResponseEntity.ok().body(new JwtResponse(jwtService.generateToken(registrationRequest.getUsername())));
    }
}
