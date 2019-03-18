package ink.laoliang.jyuncmsplatform.controller;

import ink.laoliang.jyuncmsplatform.domain.User;
import ink.laoliang.jyuncmsplatform.domain.request.UpdateUserInfo;
import ink.laoliang.jyuncmsplatform.domain.response.LoginUserInfo;
import ink.laoliang.jyuncmsplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUserList() {
        return userService.getUserList();
    }

    @PostMapping
    public User addNewUser(@RequestAttribute String USER_ROLE,
                           @RequestBody User user) {
        return userService.addNewUser(USER_ROLE, user);
    }

    @PutMapping
    public User updateUser(@RequestAttribute String USER_ROLE,
                           @RequestBody UpdateUserInfo updateUserInfo) {
        return userService.updateUser(USER_ROLE, updateUserInfo);
    }

    @DeleteMapping
    public void deleteUser(@RequestAttribute String USER_ROLE,
                           @RequestParam String username) {
        userService.deleteUser(USER_ROLE, username);
    }

    @PostMapping(value = "/login")
    public LoginUserInfo login(@RequestBody User user) {
        return userService.login(user);
    }
}
