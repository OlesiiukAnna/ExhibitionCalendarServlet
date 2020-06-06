package ua.external.servlet.handler.get;

import ua.external.service.interfaces.ExhibitionHallService;
import ua.external.servlet.handler.ServletHandler;
import ua.external.util.dto.ExhibitionHallDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static ua.external.servlet.handler.Paths.REGISTER_NEW_EXHIBITION_JSP;

public class RegisterNewExhibitionGet implements ServletHandler {
    private ExhibitionHallService<ExhibitionHallDto> exhibitionHallService;

    public RegisterNewExhibitionGet(ExhibitionHallService<ExhibitionHallDto> exhibitionHallService) {
        this.exhibitionHallService = exhibitionHallService;
    }

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        List<ExhibitionHallDto> exhibitionHallDtos = exhibitionHallService.getAll();
        request.setAttribute("exhibitionHalls", exhibitionHallDtos);
        return REGISTER_NEW_EXHIBITION_JSP;
    }
}
