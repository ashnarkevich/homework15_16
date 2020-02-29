package com.gmail.petrikov05.repository;

import java.sql.Connection;
import java.sql.SQLException;

public interface GeneralRepository<T> {

    void add(Connection connection, T t) throws SQLException;

    void delete(Connection connection, Long id) throws SQLException;

}
