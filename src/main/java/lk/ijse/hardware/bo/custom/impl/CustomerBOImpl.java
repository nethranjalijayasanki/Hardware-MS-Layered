package lk.ijse.hardware.bo.custom.impl;

import lk.ijse.hardware.bo.custom.CustomerBO;
import lk.ijse.hardware.dao.DAOFactory;
import lk.ijse.hardware.dao.custom.CustomerDAO;
import lk.ijse.hardware.dto.CustomerDTO;
import lk.ijse.hardware.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO=(CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> customerDTOS = new ArrayList<>();
        ArrayList<Customer> customers = customerDAO.getAll();

        for (Customer customer : customers) {
            CustomerDTO customerDTO = new CustomerDTO(customer.getC_id(), customer.getName(), customer.getAddress(), customer.getTel(),customer.getEmail());
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;
    }
    @Override
    public ArrayList<String> getCustomerIDs() throws SQLException, ClassNotFoundException {
        return customerDAO.getIDs();
    }
    @Override
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(id);
    }
    @Override
    public boolean addCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.add(new Customer(dto.getC_id(), dto.getName(), dto.getAddress(), dto.getTel(), dto.getEmail()));
    }

    @Override
    public boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(dto.getC_id(), dto.getName(), dto.getAddress(), dto.getTel(), dto.getEmail()));

    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);
    }

    @Override
    public CustomerDTO searchByID(String id) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.searchByID(id);
        return new CustomerDTO(customer.getC_id(), customer.getName(), customer.getAddress(), customer.getTel(),customer.getEmail());

    }

    @Override
    public CustomerDTO searchByTel(String tell) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.searchByID(tell);
        return new CustomerDTO(customer.getC_id(), customer.getName(), customer.getAddress(), customer.getTel(),customer.getEmail());
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return customerDAO.generateNewID();
    }
}
