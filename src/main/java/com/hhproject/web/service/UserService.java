package com.hhproject.web.service;


import com.hhproject.web.model.User;
import com.hhproject.web.respository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    final private User user;
    final private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> getAllUser() {
        return userRepository.findAll().stream().collect(Collectors.toList());
    }

    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserById(long id) {
        Optional<User> user = userRepository.findById(id);
        return user;
    }

    @Transactional(readOnly = true)
    public void update(long id,User user) {
        Optional<User> userDb =  userRepository.findById(id);
        userDb.ifPresent(user2 -> {
            user.setId(user2.getId());
            user2 = user;
            userRepository.save(user2);
        });
    }

    public void deleteUser(long id) {
        userRepository.findById(id).ifPresent(u -> {
            userRepository.delete(u);
        });
    }
}
