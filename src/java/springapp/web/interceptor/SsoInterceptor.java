package springapp.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class SsoInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        
        // Bỏ qua các đường dẫn liên quan đến đăng nhập để tránh lặp vô hạn
        if (uri.contains("login") || uri.contains("sso") || uri.contains("logout")) {
            return true;
        }

        Object user = request.getSession().getAttribute("LOGGEDIN_USER");
        
        if (user == null) {
            // CHƯA ĐĂNG NHẬP -> Đẩy sang C# HR
            // Sửa lại Cổng (Port) của máy em cho chính xác (ví dụ HR chạy port 5000, Java chạy 8080)
            String javaSsoReceiverUrl = "http://localhost:8080/springapp/admin/sso";
            String csharpLoginUrl = "http://localhost:5000/Login?returnUrl=" + javaSsoReceiverUrl;
            
            response.sendRedirect(csharpLoginUrl);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {}

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {}
}