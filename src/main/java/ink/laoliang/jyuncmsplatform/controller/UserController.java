package ink.laoliang.jyuncmsplatform.controller;

import ink.laoliang.jyuncmsplatform.domain.User;
import ink.laoliang.jyuncmsplatform.domain.request.UpdatePasswordInfo;
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
    public List<User> addNewUser(@RequestAttribute String USER_ROLE,
                                 @RequestBody User user) {
        return userService.addNewUser(USER_ROLE, user);
    }

    @PutMapping
    public List<User> updateUser(@RequestAttribute String USER_ROLE,
                                 @RequestBody User user) {
        return userService.updateUser(USER_ROLE, user);
    }

    @DeleteMapping
    public List<User> deleteUser(@RequestAttribute String USER_ROLE,
                                 @RequestParam String username) {
        return userService.deleteUser(USER_ROLE, username);
    }

    @PostMapping(value = "/login")
    public LoginUserInfo login(@RequestBody User user) {
        return userService.login(user);
    }

    @PutMapping(value = "/self-info")
    public User updateSelfInfo(@RequestAttribute String USER_ROLE,
                             @RequestBody User user) {
        return userService.updateSelfInfo(USER_ROLE, user);
    }

    @PutMapping(value = "/self-password")
    public User updateSelfPassword(@RequestBody UpdatePasswordInfo updatePasswordInfo){
        return userService.updateSelfPassword(updatePasswordInfo);
    }

    @PutMapping(value = "/reset-password")
    public User resetPassword(@RequestAttribute String USER_ROLE,
                              @RequestBody User user){
        return userService.resetPassword(USER_ROLE, user);
    }
}
