package com.bosorio.user_service.infrastructure.adapter;

import com.bosorio.user_service.domain.model.User;
import com.bosorio.user_service.domain.port.UserPersistencePort;
import com.bosorio.user_service.infrastructure.adapter.out.persistence.entity.UserEntity;
import com.bosorio.user_service.infrastructure.adapter.out.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserAdapter implements UserPersistencePort {

    private final UserRepository userRepository;

    @Override
    public void register(User user) {
        userRepository.save(new UserEntity(
                null,
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getPassword()
        ));
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id)
                .map(userEntity -> new User(
                        userEntity.getId(),
                        userEntity.getName(),
                        userEntity.getEmail(),
                        userEntity.getPassword(),
                        userEntity.getRole()
                    )
                );
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userEntity -> new User(
                        userEntity.getId(),
                        userEntity.getName(),
                        userEntity.getEmail(),
                        userEntity.getPassword(),
                        userEntity.getRole()
                    )
                );
    }
}
