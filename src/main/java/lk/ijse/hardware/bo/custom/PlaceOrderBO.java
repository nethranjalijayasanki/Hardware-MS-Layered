package lk.ijse.hardware.bo.custom;

import lk.ijse.hardware.bo.SuperBO;
import lk.ijse.hardware.dto.CustomerDTO;
import lk.ijse.hardware.dto.PlacOrderDTO;
import lk.ijse.hardware.entity.Place_Order;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PlaceOrderBO extends SuperBO {
    public ArrayList<PlacOrderDTO> getAllPlacOrder() throws SQLException, ClassNotFoundException;
    public ArrayList<String> getPlacOrderIDs() throws SQLException, ClassNotFoundException;
    public boolean addPlacOrder(PlacOrderDTO placOrderDTO) throws SQLException, ClassNotFoundException;
    public boolean updatePlacOrder(PlacOrderDTO placOrderDTO) throws SQLException, ClassNotFoundException;
    public boolean deletePlacOrder(String id) throws SQLException, ClassNotFoundException;
    public PlacOrderDTO searchByID(String id) throws SQLException, ClassNotFoundException;
    public PlacOrderDTO searchByTel(String tell) throws SQLException, ClassNotFoundException;
    public String generateNewID() throws SQLException, ClassNotFoundException;
    public boolean existPlacOrder(String id) throws SQLException, ClassNotFoundException;
    public boolean placeOrder(Place_Order po) throws SQLException;
}
