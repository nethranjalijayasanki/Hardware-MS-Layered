package lk.ijse.hardware.bo.custom;

import lk.ijse.hardware.bo.SuperBO;
import lk.ijse.hardware.dto.CustomerDTO;
import lk.ijse.hardware.dto.TransportDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TransportBO extends SuperBO {
    public ArrayList<TransportDTO> getAllTransport() throws SQLException, ClassNotFoundException;
    public ArrayList<String> getTransportIDs() throws SQLException, ClassNotFoundException;
    public boolean addTransport(TransportDTO transportDTO) throws SQLException, ClassNotFoundException;
    public boolean updateTransport(TransportDTO transportDTO) throws SQLException, ClassNotFoundException;
    public boolean deleteTransport(String id) throws SQLException, ClassNotFoundException;
    public TransportDTO searchByID(String id) throws SQLException, ClassNotFoundException;
    public TransportDTO searchByTel(String tell) throws SQLException, ClassNotFoundException;
    public String generateNewID() throws SQLException, ClassNotFoundException;
    public boolean existTransport(String id) throws SQLException, ClassNotFoundException;
}
