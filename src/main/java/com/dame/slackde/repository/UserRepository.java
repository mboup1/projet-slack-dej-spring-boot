package com.dame.slackde.repository;

import com.dame.slackde.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByEmailIsNotNull();

    User findByUsername(String username);

}
