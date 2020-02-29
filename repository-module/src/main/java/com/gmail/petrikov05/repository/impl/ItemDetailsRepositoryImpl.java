package com.gmail.petrikov05.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.gmail.petrikov05.repository.ItemDetailsRepository;
import com.gmail.petrikov05.repository.model.ItemDetails;
import org.springframework.stereotype.Repository;

@Repository
public class ItemDetailsRepositoryImpl extends GeneralRepositoryImpl<ItemDetails> implements ItemDetailsRepository {

    @Override
    public void add(Connection connection, ItemDetails itemDetails) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO item_details(id, price) VALUES (?,?)",
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            statement.setLong(1, itemDetails.getId());
            statement.setBigDecimal(2, itemDetails.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating ItemDetails failed, no rows affected.");
            }
        }
    }

    @Override
    public void delete(Connection connection, Long id) throws SQLException {
        throw new UnsupportedOperationException();
    }

}
