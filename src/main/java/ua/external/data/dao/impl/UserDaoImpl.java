package ua.external.data.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.external.data.dao.interfaces.UserDao;
import ua.external.data.entity.User;
import ua.external.exceptions.DaoException;
import ua.external.exceptions.dao.DuplicateValueException;
import ua.external.util.enums.Role;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ua.external.data.dao.impl.sql.queries.UserQueries.*;

public class UserDaoImpl implements UserDao<User> {
    private Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    private DataSource dataSource;

    public UserDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<User> getById(int id) {
        User user = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            int userId = -1;
            while (resultSet.next() && id != userId) {
                userId = resultSet.getInt(1);
                user = getUserFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            logger.error("Error when get user ", e);
            throw new DaoException(e);
        }
        return Optional.ofNullable(user);
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        int userId = resultSet.getInt(1);
        String email = resultSet.getString(2);
        String pass = resultSet.getString(3);
        String firstName = resultSet.getString(4);
        String lastName = resultSet.getString(5);
        String phone = resultSet.getString(6);
        String role = resultSet.getString(7);
        return new User(userId, email, pass, firstName, lastName, phone, Role.valueOf(role));
    }

    @Override
    public Optional<User> getByEmail(String email) {
        User user = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_EMAIL)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            String userEmail = "";
            while (resultSet.next() && !email.equals(userEmail)) {
                userEmail = resultSet.getString(2);
                user = getUserFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            logger.error("Error when get user by email ", e);
            throw new DaoException(e);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_USERS);
            while (resultSet.next()) {
                users.add(getUserFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            logger.error("Error when get all users ", e);
            throw new DaoException(e);
        }
        return users;
    }

    @Override
    public boolean save(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_USER)) {
            setUserToStatement(user, statement);
            statement.execute();
            return true;
        } catch (SQLException e) {
            logger.error("Error when save user ", e);
            throw new DuplicateValueException(e);
        }
    }

    private void setUserToStatement(User user, PreparedStatement statement) throws SQLException {
        statement.setString(1, user.getEmail());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getFirstName());
        statement.setString(4, user.getLastName());
        statement.setString(5, user.getPhone());
        statement.setString(6, user.getRole().toString());
    }

    @Override
    public Optional<User> update(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER)) {
            setUserToStatement(user, statement);
            statement.setInt(7, user.getId());
            int updatedRows = statement.executeUpdate();
            if (updatedRows == 1) {
                return Optional.of(user);
            }
        } catch (SQLException e) {
            logger.error("Error when update user ", e);
            throw new DaoException(e);
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER)) {
            statement.setInt(1, id);
            int updatedRows = statement.executeUpdate();
            if (updatedRows == 1) {
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error when delete user ", e);
            throw new DaoException(e);
        }
        return false;
    }
}
