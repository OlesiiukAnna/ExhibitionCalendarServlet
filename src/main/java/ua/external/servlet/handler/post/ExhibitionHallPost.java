package ua.external.servlet.handler.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.external.exceptions.InvalidDataException;
import ua.external.service.interfaces.ExhibitionHallService;
import ua.external.service.interfaces.ExhibitionService;
import ua.external.servlet.handler.ServletHandler;
import ua.external.servlet.handler.helpers.CartStorage;
import ua.external.util.dto.ExhibitionDto;
import ua.external.util.dto.ExhibitionHallDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

import static ua.external.servlet.handler.Paths.EXHIBITION_HALL_JSP;

public class ExhibitionHallPost implements ServletHandler {
    private static final Logger logger = LoggerFactory.getLogger(ExhibitionHallPost.class);

    private ExhibitionHallService<ExhibitionHallDto> exhibitionHallService;
    private ExhibitionService<ExhibitionDto> exhibitionService;

    public ExhibitionHallPost(ExhibitionHallService<ExhibitionHallDto> exhibitionHallService,
                              ExhibitionService<ExhibitionDto> exhibitionService) {
        this.exhibitionHallService = exhibitionHallService;
        this.exhibitionService = exhibitionService;
    }

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("exhibition-hall-id"));
        Optional<ExhibitionHallDto> exhibitionHallDto = exhibitionHallService.getById(id);
        request.setAttribute("exhibitionHall", exhibitionHallDto.get());

        List<ExhibitionDto> exhibitions = exhibitionService.getAllByExhibitionHallId(id);
        request.setAttribute("exhibitions", exhibitions);

        try {
            CartStorage.addToCart(request, exhibitions);
        } catch (InvalidDataException e) {
            logger.info("incorrect input data ", e);
        }
        return EXHIBITION_HALL_JSP;
    }
}
