package br.com.bunbismuth.login_app_backend.repositories;

import br.com.bunbismuth.login_app_backend.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

  Optional<User> findByEmail(String email);
}
