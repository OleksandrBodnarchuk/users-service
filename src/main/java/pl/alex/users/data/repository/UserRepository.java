package pl.alex.users.data.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import pl.alex.users.data.entity.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

  Optional<UserEntity> findByEmail(String username);
}
