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

import static ua.external.servlet.handler.Paths.REGISTER_NEW_EMPLOYEE_JSP;

public class RegisterNewEmployee implements ServletHandler {
    private static final Logger logger = LoggerFactory.getLogger(RegisterNewEmployee.class);

    private UserService<UserDto> userService;

    public RegisterNewEmployee(UserService<UserDto> userService) {
        this.userService = userService;
    }

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        UserDto userDto = new UserDto();
        String email = request.getParameter("employee-email");
        String password = request.getParameter("employee-password");
        String firstName = request.getParameter("employee-first-name");
        String lastName = request.getParameter("employee-last-name");
        String phone = request.getParameter("employee-phone");
        String role = request.getParameter("employee-role");
        if (email == null || password == null ||
                firstName == null || lastName == null ||
                phone == null) {
            request.setAttribute("isInvalidData", true);
            return REGISTER_NEW_EMPLOYEE_JSP;
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
            request.setAttribute("isEmployeeExists", true);
            logger.warn("Such user is already exists", e);
        } catch (InvalidUserException e) {
            request.setAttribute("isInvalidData", true);
            logger.warn("Invalid input data", e);
        } catch (NoSuchAlgorithmException e) {
            logger.error("Password missed algorithm", e);
        }

        if (isSaved) {
            request.setAttribute("employeeRegistered", true);
        }
        return REGISTER_NEW_EMPLOYEE_JSP;
    }
}
