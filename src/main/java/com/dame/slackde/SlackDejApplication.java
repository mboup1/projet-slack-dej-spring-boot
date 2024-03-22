package com.dame.slackde;

import com.dame.slackde.entity.Channel;
import com.dame.slackde.entity.Post;
import com.dame.slackde.entity.User;
import com.dame.slackde.repository.ChannelRepository;
import com.dame.slackde.service.ChannelService;
import com.dame.slackde.service.PostService;
import com.dame.slackde.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
public class SlackDejApplication {

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @Autowired
    ChannelService channelService;

    @Autowired
    ChannelRepository channelRepository;

    public static void main(String[] args) {SpringApplication.run(SlackDejApplication.class, args);}

    @EventListener(ApplicationReadyEvent.class)
    void initApplication() {

        // L'initialisation se déclenche si il n'y a ni channel ni user
        if (channelRepository.count() == 0) {
            System.out.println("// initialisation application //////////////////////////");

            User user1 = new User("mboup", "dame@dame.com");
            user1.setUsername("dame");
//            user1.setPassword("$2a$10$vDkR.U8N0GZzSmteZjYaRuhSu69hWvoBk.3ItX1Rugzlg316M82uy");
            user1.setPassword("123");
            user1.setRoles(Arrays.asList("USER", "ADMIN"));
            userService.addUser(user1);


            Channel channel1 = new Channel();
            channel1.setName("Général");
            channel1.setisDeletable(false);
            channel1.setUser(user1);
            channelService.save(channel1);


            Post post1 = new Post("Bonjour, premier post dans le canal général !", new Date());
            post1.setIdChannel(channel1.getId());
            post1.setIdUser(user1.getId());
            post1.setChannel(channel1);
            post1.setUser(user1);

            postService.addPost(post1);

            System.out.println("Fin initialisation");
        }

    }

}
