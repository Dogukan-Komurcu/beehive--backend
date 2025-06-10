package com.example.beehiveproject.service;


import com.example.beehiveproject.dto.request.LoginRequest;
import com.example.beehiveproject.dto.request.RegisterRequest;
import com.example.beehiveproject.dto.response.LoginResponse;
import com.example.beehiveproject.dto.response.UserResponse;
import com.example.beehiveproject.entity.Role;
import com.example.beehiveproject.entity.User;
import com.example.beehiveproject.repository.RoleRepository;
import com.example.beehiveproject.repository.UserRepository;
import com.example.beehiveproject.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//Service layer is where the business logis created. We can handle our security validations
//and DTO to entity transformations.
@Service
public class AuthService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    // Constructor-based dependency injection is preferred
    @Autowired
    public AuthService(RoleRepository roleRepository, UserRepository userRepository, JwtUtil jwtUtil) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    //Map RegisterRequest DTO to User entity, save it to DB,
    //then return a simplified UserResponse DTO.
    public UserResponse register(RegisterRequest request) {
        Role userRole = roleRepository.findByName("GÖZLEMCİ")
                .orElseThrow(() -> new RuntimeException("Default role 'GÖZLEMCİ' not found in database."));

        User user = new User(
                request.getEmail(),
                request.getPassword(),
                request.getName(),
                request.getFullName(),
                userRole
        );

        user = userRepository.save(user);

        return new UserResponse(user.getId(), user.getFullName(), user.getEmail());
    }

    /**
     * Handles login.
     * If email and password match, we generate a JWT token
     * and return it inside a LoginResponse DTO.
     */
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!request.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Kullanıcı başarılı giriş yaptı, lastLogin güncelle
        user.setLastLogin(java.time.LocalDateTime.now());
        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getId(), user.getRole().getName());
        UserResponse userResponse = new UserResponse(user.getId(), user.getFullName(), user.getEmail());
        return new LoginResponse(token, userResponse);
    }

    /**
     * This method is used for authenticated users to fetch their profile data.
     * We validate the token, extract the user ID, and return basic user info.
     */
    public UserResponse getCurrentUser(String token) {
        if (!jwtUtil.isTokenValid(token)) {
            throw new RuntimeException("Invalid token");
        }

        String userId = jwtUtil.extractId(token);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserResponse(user.getId(), user.getFullName(), user.getEmail());
    }

    // Şifre sıfırlama
    public String resetPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // Geçici şifre oluştur
        String tempPassword = java.util.UUID.randomUUID().toString().substring(0, 8);
        user.setPassword(tempPassword);
        userRepository.save(user);
        // Burada e-posta ile gönderim yapılabilir
        return tempPassword;
    }


}
