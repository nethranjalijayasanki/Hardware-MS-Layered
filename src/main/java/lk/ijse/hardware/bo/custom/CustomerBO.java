package lk.ijse.hardware.bo.custom;

import lk.ijse.hardware.bo.SuperBO;
import lk.ijse.hardware.dto.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {

    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException;
    public ArrayList<String> getCustomerIDs() throws SQLException, ClassNotFoundException;
    public boolean addCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;
    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;
    public CustomerDTO searchByID(String id) throws SQLException, ClassNotFoundException;
    public CustomerDTO searchByTel(String tell) throws SQLException, ClassNotFoundException;
    public String generateNewID() throws SQLException, ClassNotFoundException;
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException;

}
