package com.efhem.content.auth;

import com.efhem.content.auth.model.AuthenticationResponse;
import com.efhem.content.auth.model.SignInRequest;
import com.efhem.content.auth.model.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authService;
    @PostMapping("/signUp")
    public ResponseEntity<AuthenticationResponse> signup(
            @RequestBody SignupRequest request
    ){
        return ResponseEntity.ok(authService.signup(request));
    }

    @PostMapping("/signIn")
    public ResponseEntity<AuthenticationResponse> signIn(
            @RequestBody SignInRequest request
    ){
        return ResponseEntity.ok(authService.signIn(request));
    }
}
