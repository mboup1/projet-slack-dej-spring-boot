package com.dame.slackde.service;

import com.dame.slackde.entity.Channel;
import com.dame.slackde.entity.Post;
import com.dame.slackde.entity.User;
import com.dame.slackde.repository.ChannelRepository;
import com.dame.slackde.repository.PostRepository;
import com.dame.slackde.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class PostServiceIntegrationTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    ChannelRepository channelRepository;

    @Test
    void testAddPostToUser() {
        User user1 = new User("jeff", "jef@jef.com");
        userRepository.save(user1);

        Post post1 = new Post("Bonjour, premier post !", new Date());
        post1.setUser(user1);

        postRepository.save(post1);
    }


    @Test
    void testAddPostToUserAndChannel() {
        Channel channel1 = new Channel();
        channel1.setName("canal 1");
        channelRepository.save(channel1);

        User user1 = new User("Jeff", "jef@jef.com");
        userRepository.save(user1);

        Post post1 = new Post("Bonjour, premier post dans le général !", new Date());
        post1.setChannel(channel1);
        post1.setUser(user1);

        postRepository.save(post1);
    }

    @Test
    void testFindAllPosts() {
        List<Post> postList = postRepository.findAll();

        if (postList.isEmpty()) {
            System.out.println("Aucun post trouvé");
        } else {
            for (Post post : postList) {
                System.out.println(post);
            }
        }
    }

    @Test
    void testFindPostById() {
        Optional<Post> postOptional = postRepository.findById(1L);
        if(postOptional.isEmpty())
            System.out.println("id non trouvé");
        else {
            Post post = postOptional.get();
            System.out.println(post);
        }
    }

    @Test
    void testUpdatePostById() {

        Optional<Post> postOptional = postRepository.findById(13L);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.setMessage("message message 2");
            postRepository.save(post);
        }
    }

    @Test
    void testDeletePostById() {
        postRepository.deleteById(2L);
    }




}
