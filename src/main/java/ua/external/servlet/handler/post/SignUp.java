package ua.external.servlet.handler.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.external.exceptions.user.SuchUserIsAlreadyExistsException;
import ua.external.exceptions.user.InvalidUserException;
import ua.external.service.interfaces.UserService;
import ua.external.servlet.handler.ServletHandler;
import ua.external.util.dto.UserDto;
import ua.external.util.enums.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;

import static ua.external.servlet.handler.Paths.LOGIN_PAGE;
import static ua.external.servlet.handler.Paths.SIGN_UP_JSP;

public class SignUp implements ServletHandler {
    private static final Logger logger = LoggerFactory.getLogger(SignUp.class);

    private UserService<UserDto> userService;

    public SignUp(UserService<UserDto> userService) {
        this.userService = userService;
    }

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        UserDto userDto = new UserDto();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String firstName = request.getParameter("first-name");
        String lastName = request.getParameter("last-name");
        String phone = request.getParameter("phone");
        String role = request.getParameter("role");
        if (email == null || password == null ||
                firstName == null || lastName == null ||
                phone == null) {
            return SIGN_UP_JSP;
        } else {
            userDto.setEmail(email);
            userDto.setPassword(password);
            userDto.setFirstName(firstName);
            userDto.setLastName(lastName);
            userDto.setPhone(phone);
            userDto.setRole(Role.valueOf(role));
        }
        boolean isSaved = false;
        try {
            isSaved = userService.save(userDto);
        } catch (SuchUserIsAlreadyExistsException e) {
            request.setAttribute("isUserExists", true);
            logger.warn("Such user is already exists", e);
        } catch (InvalidUserException e) {
            request.setAttribute("isInvalidData", true);
            logger.warn("Invalid input data", e);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        String result;
        if (isSaved) {
            result = "redirect:" + LOGIN_PAGE;
        } else {
            result = SIGN_UP_JSP;
        }
        return result;
    }
}
