package aua.badges.dao;

import aua.badges.model.User;
import aua.badges.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author davitv
 */
@Service
@Slf4j
public class UserDAO {
    @Autowired
    private UserRepository userRepository;


    public User saveUser(User user) {
     return userRepository.save(user);
    }

    public List<User> findAllUsers() {
       return userRepository.findAll();
    }

    public Optional<User> findUserById(String userId) {
    return userRepository.findById(userId);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
