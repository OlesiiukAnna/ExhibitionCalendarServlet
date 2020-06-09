package ua.external.servlet.handler.get;

import ua.external.service.interfaces.ExhibitionHallService;
import ua.external.service.interfaces.ExhibitionService;
import ua.external.servlet.handler.ServletHandler;
import ua.external.util.dto.ExhibitionDto;
import ua.external.util.dto.ExhibitionHallDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

import static ua.external.servlet.handler.Paths.EXHIBITION_HALL_JSP;

public class ExhibitionHallGet implements ServletHandler {
    private ExhibitionHallService<ExhibitionHallDto> exhibitionHallService;
    private ExhibitionService<ExhibitionDto> exhibitionService;

    public ExhibitionHallGet(ExhibitionHallService<ExhibitionHallDto> exhibitionHallService,
                             ExhibitionService<ExhibitionDto> exhibitionService) {
        this.exhibitionHallService = exhibitionHallService;
        this.exhibitionService = exhibitionService;
    }

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        String incomeId = request.getParameter("exhibition-hall-id");
        if (incomeId != null && !incomeId.isBlank()){
            int id = Integer.parseInt(incomeId);
            Optional<ExhibitionHallDto> exhibitionHallDto = exhibitionHallService.getById(id);
            if (exhibitionHallDto.isPresent()){
                request.setAttribute("exhibitionHall", exhibitionHallDto.get());
                List<ExhibitionDto> exhibitions = exhibitionService.getAllByExhibitionHallId(id);
                request.setAttribute("exhibitions", exhibitions);
            }
        }
        return EXHIBITION_HALL_JSP;
    }
}
