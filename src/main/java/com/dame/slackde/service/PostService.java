package com.dame.slackde.service;

import com.dame.slackde.entity.Channel;
import com.dame.slackde.entity.Post;
import com.dame.slackde.entity.User;
import com.dame.slackde.repository.ChannelRepository;
import com.dame.slackde.repository.PostRepository;
import com.dame.slackde.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChannelRepository channelRepository;

    public List<Post> getAllPosts() {return postRepository.findAll();}

    public Post getPostById(long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        return optionalPost.orElse(null);
    }

    public Post addPost(Post post) {
        Optional<Channel> existingChannel = channelRepository.findById(post.getIdChannel());
        Optional<User> existingUser = userRepository.findById(post.getIdUser());
        if (existingChannel.isPresent()) {

            Channel channel = existingChannel.get();
            User user = existingUser.get();

            post.setChannel(channel);
            post.setUser(user);
            post.setPostDateTime(new Date());
            postRepository.save(post);
            return post;
        }else {
            return null;
        }
    }

    public void deletePost(long id) {
        postRepository.deleteById(id);
    }

    public Post updatePost(Post updatedPost, long id) {
        Optional<Channel> existingChannelUpdate = channelRepository.findById(updatedPost.getIdChannel());
        Optional<User> existingUserUpdate = userRepository.findById(updatedPost.getIdUser());

        if (existingChannelUpdate.isPresent()) {
            Channel channel = existingChannelUpdate.get();
            User user = existingUserUpdate.get();

            Optional<Post> existingPostOptional = postRepository.findById(id);


            if (existingPostOptional.isPresent()) {
                Post existingPost = existingPostOptional.get();
                if (!existingPost.getMessage().equals(updatedPost.getMessage())) {

                    updatedPost.setChannel(channel);
                    updatedPost.setUser(user);

                    updatedPost.setPostDateTime(new Date());
                    updatedPost.setId(id);
                    postRepository.save(updatedPost);
                }
            }
            return updatedPost;
        }else {
            return null;
        }
    }



    public Post addPostToUser(Post post, User user) {
        user.addPost(post);
        post.setUser(user);

        userRepository.save(user);
        return postRepository.save(post);
    }

    public Post addPostToChannel(Post post, Channel channel) {
        channel.addPost(post);
        post.setChannel(channel);

        channelRepository.save(channel);
        return postRepository.save(post);
    }

    public Post addPostToUserAndChannel(Post post, User user, Channel channel) {
        user.addPost(post);
        post.setUser(user);
        userRepository.save(user);

        channel.addPost(post);
        post.setChannel(channel);
        channelRepository.save(channel);

        return postRepository.save(post);
    }

}
