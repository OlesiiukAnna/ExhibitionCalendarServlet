package ua.external.servlet.handler.get;

import ua.external.service.interfaces.ExhibitionService;
import ua.external.service.interfaces.TicketService;
import ua.external.servlet.handler.ServletHandler;
import ua.external.servlet.handler.helpers.ProcessHomePage;
import ua.external.util.dto.ExhibitionDto;
import ua.external.util.dto.TicketDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.external.servlet.handler.Paths.EMPLOYEE_HOME_PAGE_JSP;

public class EmployeeHomePageGet implements ServletHandler {
    private TicketService<TicketDto> ticketService;
    private ExhibitionService<ExhibitionDto> exhibitionService;

    public EmployeeHomePageGet(TicketService<TicketDto> ticketService,
                               ExhibitionService<ExhibitionDto> exhibitionService) {
        this.ticketService = ticketService;
        this.exhibitionService = exhibitionService;
    }

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        ProcessHomePage.processPage(request, ticketService, exhibitionService);
        return EMPLOYEE_HOME_PAGE_JSP;
    }
}
