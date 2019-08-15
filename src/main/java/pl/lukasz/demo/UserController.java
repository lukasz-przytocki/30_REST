package pl.lukasz.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/api/user/{id}")
    public ResponseEntity getUser(@PathVariable Long id) {

        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/api/users")
    public List<User> getUsers() {

        List<User> users = userRepository.findAll();
        return users;
    }

    @PostMapping("/api/users")
    public void addUser(@RequestBody User user){
        userRepository.save(user) ;
    }

    @PutMapping("api/user/{id}")
    public ResponseEntity <User> updateUser(@PathVariable Long id, User user){
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            User userFormDB = userOptional.get();
            userFormDB.setFirstName((user.getFirstName()));
            userFormDB.setLastName((user.getLastName()));
            userRepository.save(userFormDB);
            return ResponseEntity.ok(userFormDB);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/api/user/{id}")
    public void delete(@PathVariable Long id){
        userRepository.deleteById(id);

    }
}