package lk.ijse.hardware.dao.custom.impl;

import lk.ijse.hardware.dao.SQLUtil;
import lk.ijse.hardware.dao.custom.ItemDAO;
import lk.ijse.hardware.db.DbConnection;
import lk.ijse.hardware.entity.Item;
import lk.ijse.hardware.entity.Order_Detail;
import lk.ijse.hardware.entity.Transport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {

    @Override
    public boolean add(Item item) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO items VALUES(?, ?, ?, ?, ?)",item.getI_id(),item.getS_id(),item.getDescription(),item.getUnit_price(),item.getQty_on_hand());

    }

    @Override
    public boolean update(Item item) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE items SET s_id=?, description=?, unitPrice=?, qtyOnHand=? WHERE i_id=?",item.getI_id(),item.getS_id(),item.getDescription(),item.getUnit_price(),item.getQty_on_hand());
    }

    public boolean delete(String id) throws SQLException {

        return SQLUtil.execute("DELETE FROM items WHERE i_id = ?");
    }

    public ArrayList<Item> getAll() throws SQLException {
        ArrayList<Item> allItem = new ArrayList<>();
        ResultSet rst=SQLUtil.execute("SELECT * FROM items");

        while (rst.next()){
            Item item=new Item(rst.getString("i_id"),rst.getString("s_id"),rst.getString("description"),rst.getDouble("unitPrice"),rst.getInt("qtyOnHand"));
            allItem.add(item);

        }
        return allItem;

    }

    @Override
    public Item searchByID(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst=SQLUtil.execute("SELECT * FROM items WHERE i_id = ?");
        rst.next();
        return new Item(rst.getString("i_id"),rst.getString("s_id"),rst.getString("description"),rst.getDouble("unitPrice"),rst.getInt("qtyOnHand"));

    }

    @Override
    public ArrayList<String> getIDs() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT i_id FROM items");

        ArrayList<String> idList = new ArrayList<>();
        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT i_id FROM items ORDER BY i_id DESC LIMIT 1");
        if (rst.next()) {
            String id = rst.getString("id");
            String transportId  = String.valueOf(Integer.parseInt(id.replace("I00-", "")) + 1);
            return String.format("I00-%03d", transportId);
        } else {
            return "I00-001";
        }
    }
    @Override
    public Item searchByTel(String tell) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
    /*
    public static boolean update(List<Order_Detail> odList) throws SQLException {
        for (Order_Detail od : odList) {
            boolean isUpdateQty = updateQty(odList);
            if(!isUpdateQty) {
                return false;
            }
        }
        return true;
    }

     */
    public boolean updateQty(List<Order_Detail> odList) throws SQLException {
        String sql = "UPDATE items SET qty_on_hand = qty_on_hand - ? WHERE i_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        for (Order_Detail od : odList){
            pstm.setInt(1, Integer.parseInt(od.getI_id()));
            pstm.setString(2, String.valueOf(od.getQty()));
            pstm.executeUpdate();
        }
        return true;
    }
}
