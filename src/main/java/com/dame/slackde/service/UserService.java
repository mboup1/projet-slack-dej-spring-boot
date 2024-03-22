package com.dame.slackde.service;

import com.dame.slackde.entity.User;
import com.dame.slackde.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public List<String> getEmail(Integer idUser) {
        Optional<User> optional = userRepository.findById(idUser);
        if (optional.isPresent()) {
            User user = optional.get();
            return Collections.singletonList(user.getEmail());
        }
        return Collections.emptyList();
    }


    public Optional<User> getOneById(Integer id) {
        return userRepository.findById(id);
    }


    // Add User with Email Check
    public User addUser(User user) {
//        System.out.println("user : "+ user);

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }
    // Remplacer un utilisateur par un autre (tous les attributs)
    public void replaceUser(User userToUpdate, User newDataUser){
        if(newDataUser.getName()!=null && !newDataUser.getName().isBlank())
            userToUpdate.setName(newDataUser.getName());
        if(newDataUser.getEmail()!=null && !newDataUser.getEmail().isBlank())
            userToUpdate.setEmail(newDataUser.getEmail());
        if(newDataUser.getPosts()!=null && !newDataUser.getPosts().isEmpty())
            userToUpdate.setPosts(newDataUser.getPosts());
        if(newDataUser.getChannels()!=null && !newDataUser.getChannels().isEmpty())
            userToUpdate.setChannels(newDataUser.getChannels());
        userRepository.save(userToUpdate);
    }

    public Optional<User> updateUser (Integer id, User newDataUser) {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            User userToUpdate = optional.get();
            replaceUser(userToUpdate, newDataUser);
            userRepository.save(userToUpdate);
        }
        return optional;
    }

    public int nombreDeUsers() {
        return userRepository.findAll().size();
    }


    // Get All Emails
    public List<String> getAllEmails() {
        return userRepository.findAllByEmailIsNotNull()
                .stream()
                .map(User::getEmail)
                .collect(Collectors.toList());
    }


    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }


}


