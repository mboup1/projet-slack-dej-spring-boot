package fr.moussalli.slackdej.service;

import fr.moussalli.slackdej.entity.Channel;
import fr.moussalli.slackdej.entity.Post;
import fr.moussalli.slackdej.entity.User;
import fr.moussalli.slackdej.repository.ChannelRepository;
import fr.moussalli.slackdej.repository.PostRepository;
import fr.moussalli.slackdej.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    public void delete(Long id) {
        channelRepository.deleteById(id);
    }

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
