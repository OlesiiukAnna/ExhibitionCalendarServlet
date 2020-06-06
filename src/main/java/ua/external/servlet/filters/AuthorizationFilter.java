package ua.external.servlet.filters;

import ua.external.util.dto.UserDto;
import ua.external.util.enums.Role;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static ua.external.servlet.handler.Paths.ERROR_JSP;

public class AuthorizationFilter implements Filter {

    public void init(FilterConfig config) throws ServletException {

    }

    public void doFilter(ServletRequest req,
                         ServletResponse resp,
                         FilterChain chain) throws ServletException, IOException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) resp;
        final HttpSession session = request.getSession();
        String servletPath = request.getServletPath();

        UserDto userDto = (UserDto) session.getAttribute("user");
        if (servletPath.contains("/employee") && !userDto.getRole().equals(Role.EMPLOYEE)) {
            response.sendRedirect(ERROR_JSP);
        } else if (servletPath.contains("/visitor") && !userDto.getRole().equals(Role.VISITOR)) {
            response.sendRedirect(ERROR_JSP);
        }

        chain.doFilter(req, resp);
    }

    public void destroy() {

    }

}

