package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class MainController {
    private final UserService userService;
    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    //@RequestMapping(value = { "/usersList" }, method = RequestMethod.GET)
    @GetMapping()
    //Возвращаем список из людей
    public String allUsers (Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("user.roles", users);
        return "admin";
    }
    @GetMapping("/{id}")
    // Получим одного человека из ДАО и передадим в отображение
    public String showById (@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "users";
    }
    @GetMapping("/create")
    public String newUserForm (@ModelAttribute("user") User user, Model model) {
        List<Role> listRoles = userService.listRoles();
        model.addAttribute("listRoles", listRoles);
        //model.addAttribute("user", new User());
        return "create";
    }
    @PostMapping("/create")
    public String create(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/users";
    }
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
    @GetMapping("/update/{id}")
    public String edit (@PathVariable("id") long id,Model model) {
        User user = userService.getUser(id);
        List<Role> listRoles = userService.listRoles();
        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        return "update";
    }
    @PostMapping ("/update")
    public String editUsers(User user) {
        userService.addUser(user);//addUser(user)
        return "redirect:/admin";
    }
}
