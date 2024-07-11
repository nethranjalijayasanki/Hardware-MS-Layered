package lk.ijse.hardware.bo.custom;

import lk.ijse.hardware.bo.SuperBO;
import lk.ijse.hardware.dto.CustomerDTO;
import lk.ijse.hardware.dto.SupplierDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierBO extends SuperBO {
    public ArrayList<SupplierDTO> getAllSupplier() throws SQLException, ClassNotFoundException;
    public ArrayList<String> getSupplierIDs() throws SQLException, ClassNotFoundException;
    public boolean addSupplier(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException;
    public boolean updateSupplier(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException;
    public boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException;
    public SupplierDTO searchByID(String id) throws SQLException, ClassNotFoundException;
    public SupplierDTO searchByTel(String tell) throws SQLException, ClassNotFoundException;
    public String generateNewID() throws SQLException, ClassNotFoundException;
    public boolean existSupplier(String id) throws SQLException, ClassNotFoundException;
}
