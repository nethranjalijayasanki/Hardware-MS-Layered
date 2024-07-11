package lk.ijse.hardware.bo.custom;

import lk.ijse.hardware.bo.SuperBO;
import lk.ijse.hardware.dto.CustomerDTO;
import lk.ijse.hardware.dto.DriverDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DriverBO extends SuperBO {
    public ArrayList<DriverDTO> getAllDriver() throws SQLException, ClassNotFoundException;
    public ArrayList<String> getDriverIDs() throws SQLException, ClassNotFoundException;
    public boolean addDriver(DriverDTO driverDTO) throws SQLException, ClassNotFoundException;
    public boolean updateDriver(DriverDTO driverDTO) throws SQLException, ClassNotFoundException;
    public boolean deleteDriver(String id) throws SQLException, ClassNotFoundException;
    public DriverDTO searchByID(String id) throws SQLException, ClassNotFoundException;
    public DriverDTO searchByTel(String tell) throws SQLException, ClassNotFoundException;
    public String generateNewID() throws SQLException, ClassNotFoundException;
    public boolean existDriver(String id) throws SQLException, ClassNotFoundException;
}
