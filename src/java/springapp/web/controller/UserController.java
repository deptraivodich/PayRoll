/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package springapp.web.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired; // Import cái này
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springapp.web.dao.UserDao;
import springapp.web.model.Users;

/**
 *
 * @author KunPC
 */
@Controller
@RequestMapping(value = "/admin")
public class UserController {

    // SỬA: Dùng @Autowired để Spring tự động bơm UserDao vào
    // KHÔNG ĐƯỢC DÙNG: new UserDao() nữa.
    @Autowired
    private UserDao dao;

    @RequestMapping(value = "/user/list")
    public String listUsers(ModelMap model, HttpServletRequest request) {
        Users user = (Users) request.getSession().getAttribute("LOGGEDIN_USER");
        if (user != null) {
            List<Users> listUsers = dao.listUser();
            model.addAttribute("listUsers", listUsers);
            return "admin/listUser";
        } else {
            model.addAttribute("user", new Users());
            return "redirect:/admin/login.html";
        }
    }

    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public String addUsers(@ModelAttribute(value = "user") Users user, ModelMap model, HttpServletRequest request) {
        Users userSession = (Users) request.getSession().getAttribute("LOGGEDIN_USER");
        
        if (userSession != null) {
            // Logic kiểm tra User tồn tại
            if (dao.CheckUser(user.getUserName())) {
                model.addAttribute("error", "Username da ton tai!");
                return "admin/addUser"; // Trả về trang thêm nếu lỗi
            } else {
                // Lưu ý: Code của bạn chưa có hàm dao.save(user), 
                // nhưng tạm thời cứ để logic này chạy listUser trước đã.
                return "redirect:/admin/user/list.html"; 
            }
        } else {
            model.addAttribute("user", new Users());
            return "redirect:/admin/login.html";
        }
    }

    @RequestMapping(value = "/user/add", method = RequestMethod.GET)
    public String formUsers(ModelMap model, HttpServletRequest request) {
        Users user = (Users) request.getSession().getAttribute("LOGGEDIN_USER");
        if (user != null) {
            model.addAttribute("user", new Users());
            return "admin/addUser";
        } else {
            model.addAttribute("user", new Users());
            return "redirect:/admin/login.html";
        }
    }
}