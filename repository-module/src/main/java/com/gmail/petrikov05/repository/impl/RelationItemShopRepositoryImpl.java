package com.gmail.petrikov05.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.gmail.petrikov05.repository.RelationItemShopRepository;
import com.gmail.petrikov05.repository.model.Relation;
import org.springframework.stereotype.Repository;

@Repository
public class RelationItemShopRepositoryImpl extends GeneralRepositoryImpl<Relation> implements RelationItemShopRepository {

    @Override
    public void add(Connection connection, Relation relation) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO relation_item_shop(item_id , shop_id) VALUES (?,?);"
                )
        ) {
            statement.setLong(1, relation.getItemId());
            statement.setLong(2, relation.getShopId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating relationItemShop failed, no rows affected.");
            }
        }
    }

    @Override
    public void delete(Connection connection, Long id) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "DELETE FROM relation_item_shop WHERE id = ?;"
                )
        ) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("delete item failed, no rows affected.");
            }
        }
    }

    @Override
    public List<Long> findWithRelationItemShop(Connection connection) throws SQLException {
        try (
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT id FROM relation_item_shop WHERE item_id = shop_id;")
        ) {
            List<Long> ids = new ArrayList<>();
            while (rs.next()) {
                Long id = rs.getLong("id");
                ids.add(id);
            }
            return ids;
        }
    }

}
