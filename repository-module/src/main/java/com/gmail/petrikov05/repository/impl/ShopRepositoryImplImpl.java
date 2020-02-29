package com.gmail.petrikov05.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.gmail.petrikov05.repository.ShopRepository;
import com.gmail.petrikov05.repository.model.Shop;
import org.springframework.stereotype.Repository;

@Repository
public class ShopRepositoryImplImpl extends GeneralRepositoryImpl<Shop> implements ShopRepository {

    @Override
    public void add(Connection connection, Shop shop) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO shop(name , location) VALUES (?,?)",
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            statement.setString(1, shop.getName());
            statement.setString(2, shop.getLocation());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating shop failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    shop.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating shop failed, no ID obtained.");
                }
            }
        }
    }

    @Override
    public void delete(Connection connection, Long id) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "DELETE FROM shop WHERE id IN (?);"
                )
        ) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("delete shop failed, no rows affected.");
            }
        }
    }

}
