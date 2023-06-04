package com.piggyBank.controller;

import com.piggyBank.entity.Users;
import com.piggyBank.repos.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping
public class RegistrationController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/403")
    public String error403() {
        return "error/403";
    }

    @GetMapping("/registration")
    public String registration(Model model) {

        model.addAttribute("users", new Users());

        return "registration";
    }

    @PostMapping("/registration")
    public String addEmployee(@Valid @ModelAttribute("users") Users users,
                              BindingResult bindingResult,
                              Model model) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errorsMap);

            return "registration";
        }
            Users usersFormDb = usersRepository.findByEmail(users.getEmail());

            if (usersFormDb != null) {
                model.addAttribute("usernameError", "Пользователь существует!");
                return "registration";
            }

            users.setStatus(true);
            users.setPassword(passwordEncoder.encode(users.getPassword()));

            usersRepository.save(users);

            return "redirect:/login";
        }

        @GetMapping("/user")
        public String userAccount(Model model) {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Users user = usersRepository.findByEmail(auth.getName());

            model.addAttribute("user", user);

            return "user";

        }

        @GetMapping("/editUser/{id}")
        public String getUserAccount(@PathVariable("id") Integer id, Model model) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Users users = usersRepository.findByEmail(auth.getName());

            users.setPassword(null);

            if (users.getId() == id) {
                model.addAttribute("users", users);

                return "editUser";
            }
            else {
                model.addAttribute("message", "Не найдено");
                return "home";
            }
        }

        @PatchMapping("/editUser/{id}")
        public String patchUserAccount(@ModelAttribute("users") Users users,
                                       @PathVariable("id") Integer id) {

            users.setPassword(passwordEncoder.encode(users.getPassword()));
            usersRepository.updateUser(id, users.getUsername(), users.getPassword());

            return "redirect:/";
        }
}
