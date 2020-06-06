package ua.external.servlet.handler.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.external.exceptions.InvalidDataException;
import ua.external.exceptions.exhibition.InvalidDateException;
import ua.external.exceptions.exhibition.PriceBelowZeroException;
import ua.external.service.interfaces.ExhibitionService;
import ua.external.servlet.handler.ServletHandler;
import ua.external.util.dto.ExhibitionDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

import static ua.external.servlet.handler.Paths.REGISTER_NEW_EXHIBITION_JSP;

public class RegisterNewExhibitionPost implements ServletHandler {
    private static final Logger logger = LoggerFactory.getLogger(RegisterNewExhibitionPost.class);

    private ExhibitionService<ExhibitionDto> exhibitionService;

    public RegisterNewExhibitionPost(ExhibitionService<ExhibitionDto> exhibitionService) {
        this.exhibitionService = exhibitionService;
    }

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        ExhibitionDto exhibitionDto = new ExhibitionDto();
        String name = request.getParameter("exhibition-name");
        String description = request.getParameter("exhibition-description");
        String beginDate = request.getParameter("exhibition-begin-date");
        String endDate = request.getParameter("exhibition-end-date");
        String fullTicketPrice = request.getParameter("exhibition-full-ticket-price");
        String exhibitionHallId = request.getParameter("exhibition-hall-id");

        if (name == null || description == null || fullTicketPrice == null ||
                name.isBlank() || description.isBlank() || fullTicketPrice.isBlank()) {
            request.setAttribute("exhibitionRegistered", false);
            request.setAttribute("isInvalidData", true);
            return REGISTER_NEW_EXHIBITION_JSP;
        } else {
            exhibitionDto.setName(name);
            exhibitionDto.setDescription(description);
            exhibitionDto.setBeginDate(LocalDate.parse(beginDate));
            exhibitionDto.setEndDate(LocalDate.parse(endDate));
            exhibitionDto.setFullTicketPrice(Integer.parseInt(fullTicketPrice));
            exhibitionDto.setExhibitionHallId(Integer.parseInt(exhibitionHallId));
        }

        boolean isSaved = false;
        try {
            isSaved = exhibitionService.save(exhibitionDto);
        } catch (InvalidDateException e) {
            request.setAttribute("isDatesWrong", true);
            logger.warn("Invalid input dates", e);
        } catch (PriceBelowZeroException e) {
            logger.warn("Wrong price", e);
        } catch (InvalidDataException e) {
            request.setAttribute("isInvalidData", true);
            logger.warn("Invalid input data", e);
        }
        if (isSaved) {
            request.setAttribute("exhibitionRegistered", true);
        }
        return REGISTER_NEW_EXHIBITION_JSP;
    }
}
