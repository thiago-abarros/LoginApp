package br.com.bunbismuth.login_app_backend.controllers;

import br.com.bunbismuth.login_app_backend.domain.User;
import br.com.bunbismuth.login_app_backend.dto.LoginRequestDTO;
import br.com.bunbismuth.login_app_backend.dto.RegisterRequestDTO;
import br.com.bunbismuth.login_app_backend.dto.ResponseDTO;
import br.com.bunbismuth.login_app_backend.infra.security.TokenService;
import br.com.bunbismuth.login_app_backend.repositories.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenService tokenService;

  @PostMapping("/login")
  public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequestDTO body) {
    User user = userRepository.findByEmail(body.email())
        .orElseThrow(() -> new RuntimeException("User not found"));

    if (passwordEncoder.matches(body.password(), user.getPassword())) {
      String token = this.tokenService.generateToken(user);
      return ResponseEntity.ok(new ResponseDTO(user.getPassword(), token));
    }
    return ResponseEntity.badRequest().body(new ResponseDTO("user not found", null));
  }

  @PostMapping("/register")
  public ResponseEntity<ResponseDTO> register(@RequestBody RegisterRequestDTO body) {
    Optional<User> user = userRepository.findByEmail(body.email());
    if (user.isEmpty()) {
      User newUser = new User();
      newUser.setUsername(body.name());
      newUser.setPassword(passwordEncoder.encode(body.password()));
      newUser.setEmail(body.email());
      this.userRepository.save(newUser);

      String token = this.tokenService.generateToken(newUser);
      return ResponseEntity.ok(new ResponseDTO(newUser.getUsername(), token));
    }

    return ResponseEntity.badRequest().body(new ResponseDTO("user already exists", null));
  }
}
