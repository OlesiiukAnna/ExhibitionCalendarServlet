package ua.external.servlet.handler.get;

import ua.external.service.interfaces.ExhibitionService;
import ua.external.servlet.handler.ServletHandler;
import ua.external.util.dto.ExhibitionDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static ua.external.servlet.handler.Paths.EXHIBITIONS_JSP;

public class ExhibitionsGet implements ServletHandler {
    private ExhibitionService<ExhibitionDto> exhibitionService;

    public ExhibitionsGet(ExhibitionService<ExhibitionDto> exhibitionService) {
        this.exhibitionService = exhibitionService;
    }

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        List<ExhibitionDto> exhibitions = exhibitionService.getAll();
        request.setAttribute("exhibitions", exhibitions);
        return EXHIBITIONS_JSP;
    }
}
