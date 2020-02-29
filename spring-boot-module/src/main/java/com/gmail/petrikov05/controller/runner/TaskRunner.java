package com.gmail.petrikov05.controller.runner;

import java.math.BigDecimal;

import com.gmail.petrikov05.service.ItemService;
import com.gmail.petrikov05.service.RelationItemShopService;
import com.gmail.petrikov05.service.ShopService;
import com.gmail.petrikov05.service.model.ItemDTO;
import com.gmail.petrikov05.service.model.ShopDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Controller;

@Controller
public class TaskRunner implements ApplicationRunner {

    private static final Logger logger = LogManager.getLogger(TaskRunner.class);
    private ItemService itemService;
    private ShopService shopService;
    private RelationItemShopService relationItemShop;

    public TaskRunner(ItemService itemService, ShopService shopService, RelationItemShopService relationItemShop) {
        this.itemService = itemService;
        this.shopService = shopService;
        this.relationItemShop = relationItemShop;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ItemDTO itemDTO = getItemDTO();
        Long itemId = itemService.add(itemDTO);
        logger.info("Added item. Its ID: " + itemId);

        ShopDTO shopDTO = getShopDTO();
        Long shopId = shopService.add(shopDTO);
        logger.info("Added shop. Its ID: " + shopId);

        relationItemShop.createRelation(itemId, shopId);

        relationItemShop.deleteDataWithRelationItemShop();
    }

    private ShopDTO getShopDTO() {
        String name = "Shop Name";
        String location = "Shop location";
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setName(name);
        shopDTO.setLocation(location);
        return shopDTO;
    }

    private ItemDTO getItemDTO() {
        ItemDTO item = new ItemDTO();
        String name = "name";
        String description = "description";
        BigDecimal price = BigDecimal.valueOf(23);
        item.setName(name);
        item.setDescription(description);
        item.setPrice(price);
        return item;
    }

}
