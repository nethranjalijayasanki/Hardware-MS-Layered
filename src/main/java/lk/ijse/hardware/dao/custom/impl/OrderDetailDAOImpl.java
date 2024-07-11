package lk.ijse.hardware.dao.custom.impl;

import lk.ijse.hardware.dao.SQLUtil;
import lk.ijse.hardware.dao.custom.OrderDetailDAO;
import lk.ijse.hardware.db.DbConnection;
import lk.ijse.hardware.entity.Order_Detail;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {
/*
    public static boolean save(List<Order_Detail> odList) throws SQLException {
        for (Order_Detail od : odList) {
            boolean isSaved = save(od);
            if(!isSaved) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(Order_Detail od) throws SQLException {
        String sql = "INSERT INTO order_detail VALUES(?, ?, ?, ?)";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setString(1, od.getO_id());
        pstm.setString(2, od.getI_id());
        pstm.setInt(3, od.getQty());
        pstm.setDouble(4, od.getUnit_price());

        return pstm.executeUpdate() > 0;    //false ->  |
    }

 */

    @Override
    public boolean add(Order_Detail orderDetail) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO order_detail VALUES(?, ?, ?, ?)",orderDetail.getO_id(),orderDetail.getI_id(),orderDetail.getQty(),orderDetail.getUnit_price());

    }

    @Override
    public boolean update(Order_Detail entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ArrayList<Order_Detail> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<String> getIDs() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public Order_Detail searchByID(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public Order_Detail searchByTel(String tell) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
