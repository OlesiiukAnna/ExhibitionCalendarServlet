package ua.external.data.dao.interfaces;

import ua.external.data.dao.Dao;
import ua.external.data.entity.Exhibition;
import ua.external.exceptions.DaoException;

import java.util.List;
import java.util.Optional;

public interface ExhibitionDao <T extends Exhibition> extends Dao<T> {

    Optional<Exhibition> getByName(String name) throws DaoException;

    List<Exhibition> getAllByExhibitionHallId(int id) throws DaoException;
}
