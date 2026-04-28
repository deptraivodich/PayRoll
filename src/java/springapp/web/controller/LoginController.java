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
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/admin")
public class LoginController {
    
    private static final String SECRET_KEY = "System_Integration_Practice_Secret_Key_2026";
    // Chú ý: Đổi port 5000 thành port thực tế của ứng dụng C#
    private static final String HR_SSO_URL = "http://localhost:19335/Login/Sso";
    
    // 1. Hàm được gọi khi bấm nút "Chuyển sang HR"
    @RequestMapping(value = "/goToHr.html", method = RequestMethod.GET)
    public String goToHr(HttpServletRequest request) {
        Users user = (Users) request.getSession().getAttribute("LOGGEDIN_USER");
        if (user == null) {
            return "redirect:login.html"; 
        }

        // Tạo vé (Token) để mang sang C#
        String token = Jwts.builder()
                .claim("userName", user.getUserName())
                .setExpiration(new java.util.Date(System.currentTimeMillis() + 300000)) // 5 phút
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes(java.nio.charset.StandardCharsets.UTF_8))
                .compact();

        // Chuyển hướng sang C# kèm theo vé
        return "redirect:" + HR_SSO_URL + "?token=" + token;
    }

    // 2. Hàm đón vé (Token) từ hệ thống C# gửi sang
    @RequestMapping(value = "/sso.html", method = RequestMethod.GET)
    public String ssoLogin(@RequestParam("token") String token, HttpServletRequest request) {
        try {
            io.jsonwebtoken.Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY.getBytes(java.nio.charset.StandardCharsets.UTF_8))
                    .parseClaimsJws(token)
                    .getBody();

            String userName = (String) claims.get("userName");

            // Vé hợp lệ -> Tự động tạo Session đăng nhập
            Users ssoUser = new Users();
            ssoUser.setUserName(userName);
            request.getSession().setAttribute("LOGGEDIN_USER", ssoUser);

            return "redirect:dashboard.html"; // Trỏ về trang chủ của Spring
        } catch (Exception e) {
            return "redirect:login.html?error=invalid_token";
        }
    }
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