package ua.external.configuration;

import ua.external.data.dao.connection.DBCPDataSource;
import ua.external.data.dao.impl.ExhibitionDaoImpl;
import ua.external.data.dao.impl.ExhibitionHallDaoImpl;
import ua.external.data.dao.impl.TicketDaoImpl;
import ua.external.data.dao.impl.UserDaoImpl;

import javax.sql.DataSource;

public class DaoFactory {
    private static DaoFactory daoFactory;

    private DataSource dataSource;

    public DaoFactory() {
        dataSource = DBCPDataSource.getDataSource();
    }

    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            synchronized (DaoFactory.class) {
                if (daoFactory == null) {
                    daoFactory = new DaoFactory();
                }
            }
        }
        return daoFactory;
    }

    public UserDaoImpl createUserDao() {
        return new UserDaoImpl(dataSource);
    }


    public ExhibitionHallDaoImpl createExhibitionHallDao() {
        return new ExhibitionHallDaoImpl(dataSource);
    }


    public ExhibitionDaoImpl createExhibitionDao() {
        return new ExhibitionDaoImpl(dataSource);
    }


    public TicketDaoImpl createTicketDao() {
        return new TicketDaoImpl(dataSource);
    }

}
