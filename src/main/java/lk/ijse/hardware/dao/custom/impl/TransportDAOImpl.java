package lk.ijse.hardware.dao.custom.impl;

import lk.ijse.hardware.dao.SQLUtil;
import lk.ijse.hardware.dao.custom.TransportDAO;
import lk.ijse.hardware.db.DbConnection;
import lk.ijse.hardware.entity.Transport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransportDAOImpl implements TransportDAO {

    public boolean add(Transport transport) throws SQLException {

        return SQLUtil.execute("INSERT INTO transports VALUES(?, ?, ?)",transport.getT_id(),transport.getDescription(),transport.getD_id());

    }

    public boolean update(Transport transport) throws SQLException {

        return SQLUtil.execute("UPDATE transports SET d_id = ?, description = ? WHERE t_id = ?",transport.getT_id(),transport.getDescription(),transport.getD_id());

    }
    public boolean delete(String id) throws SQLException {

        return SQLUtil.execute("DELETE FROM transports WHERE t_id = ?");

    }
    public ArrayList<Transport> getAll() throws SQLException {

        ArrayList<Transport> allTransport = new ArrayList<>();
        ResultSet rst=SQLUtil.execute("SELECT * FROM transports");

        while (rst.next()){
            Transport transport=new Transport(rst.getString("t_id"),rst.getString("description"),rst.getString("d_id"));
            allTransport.add(transport);

        }
        return allTransport;
    }

    @Override
    public Transport searchByID(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst=SQLUtil.execute("SELECT * FROM transports WHERE t_id = ?");
        rst.next();
        return new Transport(rst.getString("t_id"),rst.getString("description"),rst.getString("d_id"));

    }

    @Override
    public ArrayList<String> getIDs() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT t_id FROM transports");

        ArrayList<String> idList = new ArrayList<>();
        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }
    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT t_id FROM transports ORDER BY t_id DESC LIMIT 1");
        if (rst.next()) {
            String id = rst.getString("id");
            String transportId  = String.valueOf(Integer.parseInt(id.replace("T00-", "")) + 1);
            return String.format("T00-%03d", transportId);
        } else {
            return "T00-001";
        }
    }


    @Override
    public Transport searchByTel(String tell) throws SQLException, ClassNotFoundException {
        return null;
    }



    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

}
