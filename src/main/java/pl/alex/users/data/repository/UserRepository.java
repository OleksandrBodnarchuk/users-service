package pl.alex.users.data.repository;

import org.springframework.data.repository.CrudRepository;
import pl.alex.users.data.entity.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

}
