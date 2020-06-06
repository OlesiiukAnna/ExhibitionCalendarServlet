package ua.external.data.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.external.data.dao.interfaces.ExhibitionHallDao;
import ua.external.data.entity.ExhibitionHall;
import ua.external.exceptions.DaoException;
import ua.external.exceptions.dao.DuplicateValueException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ua.external.data.dao.impl.sql.queries.ExhibitionHallQueries.*;

public class ExhibitionHallDaoImpl implements ExhibitionHallDao<ExhibitionHall> {
    private Logger logger = LoggerFactory.getLogger(ExhibitionHallDaoImpl.class);

    private DataSource dataSource;

    public ExhibitionHallDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<ExhibitionHall> getById(int id) {
        ExhibitionHall exhibitionHall = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_EXHIBITION_HALL_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            int exhibitionHallId = -1;
            while (resultSet.next() && id != exhibitionHallId) {
                exhibitionHallId = resultSet.getInt(1);
                exhibitionHall = getExhibitionHallFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            logger.error("Error when get exhibition hall ", e);
            throw new DaoException(e);
        }
        return Optional.ofNullable(exhibitionHall);
    }

    private ExhibitionHall getExhibitionHallFromResultSet(ResultSet resultSet) throws SQLException {
        int exhibitionHallId = resultSet.getInt(1);
        String name = resultSet.getString(2);
        int numberOfVisitorsPerDay = resultSet.getInt(3);
        return new ExhibitionHall(exhibitionHallId, name, numberOfVisitorsPerDay);
    }

    @Override
    public Optional<ExhibitionHall> getByName(String name) {
        ExhibitionHall exhibitionHall = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_EXHIBITION_HALL_BY_NAME)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            String exhibitionHallName = "";
            while (resultSet.next() && !name.equals(exhibitionHallName)) {
                exhibitionHallName = resultSet.getString(2);
                exhibitionHall = getExhibitionHallFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            logger.error("Error when get exhibition hall ", e);
            throw new DaoException(e);
        }
        return Optional.ofNullable(exhibitionHall);
    }

    @Override
    public List<ExhibitionHall> getAll() {
        List<ExhibitionHall> exhibitionHalls = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_EXHIBITION_HALLS);
            while (resultSet.next()) {
                exhibitionHalls.add(getExhibitionHallFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            logger.error("Error when get all exhibition halls ", e);
            throw new DaoException(e);
        }
        return exhibitionHalls;
    }

    @Override
    public boolean save(ExhibitionHall exhibitionHall) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_EXHIBITION_HALL)) {
            statement.setString(1, exhibitionHall.getName());
            statement.setInt(2, exhibitionHall.getAllowableNumberOfVisitorsPerDay());
            statement.execute();
            return true;
        } catch (SQLException e) {
            logger.error("Error when save exhibition hall ", e);
            throw new DuplicateValueException(e);
        }
    }

    @Override
    public Optional<ExhibitionHall> update(ExhibitionHall exhibitionHall) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_EXHIBITION_HALL)) {
            statement.setString(1, exhibitionHall.getName());
            statement.setInt(2, exhibitionHall.getId());
            statement.setInt(3, exhibitionHall.getId());
            int updatedRows = statement.executeUpdate();
            if (updatedRows == 1) {
                return Optional.of(exhibitionHall);
            }
        } catch (SQLException e) {
            logger.error("Error when update exhibition hall ", e);
            throw new DaoException(e);
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_EXHIBITION_HALL)) {
            statement.setInt(1, id);
            int updatedRows = statement.executeUpdate();
            if (updatedRows == 1) {
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error when delete exhibition hall ", e);
            throw new DaoException(e);
        }
        return false;
    }
}
