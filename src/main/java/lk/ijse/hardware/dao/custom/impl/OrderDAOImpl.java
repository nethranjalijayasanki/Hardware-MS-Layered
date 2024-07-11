package lk.ijse.hardware.dao.custom.impl;

import lk.ijse.hardware.dao.SQLUtil;
import lk.ijse.hardware.dao.custom.OrderDAO;
import lk.ijse.hardware.db.DbConnection;
import lk.ijse.hardware.entity.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public boolean add(Order order) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO orders VALUES( ?, ?, ?)",order.getO_id(),order.getDate(),order.getC_id());

    }
    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT o_id FROM orders ORDER BY o_id DESC LIMIT 1");
        if (rst.next()) {
            String id = rst.getString("id");
            String transportId  = String.valueOf(Integer.parseInt(id.replace("B00-", "")) + 1);
            return String.format("B00-%03d", transportId);
        } else {
            return "B00-001";
        }
    }

    @Override
    public boolean update(Order entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ArrayList<Order> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<String> getIDs() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public Order searchByID(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public Order searchByTel(String tell) throws SQLException, ClassNotFoundException {
        return null;
    }



    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
