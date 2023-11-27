package com.github.supercoding.respository.users;

import java.util.Optional;

public interface UserRepository {
    Optional<UserEntity> findUserById(Integer userId);
}
