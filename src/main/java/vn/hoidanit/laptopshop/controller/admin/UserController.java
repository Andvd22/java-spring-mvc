package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;
import java.io.*;
import java.io.IOException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import jakarta.servlet.ServletContext;
import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;
import vn.hoidanit.laptopshop.service.UploadService;
import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class UserController {

    private final UserService userService;
    private final UploadService uploadService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, UploadService uploadService,
            PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.uploadService = uploadService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        List<User> arrUsers = this.userService.getAllUsersByEmail("dovanan774@gmail.com");
        System.out.println(arrUsers);
        model.addAttribute("anric", "test");
        model.addAttribute("hoidanit", "from controller with model");
        return "hello";
    }

    @RequestMapping("/admin/user")
    public String getUserPage(Model model) {
        List<User> users = this.userService.getAllUsers();
        System.out.println(">>> check users: " + users);
        model.addAttribute("users1", users);
        // return "admin/user/table-user";
        return "admin/user/show";
    }

    @RequestMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable long id) {
        System.out.println("check path id = " + id);
        model.addAttribute("id", id);
        model.addAttribute("user", this.userService.getUserById(id));
        // return "admin/user/show";// link jsp
        return "admin/user/detail";// link jsp
    }

    // update
    @RequestMapping("/admin/user/update/{id}")
    public String getUserUpdatePage(Model model, @PathVariable long id) {
        User currentUser = this.userService.getUserById(id);
        model.addAttribute("newUser", currentUser);
        return "admin/user/update";// link jsp
    }

    @PostMapping("/admin/user/update")
    public String handleUserUpdate(Model model, @ModelAttribute("newUser") User dovanan) {
        User currentUser = this.userService.getUserById(dovanan.getId());
        if (currentUser != null) {
            currentUser.setAddress(dovanan.getAddress());
            currentUser.setFullName(dovanan.getFullName());
            currentUser.setPhone(dovanan.getPhone());
            this.userService.handelSaveUser(currentUser);
        }
        return "redirect:/admin/user";
    }

    // create
    @GetMapping("/admin/user/create") // link tren thanh url
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";// link jsp
    }

    @PostMapping(value = "/admin/user/create")
    public String handleCreateUser(Model model,
            @ModelAttribute("newUser") @Valid User dovanan,
            BindingResult newUserbindingResult,
            @RequestParam("hoidanitFile") MultipartFile file) {

        // validate
        List<FieldError> errors = newUserbindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(error.getField() + " - " + error.getDefaultMessage());
        }

        if (newUserbindingResult.hasErrors()) {
            return "/admin/user/create";
        }
        // neu hop le
        String avatar = this.uploadService.handelSaveUploadFile(file, "avatar");
        String hashPassword = this.passwordEncoder.encode(dovanan.getPassword());

        dovanan.setAvatar(avatar);
        dovanan.setPassword(hashPassword);
        dovanan.setRole(this.userService.getRoleByName(dovanan.getRole().getName()));// lay id ???
        // System.out.println("run here" + dovanan);
        this.userService.handelSaveUser(dovanan);
        return "redirect:/admin/user";// redirect ve link url
    }

    // delete
    @GetMapping("/admin/user/delete/{id}")
    public String getUserDeletePage(Model model, @PathVariable long id) {
        model.addAttribute("newUser", new User());
        // User user = new User();
        // user.setId(id);
        // model.addAttribute("newUser", user);
        model.addAttribute("id", id);
        return "admin/user/delete";// link jsp
    }

    @PostMapping("/admin/user/delete")
    public String handleDeleteUser(Model model, @ModelAttribute("newUser") User dovanan) {
        System.out.println("run hereeeeeee");
        this.userService.deleteAUser(dovanan.getId());
        return "redirect:/admin/user";
    }

}
