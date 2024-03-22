package com.dame.slackde.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PostTest {

    @Test
    void getPost() {
        Post post = new Post();

        post.setMessage("message 1");
        post.setPostDateTime(new Date());

        assertEquals("message 1", post.getMessage());
        assertEquals(new Date().toString(), post.getPostDateTime().toString());
    }

    @Test
    void addPostToUser() {
        User user = new User("dame", "dame@dame.com");
        Post post = new Post("Bonjour, post 1 !", new Date());
        post.setUser(user);

        assertEquals(user, post.getUser());
    }

    @Test
    void addPostToChannel() {
        Channel channel = new Channel();
        channel.setName("canal 1");
        Post post = new Post("Bonjour, post 1 !", new Date());
        post.setChannel(channel);

        assertEquals(channel, post.getChannel());
    }

}
