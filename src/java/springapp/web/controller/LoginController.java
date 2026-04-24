package springapp.web.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute; // Import thêm cái này
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springapp.web.dao.UserDao;
import springapp.web.model.Users;

@Controller
@RequestMapping(value = "/admin")
public class LoginController {
    

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap model) {
        // Chỉ giữ lại dòng này để tạo form
        model.addAttribute("user", new Users());
        
        // ĐÃ XÓA ĐOẠN LOOP IN DỮ LIỆU ĐỂ TRÁNH TREO MÁY
        
        return "login"; 
    }

    
    // SỬA: Thêm @ModelAttribute("user") để ép tên biến cho đúng ý JSP
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String proccessLogin(@ModelAttribute("user") Users user, ModelMap model, HttpServletRequest request) {
        
        if (userDao.Login(user.getUserName(), user.getPassword())) {
            request.getSession().setAttribute("LOGGEDIN_USER", user);
            return "redirect:dashboard.html";
        } else {
            model.addAttribute("error", "Sai tai khoan hoac mat khau!");
            
            // QUAN TRỌNG: Dòng này không cần thiết nếu đã dùng @ModelAttribute ở trên,
            // nhưng mình cứ để lại hoặc thêm vào cho chắc chắn 100%.
            // Nó giúp giữ lại cái tên đăng nhập người dùng vừa gõ (không bắt gõ lại).
            model.addAttribute("user", user); 
            
            return "login";
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(ModelMap model, HttpServletRequest request) {
        request.getSession().removeAttribute("LOGGEDIN_USER");
        return "redirect:login.html";
    }
}