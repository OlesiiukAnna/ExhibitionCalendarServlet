package ua.external.servlet.handler.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.external.service.interfaces.UserService;
import ua.external.servlet.handler.ServletHandler;
import ua.external.util.PasswordEncryptor;
import ua.external.util.dto.UserDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;

import static ua.external.servlet.handler.Paths.DELETE_USER_JSP;
import static ua.external.servlet.handler.Paths.INDEX_PAGE;

public class DeleteUser implements ServletHandler {
    private static final Logger logger = LoggerFactory.getLogger(DeleteUser.class);

    private UserService<UserDto> userService;

    public DeleteUser(UserService<UserDto> userService) {
        this.userService = userService;
    }

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        String passwordField1 = request.getParameter("password1");
        String passwordField2 = request.getParameter("password2");

        UserDto user = (UserDto) request.getSession().getAttribute("user");
        boolean isNullPresent = isNullFieldsPresent(email, passwordField1, passwordField2);
        boolean inputPasswordsEquals = passwordField1.equals(passwordField2);
        boolean isDataValid = isInputDataValid(email, passwordField1, user);
        if (isNullPresent || !inputPasswordsEquals || !isDataValid) {
            request.setAttribute("wrongInput", true);
            return DELETE_USER_JSP;
        }

        if (userService.delete(user.getId())) {
            request.getSession().removeAttribute("user");
            request.getSession().removeAttribute("role");
            request.getSession().invalidate();
            return "redirect:" + INDEX_PAGE;
        }
        return DELETE_USER_JSP;
    }

    private boolean isNullFieldsPresent(String email, String passwordField1, String passwordField2) {
        return email == null || passwordField1 == null ||
                passwordField2 == null;
    }

    private boolean isInputDataValid(String email, String inputPassword, UserDto user) {
        String pass;
        try {
            pass = PasswordEncryptor.encrypt(inputPassword);
        } catch (NoSuchAlgorithmException e) {
            logger.error("Something wrong with encryptor", e);
            return false;
        }
        return user.getEmail().equals(email) && pass.equals(user.getPassword());
    }
}
