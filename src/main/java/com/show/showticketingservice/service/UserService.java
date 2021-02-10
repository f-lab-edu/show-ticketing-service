package com.show.showticketingservice.service;

import com.show.showticketingservice.model.User;
import com.show.showticketingservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public int insertUser(User user) {
        return userRepository.insertUser(user);
    }

}
