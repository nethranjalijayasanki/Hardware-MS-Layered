package lk.ijse.hardware.dao.custom;

import lk.ijse.hardware.dao.CrudDAO;
import lk.ijse.hardware.entity.Item;
import lk.ijse.hardware.entity.Order_Detail;

import java.sql.SQLException;
import java.util.List;

public interface ItemDAO extends CrudDAO<Item> {
    public boolean updateQty(List<Order_Detail> odList) throws SQLException;

    }
