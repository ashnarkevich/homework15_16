package com.gmail.petrikov05.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gmail.petrikov05.repository.ConnectionRepository;
import com.gmail.petrikov05.repository.ItemRepository;
import com.gmail.petrikov05.repository.RelationItemShopRepository;
import com.gmail.petrikov05.repository.ShopRepository;
import com.gmail.petrikov05.repository.model.Relation;
import com.gmail.petrikov05.service.RelationItemShopService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class RelationItemShopServiceImpl implements RelationItemShopService {

    private static final Logger logger = LogManager.getLogger(ItemServiceImpl.class);
    private ConnectionRepository connectionRepository;
    private RelationItemShopRepository relationItemShopRepository;
    private ItemRepository itemRepository;
    private ShopRepository shopRepository;

    public RelationItemShopServiceImpl(
            ConnectionRepository connectionRepository,
            RelationItemShopRepository relationItemShopRepository,
            ItemRepository itemRepository,
            ShopRepository shopRepository) {
        this.connectionRepository = connectionRepository;
        this.relationItemShopRepository = relationItemShopRepository;
        this.itemRepository = itemRepository;
        this.shopRepository = shopRepository;
    }

    @Override
    public void createRelation(Long itemId, Long shopId) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Relation relation = convertRelation(itemId, shopId);
                relationItemShopRepository.add(connection, relation);
                connection.commit();
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                connection.rollback();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void deleteDataWithRelationItemShop() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<Long> ids = relationItemShopRepository.findWithRelationItemShop(connection);
                for (Long id : ids) {
                    relationItemShopRepository.delete(connection, id);
                    itemRepository.delete(connection, id);
                    shopRepository.delete(connection, id);
                }
                connection.commit();
                logger.info("data deleted");
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private Relation convertRelation(Long itemId, Long shopId) {
        Relation relation = new Relation();
        relation.setItemId(itemId);
        relation.setShopId(shopId);
        return relation;
    }

}
