package lk.ijse.hardware.dao.custom.impl;

import lk.ijse.hardware.dao.custom.PlaceOrderDAO;
import lk.ijse.hardware.db.DbConnection;
import lk.ijse.hardware.entity.Place_Order;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaceOrderDAOImpl implements PlaceOrderDAO {


    @Override
    public boolean add(Place_Order entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Place_Order entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ArrayList<Place_Order> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<String> getIDs() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public Place_Order searchByID(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public Place_Order searchByTel(String tell) throws SQLException, ClassNotFoundException {
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
