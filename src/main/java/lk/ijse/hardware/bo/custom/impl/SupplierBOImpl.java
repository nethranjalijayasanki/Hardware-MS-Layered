package lk.ijse.hardware.bo.custom.impl;

import lk.ijse.hardware.bo.custom.SupplierBO;
import lk.ijse.hardware.dao.DAOFactory;
import lk.ijse.hardware.dao.custom.CustomerDAO;
import lk.ijse.hardware.dao.custom.SupplierDAO;
import lk.ijse.hardware.dto.SupplierDTO;
import lk.ijse.hardware.dto.TransportDTO;
import lk.ijse.hardware.entity.Supplier;
import lk.ijse.hardware.entity.Transport;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierBOImpl implements SupplierBO {
    SupplierDAO supplierDAO=(SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER);
    @Override
    public ArrayList<SupplierDTO> getAllSupplier() throws SQLException, ClassNotFoundException {
        ArrayList<SupplierDTO> supplierDTOS = new ArrayList<>();
        ArrayList<Supplier> suppliers = supplierDAO.getAll();

        for (Supplier supplier : suppliers) {
            SupplierDTO supplierDTO = new SupplierDTO(supplier.getS_id(),supplier.getName(),supplier.getCompany(),supplier.getTel(),supplier.getEmail());
            supplierDTOS.add(supplierDTO);
        }
        return supplierDTOS;
    }

    @Override
    public ArrayList<String> getSupplierIDs() throws SQLException, ClassNotFoundException {
        return supplierDAO.getIDs();
    }

    @Override
    public boolean addSupplier(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException {
        return supplierDAO.add(new Supplier(supplierDTO.getS_id(), supplierDTO.getName(), supplierDTO.getCompany(), supplierDTO.getTel(), supplierDTO.getEmail()));;
    }

    @Override
    public boolean updateSupplier(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException {
        return supplierDAO.update(new Supplier(supplierDTO.getS_id(), supplierDTO.getName(), supplierDTO.getCompany(), supplierDTO.getTel(), supplierDTO.getEmail()));
    }

    @Override
    public boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException {
        return supplierDAO.delete(id);
    }

    @Override
    public SupplierDTO searchByID(String id) throws SQLException, ClassNotFoundException {
        Supplier supplier=supplierDAO.searchByID(id);
        return new SupplierDTO(supplier.getS_id(), supplier.getName(), supplier.getCompany(), supplier.getTel(), supplier.getEmail());
    }

    @Override
    public SupplierDTO searchByTel(String tell) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return supplierDAO.generateNewID();
    }

    @Override
    public boolean existSupplier(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
