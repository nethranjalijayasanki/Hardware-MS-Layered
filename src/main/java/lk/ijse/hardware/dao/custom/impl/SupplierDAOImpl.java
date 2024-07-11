package lk.ijse.hardware.dao.custom.impl;

import lk.ijse.hardware.dao.SQLUtil;
import lk.ijse.hardware.dao.custom.SupplierDAO;
import lk.ijse.hardware.db.DbConnection;
import lk.ijse.hardware.entity.Supplier;
import lk.ijse.hardware.entity.Transport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAOImpl implements SupplierDAO {

    @Override
    public boolean add(Supplier supplier) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("INSERT INTO suppliers VALUES(?, ?, ?, ?, ?)",supplier.getS_id(),supplier.getName(),supplier.getCompany(),supplier.getTel(),supplier.getEmail());
    }


    public boolean update(Supplier supplier) throws SQLException {

      return SQLUtil.execute("UPDATE suppliers SET name = ?, company = ?, tel = ?, email = ? WHERE s_id = ?",supplier.getS_id(),supplier.getName(),supplier.getCompany(),supplier.getTel(),supplier.getEmail());
    }

    public boolean delete(String id) throws SQLException {

        return SQLUtil.execute("DELETE FROM suppliers WHERE s_id = ?");
    }
    public ArrayList<Supplier> getAll() throws SQLException {

        ArrayList<Supplier> allSupplier = new ArrayList<>();
        ResultSet rst=SQLUtil.execute("SELECT * FROM suppliers");

        while (rst.next()){
            Supplier supplier=new Supplier(rst.getString("s_id"),rst.getString("name"),rst.getString("company"),rst.getString("tel"),rst.getString("email"));
            allSupplier.add(supplier);
        }
        return allSupplier;
    }

    public static Supplier searchById(String id) throws SQLException {

        ResultSet rst=SQLUtil.execute("SELECT * FROM suppliers WHERE s_id = ?");
        rst.next();

        return new Supplier(rst.getString("s_id"),rst.getString("name"),rst.getString("company"),rst.getString("tel"),rst.getString("email"));

    }

    @Override
    public ArrayList<String> getIDs() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public Supplier searchByID(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public Supplier searchByTel(String tell) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {

        ResultSet rst = SQLUtil.execute("SELECT s_id FROM suppliers ORDER BY s_id DESC LIMIT 1");
        if (rst.next()) {
            String id = rst.getString("id");
            String supplierId  = String.valueOf(Integer.parseInt(id.replace("S00-", "")) + 1);
            return String.format("S00-%03d", supplierId);
        } else {
            return "S00-001";
        }
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    public static List<String> getId() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT s_id FROM suppliers");

        ArrayList<String> idList = new ArrayList<>();
        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }
}
