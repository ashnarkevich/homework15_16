package com.gmail.petrikov05.service.impl;

import java.sql.Connection;
import java.sql.SQLException;

import com.gmail.petrikov05.repository.ConnectionRepository;
import com.gmail.petrikov05.repository.ItemDetailsRepository;
import com.gmail.petrikov05.repository.ItemRepository;
import com.gmail.petrikov05.repository.model.Item;
import com.gmail.petrikov05.repository.model.ItemDetails;
import com.gmail.petrikov05.service.ItemService;
import com.gmail.petrikov05.service.model.ItemDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    private static final Logger logger = LogManager.getLogger(ItemServiceImpl.class);
    private ConnectionRepository connectionRepository;
    private ItemRepository itemRepository;
    private ItemDetailsRepository itemDetailsRepository;

    public ItemServiceImpl(ConnectionRepository connectionRepository, ItemRepository itemRepository, ItemDetailsRepository itemDetailsRepository) {
        this.connectionRepository = connectionRepository;
        this.itemRepository = itemRepository;
        this.itemDetailsRepository = itemDetailsRepository;
    }

    @Override
    public Long add(ItemDTO itemDTO) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Item item = convertItemDTOToItem(itemDTO);
                itemRepository.add(connection, item);
                item.getItemDetails().setId(item.getId());
                itemDetailsRepository.add(connection, item.getItemDetails());
                connection.commit();
                return item.getId();
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                connection.rollback();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    private Item convertItemDTOToItem(ItemDTO itemDTO) {
        Item item = new Item();
        item.setName(itemDTO.getName());
        item.setDescription(itemDTO.getDescription());
        ItemDetails itemDetails = new ItemDetails();
        itemDetails.setPrice(itemDTO.getPrice());
        item.setItemDetails(itemDetails);
        return item;
    }

}