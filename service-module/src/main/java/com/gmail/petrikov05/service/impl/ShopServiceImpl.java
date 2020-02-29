package com.gmail.petrikov05.service.impl;

import java.sql.Connection;
import java.sql.SQLException;

import com.gmail.petrikov05.repository.ConnectionRepository;
import com.gmail.petrikov05.repository.ShopRepository;
import com.gmail.petrikov05.repository.model.Shop;
import com.gmail.petrikov05.service.ShopService;
import com.gmail.petrikov05.service.model.ShopDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ShopServiceImpl implements ShopService {

    private static final Logger logger = LogManager.getLogger(ShopServiceImpl.class);
    private ConnectionRepository connectionRepository;
    private ShopRepository shopRepository;

    public ShopServiceImpl(ConnectionRepository connectionRepository, ShopRepository shopRepository) {
        this.connectionRepository = connectionRepository;
        this.shopRepository = shopRepository;
    }

    @Override
    public Long add(ShopDTO shopDTO) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Shop shop = convertShopDTOToShop(shopDTO);
                shopRepository.add(connection, shop);
                connection.commit();
                return shop.getId();
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                connection.rollback();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    private Shop convertShopDTOToShop(ShopDTO shopDTO) {
        Shop shop = new Shop();
        shop.setName(shopDTO.getName());
        shop.setLocation(shopDTO.getLocation());
        return shop;
    }

}
