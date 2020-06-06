package ua.external.configuration;

import ua.external.data.dao.impl.ExhibitionDaoImpl;
import ua.external.data.dao.impl.ExhibitionHallDaoImpl;
import ua.external.data.dao.impl.TicketDaoImpl;
import ua.external.data.dao.impl.UserDaoImpl;
import ua.external.data.entity.Exhibition;
import ua.external.data.entity.ExhibitionHall;
import ua.external.data.entity.Ticket;
import ua.external.data.entity.User;
import ua.external.service.impl.ExhibitionHallServiceImpl;
import ua.external.service.impl.ExhibitionServiceImpl;
import ua.external.service.impl.TicketServiceImpl;
import ua.external.service.impl.UserServiceImpl;
import ua.external.service.interfaces.ExhibitionHallService;
import ua.external.service.interfaces.ExhibitionService;
import ua.external.service.interfaces.TicketService;
import ua.external.service.interfaces.UserService;
import ua.external.servlet.handler.ServletHandler;
import ua.external.servlet.handler.get.*;
import ua.external.servlet.handler.post.*;
import ua.external.util.dto.ExhibitionDto;
import ua.external.util.dto.ExhibitionHallDto;
import ua.external.util.dto.TicketDto;
import ua.external.util.dto.UserDto;
import ua.external.util.dto.mappers.*;
import ua.external.util.enums.Role;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static ua.external.servlet.handler.Paths.*;

public class Configuration {
    //    private final static String CREATE_TABLES_SCRIPT = "src/main/resources/sql/create_tables.sql";
//    private final static String FILL_IN_TABLES_WITH_TEST_DATA = "src/main/resources/sql/fill_in_tables.sql";
    private final URL CREATE_TABLES_SCRIPT = this.getClass().getClassLoader().getResource("./classes/sql/create_tables.sql");
    private final URL FILL_IN_TABLES_WITH_TEST_DATA = this.getClass().getClassLoader().getResource("./classes/sql/fill_in_tables.sql");

    private Map<String, ServletHandler> getHandlers;
    private Map<String, ServletHandler> postHandlers;

    private static UserDaoImpl userDao;
    private static ExhibitionDaoImpl exhibitionDao;
    private static ExhibitionHallDaoImpl exhibitionHallDao;
    private static TicketDaoImpl ticketDao;

    private static DtoMapper<UserDto, User> userDtoMapper;
    private static DtoMapper<ExhibitionHallDto, ExhibitionHall> exhibitionHallDtoMapper;
    private static DtoMapper<ExhibitionDto, Exhibition> exhibitionDtoMapper;
    private static DtoMapper<TicketDto, Ticket> ticketDtoMapper;

    private static UserService<UserDto> userService;
    private static ExhibitionHallService<ExhibitionHallDto> exhibitionHallService;
    private static ExhibitionService<ExhibitionDto> exhibitionService;
    private static TicketService<TicketDto> ticketService;

    private Map<Role, String> loginMenu;

    public Configuration() {
        init();
    }

    private void init() {
//        SqlParser.executeScriptUsingStatement(String.valueOf(CREATE_TABLES_SCRIPT), DBCPDataSource.getDataSource());
//        SqlParser.executeScriptUsingStatement(String.valueOf(FILL_IN_TABLES_WITH_TEST_DATA), DBCPDataSource.getDataSource());
        getHandlers = new HashMap<>();
        postHandlers = new HashMap<>();
        loginMenu = new HashMap<>();
        initDaos();
        initDtoMappers();
        initServices();
        initGetHandlers();
        initPostHandlers();
        initLoginMenu();
    }

    private void initDaos() {
        userDao = DaoFactory.getInstance().createUserDao();
        exhibitionHallDao = DaoFactory.getInstance().createExhibitionHallDao();
        exhibitionDao = DaoFactory.getInstance().createExhibitionDao();
        ticketDao = DaoFactory.getInstance().createTicketDao();
    }

    private void initDtoMappers() {
        userDtoMapper = new UserMapper();
        exhibitionHallDtoMapper = new ExhibitionHallMapper();
        exhibitionDtoMapper = new ExhibitionMapper();
        ticketDtoMapper = new TicketMapper();
    }

    private void initServices() {
        userService = new UserServiceImpl(userDao, userDtoMapper);
        exhibitionHallService = new ExhibitionHallServiceImpl(exhibitionHallDao, exhibitionHallDtoMapper);
        exhibitionService = new ExhibitionServiceImpl(exhibitionDao, exhibitionHallDao, exhibitionDtoMapper);
        ticketService = new TicketServiceImpl(ticketDao, exhibitionDao, userDao, ticketDtoMapper);
    }

    private void initGetHandlers() {
        getHandlers.put(INDEX_PAGE, (req, res) -> INDEX_JSP);
        getHandlers.put(LOGIN_PAGE, (req, res) -> LOGIN_JSP);
        getHandlers.put(SIGN_UP_PAGE, (req, res) -> SIGN_UP_JSP);
        getHandlers.put(LOGOUT_PAGE, new Logout());
        getHandlers.put(DELETE_USER_PAGE, (req, res) -> DELETE_USER_JSP);

        getHandlers.put(VISITOR_HOME_PAGE_PAGE, new VisitorHomePageHandlerGet(ticketService, exhibitionService));
        getHandlers.put(EMPLOYEE_HOME_PAGE_PAGE, new EmployeeHomePageGet(ticketService, exhibitionService));

        getHandlers.put(REGISTER_NEW_EMPLOYEE_PAGE, (req, res) -> REGISTER_NEW_EMPLOYEE_JSP);
        getHandlers.put(REGISTER_NEW_EXHIBITION_HALL_PAGE, (req, res) -> REGISTER_NEW_EXHIBITION_HALL_JSP);
        getHandlers.put(REGISTER_NEW_EXHIBITION_PAGE, new RegisterNewExhibitionGet(exhibitionHallService));

        getHandlers.put(CONFIRM_TICKETS_PAGE, new TicketsGet(ticketService, userService, exhibitionService));

        getHandlers.put(EXHIBITIONS_PAGE, new ExhibitionsGet(exhibitionService));
        getHandlers.put(EXHIBITION_HALLS_PAGE, new ExhibitionHalls(exhibitionHallService));
        getHandlers.put(EXHIBITION_HALL_PAGE, new ExhibitionHallGet(exhibitionHallService, exhibitionService));

        getHandlers.put(CART_PAGE, new CartGet());
        getHandlers.put(CART_DELETE_TICKET_ACTION, new CartPostDeleteTicketHandler());
    }

    private void initPostHandlers() {
        postHandlers.put(INDEX_PAGE, new Login(userService, loginMenu));
        postHandlers.put(LOGIN_PAGE, new Login(userService, loginMenu));
        postHandlers.put(SIGN_UP_PAGE, new SignUp(userService));
        postHandlers.put(DELETE_USER_PAGE, new DeleteUser(userService));

        postHandlers.put(VISITOR_HOME_PAGE_PAGE, new VisitorHomePageHandlerPost(ticketService, exhibitionService));
        postHandlers.put(EMPLOYEE_HOME_PAGE_PAGE, new EmployeeHomePagePost(ticketService, exhibitionService));

        postHandlers.put(EXHIBITIONS_PAGE, new ExhibitionsPost(exhibitionService));
        postHandlers.put(EXHIBITION_HALL_PAGE, new ExhibitionHallPost(exhibitionHallService, exhibitionService));

        postHandlers.put(REGISTER_NEW_EMPLOYEE_PAGE, new RegisterNewEmployee(userService));
        postHandlers.put(REGISTER_NEW_EXHIBITION_HALL_PAGE, new RegisterNewExhibitionHall(exhibitionHallService));
        postHandlers.put(REGISTER_NEW_EXHIBITION_PAGE, new RegisterNewExhibitionPost(exhibitionService));

        postHandlers.put(CART_PAGE, new CartPost(ticketService));
        postHandlers.put(CART_SAVE_ACTION, new CartPost(ticketService));
        postHandlers.put(CART_DELETE_TICKET_ACTION, new CartPostDeleteTicketHandler());

        postHandlers.put(CONFIRM_TICKETS_PAGE, new TicketsPost(ticketService, userService, exhibitionService));
    }

    private void initLoginMenu() {
        loginMenu.put(Role.EMPLOYEE, "redirect:" + EMPLOYEE_HOME_PAGE_PAGE);
        loginMenu.put(Role.VISITOR, "redirect:" + INDEX_PAGE);
    }

    public Map<String, ServletHandler> getGetHandlers() {
        return getHandlers;
    }

    public Map<String, ServletHandler> getPostHandlers() {
        return postHandlers;
    }

}
