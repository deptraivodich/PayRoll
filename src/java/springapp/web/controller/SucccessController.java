/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package springapp.web.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import springapp.web.model.Users;

/**
 *
 * @author KunPC
 */
@Controller
@RequestMapping(value = "/admin")
public class SucccessController {

    @RequestMapping(value = {"/dashboard"}, method = RequestMethod.GET)
    public ModelAndView viewsDashboard(HttpServletRequest request) {
        // Kiểm tra session đăng nhập
        Users user = (Users) request.getSession().getAttribute("LOGGEDIN_USER");
        
        if (user != null) {
            // Nếu đã đăng nhập -> Vào thẳng Dashboard
            return new ModelAndView("admin/dashboard");
        } else {
            // Nếu chưa đăng nhập -> Redirect về trang Login (Đã sửa cho thống nhất)
            return new ModelAndView("redirect:/admin/login.html");
        }
    }
}