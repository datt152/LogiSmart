package logismart.service;

import logismart.dto.request.LoginRequest;
import logismart.dto.request.RegisterRequest;
import logismart.dto.response.AuthResponse;

public interface AuthService {
    String register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
