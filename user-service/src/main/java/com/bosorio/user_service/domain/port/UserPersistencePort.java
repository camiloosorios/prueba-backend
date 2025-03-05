package com.bosorio.user_service.domain.port;

import com.bosorio.user_service.domain.model.User;

import java.util.Optional;

public interface UserPersistencePort {

    void register(User user);
    Optional<User> findUserById(Long id);
    Optional<User> findUserByEmail(String email);

}
