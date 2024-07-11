package lk.ijse.hardware.bo.custom;

import lk.ijse.hardware.bo.SuperBO;
import lk.ijse.hardware.dto.CustomerDTO;
import lk.ijse.hardware.dto.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBo extends SuperBO {
    public ArrayList<ItemDTO> getAllItem() throws SQLException, ClassNotFoundException;
    public ArrayList<String> getItemIDs() throws SQLException, ClassNotFoundException;
    public boolean addItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;
    public boolean updateItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;
    public boolean deleteItem(String id) throws SQLException, ClassNotFoundException;
    public ItemDTO searchByID(String id) throws SQLException, ClassNotFoundException;
    public ItemDTO searchByTel(String tell) throws SQLException, ClassNotFoundException;
    public String generateNewID() throws SQLException, ClassNotFoundException;
    public boolean existItem(String id) throws SQLException, ClassNotFoundException;
}
