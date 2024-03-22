package fr.moussalli.slackdej;

import fr.moussalli.slackdej.entity.Channel;
import fr.moussalli.slackdej.entity.Post;
import fr.moussalli.slackdej.entity.User;
import fr.moussalli.slackdej.repository.ChannelRepository;
import fr.moussalli.slackdej.repository.PostRepository;
import fr.moussalli.slackdej.repository.UserRepository;
import fr.moussalli.slackdej.service.ChannelService;
import fr.moussalli.slackdej.service.PostService;
import fr.moussalli.slackdej.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class SlackDejApplication {

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @Autowired
    ChannelService channelService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

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


//    @Bean
//    BCryptPasswordEncoder getBCE() {
//        return new BCryptPasswordEncoder();
//    }

}
