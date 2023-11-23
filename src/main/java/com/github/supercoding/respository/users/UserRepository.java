package com.github.supercoding.respository.users;

public interface UserRepository {
    UserEntity findUserById(Integer userId);
}
