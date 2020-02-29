package com.gmail.petrikov05.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gmail.petrikov05.repository.model.Relation;

public interface RelationItemShopRepository extends GeneralRepository<Relation> {

    List<Long> findWithRelationItemShop(Connection connection) throws SQLException;

}
