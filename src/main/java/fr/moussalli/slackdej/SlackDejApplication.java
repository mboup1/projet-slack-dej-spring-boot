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
import org.springframework.context.event.EventListener;

import java.time.LocalDate;
import java.util.ArrayList;
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


    // @EventListener(ApplicationReadyEvent.class) permet de s'assurer que la méthode initApplication
    // va s'exécuter une fois que la class SlackDejApplication a été instanciée et que le contexte
    // d'application est complètement prêt.
    // Cela signifie que l'utilisation de cet événement garantit que la méthode d'initialisation
    // s'exécute à un point où tous les services, contrôleurs et configurations sont pleinement opérationnels.
    @EventListener(ApplicationReadyEvent.class)
    void initApplication() {

        // L'initialisation se déclenche si il n'y a ni channel ni user
        if (channelRepository.count() == 0) {
            System.out.println("// initialisation application //////////////////////////");

            User user1 = new User("Jeff", "jef@jef.com");
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
