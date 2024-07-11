package lk.ijse.hardware.bo.custom.impl;

import lk.ijse.hardware.bo.custom.ItemBo;
import lk.ijse.hardware.dao.DAOFactory;
import lk.ijse.hardware.dao.custom.CustomerDAO;
import lk.ijse.hardware.dao.custom.ItemDAO;
import lk.ijse.hardware.dto.ItemDTO;
import lk.ijse.hardware.dto.TransportDTO;
import lk.ijse.hardware.entity.Item;
import lk.ijse.hardware.entity.Transport;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBo {
    ItemDAO itemDAO=(ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    @Override
    public ArrayList<ItemDTO> getAllItem() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> itemDTOS = new ArrayList<>();
        ArrayList<Item> items = itemDAO.getAll();

        for (Item item : items) {
            ItemDTO itemDTO = new ItemDTO(item.getI_id(),item.getS_id(),item.getDescription(),item.getUnit_price(),item.getQty_on_hand());
            itemDTOS.add(itemDTO);
        }
        return itemDTOS;
    }

    @Override
    public ArrayList<String> getItemIDs() throws SQLException, ClassNotFoundException {
        return itemDAO.getIDs();
    }

    @Override
    public boolean addItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        return itemDAO.add(new Item(itemDTO.getI_id(),itemDTO.getS_id(),itemDTO.getDescription(),itemDTO.getUnit_price(),itemDTO.getQty_on_hand()));
    }

    @Override
    public boolean updateItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        return itemDAO.update(new Item(itemDTO.getI_id(),itemDTO.getS_id(),itemDTO.getDescription(),itemDTO.getUnit_price(),itemDTO.getQty_on_hand()));
    }

    @Override
    public boolean deleteItem(String id) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(id);
    }

    @Override
    public ItemDTO searchByID(String id) throws SQLException, ClassNotFoundException {
       Item item=itemDAO.searchByID(id);
       return new ItemDTO(item.getI_id(),item.getS_id(),item.getDescription(),item.getUnit_price(),item.getQty_on_hand());
    }

    @Override
    public ItemDTO searchByTel(String tell) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return itemDAO.generateNewID();
    }

    @Override
    public boolean existItem(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
