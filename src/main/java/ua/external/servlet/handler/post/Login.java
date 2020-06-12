package ua.external.servlet.handler.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.external.service.interfaces.UserService;
import ua.external.servlet.handler.ServletHandler;
import ua.external.util.PasswordEncryptor;
import ua.external.util.dto.DataForTicketOrder;
import ua.external.util.dto.UserDto;
import ua.external.util.enums.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.util.Objects.nonNull;
import static ua.external.servlet.handler.Paths.LOGIN_JSP;

public class Login implements ServletHandler {
    private static final Logger logger = LoggerFactory.getLogger(Login.class);

    private Map<Role, String> menu;
    private List<DataForTicketOrder> dataForTicketOrders;

    private UserService<UserDto> userService;

    public Login(UserService<UserDto> userService, Map<Role, String> menu) {
        this.userService = userService;
        this.menu = menu;
        dataForTicketOrders = new CopyOnWriteArrayList<>();
    }

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        final String email = request.getParameter("email");
        final String password = request.getParameter("password");

        final HttpSession session = request.getSession();

        if (nonNull(session) &&
                nonNull(session.getAttribute("email")) &&
                nonNull(session.getAttribute("password"))) {
            final Role role = (Role) session.getAttribute("role");
            return menu.get(role);
        }

        Optional<UserDto> userOptional = userService.getByEmail(email);
        UserDto userDto = new UserDto();
        boolean isAccessGranted = false;
        if (userOptional.isPresent()) {
            String inputPass = null;
            try {
                inputPass = PasswordEncryptor.encrypt(password);
            } catch (NoSuchAlgorithmException e) {
                logger.error("Missed algorithm ", e);
            }
            isAccessGranted = userService.isPasswordCorrectForUser(inputPass, userOptional.get().getPassword());
            userDto = userOptional.get();
        }
        if (isAccessGranted) {
            request.getSession().setAttribute("userInSystem", true);
            request.getSession().setAttribute("user", userDto);
            request.getSession().setAttribute("email", userDto.getEmail());
            request.getSession().setAttribute("password", userDto.getPassword());
            request.getSession().setAttribute("role", userDto.getRole());
            request.getSession().setAttribute("dataForTicketsOrder", dataForTicketOrders);
            return menu.get(userDto.getRole());
        } else {
            logger.info("User entered wrong email or password");
            request.setAttribute("wrongInput", true);
            return LOGIN_JSP;
        }
    }
}
