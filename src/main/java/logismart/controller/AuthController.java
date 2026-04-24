package logismart.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import logismart.dto.request.LoginRequest;
import logismart.dto.request.RegisterRequest;
import logismart.dto.response.AuthResponse;
import logismart.dto.response.ErrorResponse;
import logismart.dto.response.UserResponse;
import logismart.entity.User;
import logismart.repository.UserRepository;
import logismart.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Các API liên quan đến bảo mật và xác thực người dùng")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> register(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal UserDetails currentUser) {
        if(currentUser == null) {
            return ResponseEntity.status(401).build();
        }
        return userRepository.findByUsername(currentUser.getUsername()).map(user -> {
           UserResponse userResponse  = UserResponse.builder()
                    .username(user.getUsername())
                    .fullName(user.getFullName())
                    .role(user.getRole())
                    .build();
            return ResponseEntity.ok(userResponse);
        }).orElse(ResponseEntity.status(404).build());
    }
}
