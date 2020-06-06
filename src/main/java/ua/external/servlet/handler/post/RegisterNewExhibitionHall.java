package ua.external.servlet.handler.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.external.exceptions.SuchExhibitionHallIsAlreadyExistsException;
import ua.external.service.interfaces.ExhibitionHallService;
import ua.external.servlet.handler.ServletHandler;
import ua.external.util.dto.ExhibitionHallDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.external.servlet.handler.Paths.REGISTER_NEW_EXHIBITION_HALL_JSP;

public class RegisterNewExhibitionHall implements ServletHandler {
    private static final Logger logger = LoggerFactory.getLogger(RegisterNewExhibitionHall.class);

    private ExhibitionHallService<ExhibitionHallDto> exhibitionHallService;

    public RegisterNewExhibitionHall(ExhibitionHallService<ExhibitionHallDto> exhibitionHallService) {
        this.exhibitionHallService = exhibitionHallService;
    }

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        ExhibitionHallDto exhibitionHallDto = new ExhibitionHallDto();
        String name = request.getParameter("hall-name");
        String capacity = request.getParameter("hall-capacity");
        if (name == null || name.isBlank() || capacity == null || capacity.isBlank()) {
            request.setAttribute("isInvalidData", true);
            return REGISTER_NEW_EXHIBITION_HALL_JSP;
        } else {
            exhibitionHallDto.setName(name);
            exhibitionHallDto.setAllowableNumberOfVisitorsPerDay(Integer.parseInt(capacity));
        }
        boolean isSaved = false;
        try {
            isSaved = exhibitionHallService.save(exhibitionHallDto);
        } catch (SuchExhibitionHallIsAlreadyExistsException e) {
            request.setAttribute("hallNotRegistered", true);
            logger.warn("Such exhibition hall is already exists", e);
        }
        if (isSaved) {
            request.setAttribute("hallRegistered", true);
        }
        return REGISTER_NEW_EXHIBITION_HALL_JSP;
    }
}
