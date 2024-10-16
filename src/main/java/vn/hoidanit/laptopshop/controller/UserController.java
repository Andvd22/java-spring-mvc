package vn.hoidanit.laptopshop.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;
import vn.hoidanit.laptopshop.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
        return "admin/user/table-user";
    }

    @RequestMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable long id) {
        System.out.println("check path id = " + id);
        model.addAttribute("id", id);
        model.addAttribute("user", this.userService.getUserById(id));
        return "admin/user/show";// link jsp
    }

    // update
    @RequestMapping("/admin/user/update/{id}")
    public String getUserUpdatePage(Model model, @PathVariable long id) {
        User currentUser = this.userService.getUserById(id);
        model.addAttribute("newUser", currentUser);
        return "admin/user/update";// link jsp
    }

    @PostMapping("/admin/user/update")
    public String getUserUpdate(Model model, @ModelAttribute("newUser") User dovanan) {
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
    @RequestMapping("/admin/user/create") // link tren thanh url
    public String createUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";// link jsp
    }

    @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
    public String createUserPage(Model model, @ModelAttribute("newUser") User dovanan) {
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
    public String getDeleteUser(Model model, @ModelAttribute("newUser") User dovanan) {
        System.out.println("run hereeeeeee");
        this.userService.deleteAUser(dovanan.getId());
        return "redirect:/admin/user";
    }

}
