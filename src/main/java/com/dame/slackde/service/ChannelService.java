package com.dame.slackde.service;

import com.dame.slackde.entity.Channel;
import com.dame.slackde.entity.Post;
import com.dame.slackde.entity.User;
import com.dame.slackde.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChannelService {
    @Autowired
    private ChannelRepository channelRepository;

    public ChannelService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }
    public Channel add(Channel a) {
        return channelRepository.save(a);
    }

    public Channel save(Channel a) {
        return channelRepository.save(a);
    }

    public List<Channel> getAll() {
        return channelRepository.findAll();
    }

    public Optional<Channel> findById(Long id) {
        return channelRepository.findById(id);
    }

    //Jerome
    public Channel update(Channel channel) {
        return channelRepository.save(channel);
    }

    public void delete(Long id) {
        channelRepository.deleteById(id);
    }

    //Dame
    public Channel updateChannel( long id, Channel updatedChannel) {
        Optional<Channel> existingChannelUpdate = channelRepository.findById(id);

        if (existingChannelUpdate.isPresent()) {
            Channel channel = existingChannelUpdate.get();
            System.out.println("channel :"+ channel);

            User user = existingChannelUpdate.get().getUser();
            System.out.println("user :"+ user);

            List<Post> updatedListPosts = existingChannelUpdate.get().getPosts();
            System.out.println("updatedListPosts :"+ updatedListPosts);

            channel.setUser(user);
            channel.setPosts(updatedListPosts);
            updatedChannel.setId(id);

            channelRepository.save(channel);

            return channel;
        }else {
            return null;
        }
    }

    public int nombreDeChannels() {
        return channelRepository.findAll().size();
    }

}
