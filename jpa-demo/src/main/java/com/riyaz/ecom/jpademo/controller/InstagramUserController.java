package com.riyaz.ecom.jpademo.controller;

import com.riyaz.ecom.jpademo.dto.InstagramUserDto;
import com.riyaz.ecom.jpademo.exception.ValidationException;
import com.riyaz.ecom.jpademo.mapper.InstagramUserMapper;
import com.riyaz.ecom.jpademo.models.InstagramUser;
import com.riyaz.ecom.jpademo.service.IInstagramUserService;
import com.riyaz.ecom.jpademo.validator.InstagramUserValidator;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class InstagramUserController {

    private final IInstagramUserService userService;
    private final InstagramUserMapper userMapper;
    private final InstagramUserValidator userValidator;

    public InstagramUserController(IInstagramUserService userService,
                                   InstagramUserMapper userMapper,
                                   InstagramUserValidator userValidator) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.userValidator = userValidator;
    }

    @GetMapping
    public List<InstagramUser> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public InstagramUser getUserById(@PathVariable UUID id) {
        return userService.getUserById(id);
    }

    @GetMapping("/search/username")
    public InstagramUser findByUsername(@RequestParam String username) {
        return userService.findByUsername(username);
    }

    @GetMapping("/search/email")
    public InstagramUser findByEmail(@RequestParam String email) {
        return userService.findByEmail(email);
    }

    @PostMapping
    public InstagramUserDto createUser(@RequestBody InstagramUserDto userDto) {
        List<String> errors = userValidator.validate(userDto);
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        InstagramUser user = userMapper.toEntity(userDto);
        InstagramUser savedUser = userService.save(user);
        return userMapper.toDto(savedUser);
    }

    @PutMapping("/{id}")
    public InstagramUser updateUser(@PathVariable UUID id, @RequestBody InstagramUserDto userDto) {
        List<String> errors = userValidator.validate(userDto);
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
//        InstagramUser user = userMapper.toEntity(userDto);
        InstagramUser existingUser = userService.getUserById(id);
        existingUser.setUsername(userDto.getUsername());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setName(userDto.getName());
        return userService.save(existingUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id) {
        InstagramUser user = userService.getUserById(id);
        userService.delete(user);
    }

    @PostMapping("/test")
    public InstagramUserDto createUserTest(@RequestBody InstagramUserDto userDto) {
        // TEMPORARY DEBUG TEST
        InstagramUser user = new InstagramUser();
        user.setName(userDto.getName());         // Hardcode setters to test
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());

        InstagramUser savedUser = userService.save(user);
        return userMapper.toDto(savedUser);
    }
}
