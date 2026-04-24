/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package springapp.web.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Session;
import org.hibernate.SessionFactory; // Import thêm cái này
import org.springframework.beans.factory.annotation.Autowired; // Import thêm cái này
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springapp.web.model.Users;

/**
 *
 * @author AnhDao
 */
@Controller
@RequestMapping(value = "/admin")
public class PayRateController {

    // CHÚ Ý: Phải đặt trong class (sau dấu ngoặc nhọn mở)
    @Autowired
    private SessionFactory sessionFactory;

    @RequestMapping(value = {"/payrates/list"}, method = RequestMethod.GET)
    public String listUsers(ModelMap model, HttpServletRequest request) {
        Users user = (Users) request.getSession().getAttribute("LOGGEDIN_USER");
        
        if (user == null) {
            model.addAttribute("user", new Users());
            return "redirect:/admin/login.html";
        }

        // Dùng SessionFactory của Spring (Thay cho HibernateUtil cũ)
        Session session = sessionFactory.openSession();
        try {
            // Lấy danh sách PayRates
            List listPayRates = session.createQuery("from PayRates").list();
            model.addAttribute("listPayRates", listPayRates);
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Dùng xong nhớ đóng session
            session.close();
        }

        return "admin/listPayRate";
    }
}