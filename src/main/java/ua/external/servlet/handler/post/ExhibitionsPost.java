package ua.external.servlet.handler.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.external.exceptions.InvalidDataException;
import ua.external.service.interfaces.ExhibitionService;
import ua.external.servlet.handler.ServletHandler;
import ua.external.servlet.handler.helpers.CartStorage;
import ua.external.util.dto.ExhibitionDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static ua.external.servlet.handler.Paths.EXHIBITIONS_JSP;

public class ExhibitionsPost implements ServletHandler {
    private static final Logger logger = LoggerFactory.getLogger(ExhibitionsPost.class);

    private ExhibitionService<ExhibitionDto> exhibitionService;

    public ExhibitionsPost(ExhibitionService<ExhibitionDto> exhibitionService) {
        this.exhibitionService = exhibitionService;
    }

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        List<ExhibitionDto> exhibitions = exhibitionService.getAll();
        request.setAttribute("exhibitions", exhibitions);
        try {
            CartStorage.addToCart(request, exhibitions);
        } catch (InvalidDataException e) {
            logger.info("incorrect input data ", e);
        }
        return EXHIBITIONS_JSP;
    }
}
