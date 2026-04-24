package springapp.web.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springapp.web.model.Users;

@Controller
@RequestMapping(value = "/admin")
public class EmployeeController {

    @Autowired
    private SessionFactory sessionFactory;

    @RequestMapping(value = {"/employee/list"}, method = RequestMethod.GET)
    public String listUsers(ModelMap model, HttpServletRequest request) {
        
        // 1. Kiem tra dang nhap
        Users user = (Users) request.getSession().getAttribute("LOGGEDIN_USER");
        if (user == null) {
            model.addAttribute("user", new Users());
            return "redirect:/admin/login.html";
        }

        Session session = sessionFactory.openSession();
        try {
            // 2. Thiet lap phan trang
            int limit = 100; // So dong muon hien thi moi trang
            int page = 1;    // Trang mac dinh la 1
            
            // Lay so trang tu URL (vi du: employee/list.html?page=2)
            if (request.getParameter("page") != null) {
                try {
                    page = Integer.parseInt(request.getParameter("page"));
                } catch (Exception e) {
                    page = 1;
                }
            }
            
            // Tinh vi tri bat dau (Offset)
            // Trang 1 -> bat dau tu 0
            // Trang 2 -> bat dau tu 100
            int offset = (page - 1) * limit;

            // 3. Dem tong so luong ban ghi (De biet co bao nhieu trang)
            Query countQuery = session.createQuery("select count(*) from Employee");
            long totalRecords = (Long) countQuery.uniqueResult();
            
            // Tinh tong so trang (chia lay du)
            int totalPages = (int) Math.ceil((double) totalRecords / limit);

            // 4. Lay du lieu theo trang (QUAN TRONG NHAT)
            List listEmployees = session.createQuery("from Employee")
                                        .setFirstResult(offset) // Bat dau tu dau
                                        .setMaxResults(limit)   // Lay bao nhieu dong
                                        .list();

            // 5. Gui du lieu sang JSP
            model.addAttribute("listEmployees", listEmployees);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("totalRecords", totalRecords);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return "admin/listEmployee";
    }
}