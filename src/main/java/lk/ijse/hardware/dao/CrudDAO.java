package lk.ijse.hardware.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T> extends SuperDAO {
    public boolean add(T entity) throws SQLException, ClassNotFoundException;
    public boolean update(T entity) throws SQLException, ClassNotFoundException;
    public boolean delete(String id) throws SQLException, ClassNotFoundException;
    public ArrayList<T> getAll() throws SQLException, ClassNotFoundException;
    public ArrayList<String> getIDs() throws SQLException, ClassNotFoundException;
    public T searchByID(String id) throws SQLException, ClassNotFoundException;
    public T searchByTel(String tell) throws SQLException, ClassNotFoundException;
    public  String generateNewID() throws SQLException, ClassNotFoundException;
    public boolean exist(String id) throws SQLException, ClassNotFoundException;

}
