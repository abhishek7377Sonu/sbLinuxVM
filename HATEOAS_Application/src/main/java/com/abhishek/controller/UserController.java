package com.abhishek.controller;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;

import com.abhishek.Exception.UserNotFoundException;
import com.abhishek.entity.User_Tab;
import com.abhishek.repo.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    public EntityModel<User_Tab> getUser(@PathVariable Long id) {
        User_Tab user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));

        return EntityModel.of(user,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUser(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getAllUsers()).withRel("all-users"));
    }


    @GetMapping
    public List<EntityModel<User_Tab>> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> EntityModel.of(user,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUser(user.getId())).withSelfRel()))
                .collect(Collectors.toList());
    }

    @PostMapping
    public User_Tab createUser(@RequestBody User_Tab user) {
        return userRepository.save(user);
    }
}
