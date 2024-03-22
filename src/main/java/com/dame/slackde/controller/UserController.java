package com.dame.slackde.controller;

import com.dame.slackde.service.ChannelService;
import com.dame.slackde.service.PostService;
import com.dame.slackde.service.UserService;
import com.dame.slackde.entity.User;
import com.dame.slackde.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    PostService postService;

    @Autowired
    ChannelService channelService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/public")
    public String helloPublic() {
        return "Hello Autour Du Code public!";
    }

 // GET ALL users
     @GetMapping("users")
     public ResponseEntity<List<User>> getAllUsers(){
         List<User> users = userService.getAll();
         return ResponseEntity.ok(users);
     }

    // GET all emails of users
    @GetMapping("users/emails")
    public ResponseEntity<List<String>> getAllUserEmails() {
        List<String> emails = userService.getAllEmails();
        return ResponseEntity.ok(emails);
    }


    @GetMapping("users/emails/{id}")
    public ResponseEntity<List<String>> getEmail(@PathVariable("id") Integer id) {

        List<String> email = userService.getEmail(id);

        if (email.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(email);
    }

    // POST users
    @PostMapping("users")
    public ResponseEntity<?> createUser(@RequestBody User newUser){

        List<String> allEmails = userService.getAllEmails();

        if (allEmails.contains(newUser.getEmail())) {
            return ResponseEntity.badRequest().body("Email exist déjà !");
        }

         if(newUser.getName()==null || newUser.getName().isBlank() || newUser.getEmail()==null || newUser.getEmail().isBlank())
             return ResponseEntity.badRequest().body("Saisie incomplète!");
         userService.addUser(newUser);
         return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

     // GET user/1
     @GetMapping("users/{id}")
     public ResponseEntity<?> getOneById(@PathVariable("id") Integer id) {
        Optional<User> optional = userService.getOneById(id);
         return optional.isPresent() ? ResponseEntity.ok(optional.get()) : ResponseEntity.badRequest().body("id inexistant");
    }


     // PUT users
    @PutMapping("users/{id}")
    public ResponseEntity<String> putUser(@PathVariable("id") Integer id,@RequestBody User newDataUser){
         Optional<User> optional = userService.getOneById(id);
         if(optional.isEmpty())
             return ResponseEntity.notFound().build();

         User userToUpdate = optional.get();
         userService.replaceUser(userToUpdate, newDataUser);
         return ResponseEntity.ok("Update réalisée");
    }

    // DELETE users
    @DeleteMapping("users/{id}")
    public ResponseEntity<String> deleteOneById(@PathVariable("id") Integer id){
        Optional<User> optional = userService.getOneById(id);

        if (optional.isEmpty())
        return ResponseEntity.badRequest().body("Aucun utilisateur avec cet id !");


        userService.deleteUserById(id);
        return ResponseEntity.ok("Suppression réalisée!");
    }
}





