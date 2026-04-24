package logismart.service.impl;

import logismart.dto.request.LoginRequest;
import logismart.dto.request.RegisterRequest;
import logismart.dto.response.AuthResponse;
import logismart.dto.response.UserResponse;
import logismart.entity.User;
import logismart.exception.AppException;
import logismart.repository.UserRepository;
import logismart.service.AuthService;
import logismart.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public String register(RegisterRequest request) {
        if (userRepository.existsUserByUsername(request.getUsername())) {
            throw new AppException("Username '" + request.getUsername() + "' đã tồn tại trong hệ thống!");
        }
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .role(request.getRole())
                .build();
        userRepository.save(user);
        return "User registered successfully " + user.getUsername();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException("Sai tài khoản hoặc mật khẩu"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AppException("Sai tài khoản hoặc mật khẩu");
        }
        String token = jwtService.generateToken(request.getUsername());
        UserResponse userResponse = UserResponse.builder()
                .username(user.getUsername())
                .fullName(user.getFullName())
                .role(user.getRole()).build();
        return AuthResponse.builder()
                .token(token)
                .user(userResponse)
                .build();
    }
}
