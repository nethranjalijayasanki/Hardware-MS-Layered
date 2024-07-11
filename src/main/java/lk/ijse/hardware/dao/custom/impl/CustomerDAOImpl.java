package lk.ijse.hardware.dao.custom.impl;

import lk.ijse.hardware.dao.SQLUtil;
import lk.ijse.hardware.dao.custom.CustomerDAO;
import lk.ijse.hardware.db.DbConnection;
import lk.ijse.hardware.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    public boolean add(Customer customer) throws SQLException {

        return SQLUtil.execute("INSERT INTO customers VALUES(?, ?, ?, ?, ?)",
                customer.getC_id(),
                customer.getName(),
                customer.getAddress(),
                customer.getTel(),
                customer.getEmail());
    }

    public boolean update(Customer customer) throws SQLException {

        return SQLUtil.execute("UPDATE customers SET customer_name = ?, address = ?, contact = ? WHERE customer_id = ?",
                customer.getName(),
                customer.getAddress(),
                customer.getTel(),
                customer.getEmail(),
                customer.getC_id()
        ) ;
    }

    public  boolean delete(String id) throws SQLException {

        return SQLUtil.execute("DELETE FROM customers WHERE c_id = ?");
    }

    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {

        ArrayList<Customer> allCustomers=new ArrayList<>();
        ResultSet rst=SQLUtil.execute("SELECT * FROM customers");

        while (rst.next()) {
            Customer customer = new Customer(rst.getString("c_id"), rst.getString("name"), rst.getString("address"),rst.getString("tel"),rst.getString("email"));
            allCustomers.add(customer);
        }
        return allCustomers;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT c_id FROM customers WHERE id=?", id);
        return rst.next();
    }


    @Override
    public ArrayList<String> getIDs() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT c_id FROM customers");

        ArrayList<String> idList = new ArrayList<>();
        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }

    @Override
    public Customer searchByID(String id) throws SQLException, ClassNotFoundException {

        ResultSet rst =SQLUtil.execute("SELECT * FROM customers WHERE c_id = ?");
        rst.next();
        return new Customer(rst.getString("c_id"),rst.getString("name"),rst.getString("address"),rst.getString("tel"),rst.getString("email"));

    }

    public Customer searchByTel(String tell) throws SQLException {

        ResultSet rst =SQLUtil.execute("SELECT * FROM customers WHERE tel = ?");
        rst.next();
        return new Customer(rst.getString("id"),rst.getString("name"),rst.getString("address"),rst.getString("tel"),rst.getString("email"));

    }

    @Override
    public String generateNewID() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT c_id FROM customers ORDER BY c_id DESC LIMIT 1");
        if (rst.next()) {
            String id = rst.getString("id");
            int newCustomerId = Integer.parseInt(id.replace("C00-", "")) + 1;
            return String.format("C00-%03d", newCustomerId);
        } else {
            return "C00-001";
        }
    }

}
