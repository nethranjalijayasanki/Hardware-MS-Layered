package lk.ijse.hardware.dao.custom.impl;

import lk.ijse.hardware.dao.SQLUtil;
import lk.ijse.hardware.dao.custom.DriverDAO;
import lk.ijse.hardware.db.DbConnection;
import lk.ijse.hardware.entity.Driver;
import lk.ijse.hardware.entity.Transport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DriverDAOImpl implements DriverDAO {


    @Override
    public boolean add(Driver driver) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO drivers VALUES(?, ?, ?, ?, ?)",driver.getD_id(),driver.getName(),driver.getTel(),driver.getEmail(),driver.getWork_time());

    }

    public boolean update(Driver driver) throws SQLException {

        return SQLUtil.execute("UPDATE drivers SET name = ?, tel = ?, email = ?, work_time = ? WHERE d_id = ?",driver.getD_id(),driver.getName(),driver.getTel(),driver.getEmail(),driver.getWork_time());

    }

    public boolean delete(String id) throws SQLException {

        return SQLUtil.execute("DELETE FROM drivers WHERE d_id = ?");
    }

    public  ArrayList<Driver> getAll() throws SQLException {
        ArrayList<Driver> allDrivers = new ArrayList<>();
        ResultSet rst=SQLUtil.execute("SELECT * FROM drivers");

        while (rst.next()){
            Driver driver=new Driver(rst.getString("d_id"),rst.getString("name"),rst.getString("tel"),rst.getString("email"),rst.getString("work_time"));
            allDrivers.add(driver);

        }
        return allDrivers;
    }

    @Override
    public Driver searchByID(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst=SQLUtil.execute("SELECT * FROM drivers WHERE d_id = ?");
        rst.next();

        return new Driver(rst.getString("d_id"),rst.getString("name"),rst.getString("tel"),rst.getString("email"),rst.getString("work_time"));

    }

    @Override
    public ArrayList<String> getIDs() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT d_id FROM drivers");

        ArrayList<String> idList = new ArrayList<>();
        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT d_id FROM drivers ORDER BY d_id DESC LIMIT 1");
        if (rst.next()) {
            String id = rst.getString("id");
            String transportId  = String.valueOf(Integer.parseInt(id.replace("D00-", "")) + 1);
            return String.format("D00-%03d", transportId);
        } else {
            return "D00-001";
        }
    }

    @Override
    public Driver searchByTel(String tell) throws SQLException, ClassNotFoundException {
        return null;
    }


    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
