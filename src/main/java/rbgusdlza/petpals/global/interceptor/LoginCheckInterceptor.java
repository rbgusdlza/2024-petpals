package rbgusdlza.petpals.global.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession(false);

        if (doesUserLogin(session)) {
            response.sendRedirect("/member/login?redirectURL=" + requestURI);
            return false;
        }
        return true;
    }

    private boolean doesUserLogin(HttpSession session) {
        return session == null || session.getAttribute("id") == null;
    }
}
