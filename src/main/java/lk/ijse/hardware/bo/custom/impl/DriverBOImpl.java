package lk.ijse.hardware.bo.custom.impl;

import lk.ijse.hardware.bo.custom.DriverBO;
import lk.ijse.hardware.dao.DAOFactory;
import lk.ijse.hardware.dao.custom.CustomerDAO;
import lk.ijse.hardware.dao.custom.DriverDAO;
import lk.ijse.hardware.dto.DriverDTO;
import lk.ijse.hardware.dto.TransportDTO;
import lk.ijse.hardware.entity.Driver;
import lk.ijse.hardware.entity.Transport;

import java.sql.SQLException;
import java.util.ArrayList;

public class DriverBOImpl implements DriverBO {
    DriverDAO driverDAO=(DriverDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.DRIVER);
    @Override
    public ArrayList<DriverDTO> getAllDriver() throws SQLException, ClassNotFoundException {
        ArrayList<DriverDTO> driverDTOS = new ArrayList<>();
        ArrayList<Driver> drivers = driverDAO.getAll();

        for (Driver driver : drivers) {
            DriverDTO driverDTO = new DriverDTO(driver.getD_id(),driver.getName(),driver.getTel(),driver.getTel(),driver.getWork_time());
            driverDTOS.add(driverDTO);
        }
        return driverDTOS;
    }

    @Override
    public ArrayList<String> getDriverIDs() throws SQLException, ClassNotFoundException {
        return driverDAO.getIDs();
    }

    @Override
    public boolean addDriver(DriverDTO driverDTO) throws SQLException, ClassNotFoundException {
        return driverDAO.add(new Driver(driverDTO.getD_id(),driverDTO.getName(),driverDTO.getTel(),driverDTO.getEmail(),driverDTO.getWork_time()));
    }

    @Override
    public boolean updateDriver(DriverDTO driverDTO) throws SQLException, ClassNotFoundException {
        return driverDAO.update(new Driver(driverDTO.getD_id(),driverDTO.getName(),driverDTO.getTel(),driverDTO.getEmail(),driverDTO.getWork_time()));
    }

    @Override
    public boolean deleteDriver(String id) throws SQLException, ClassNotFoundException {
        return driverDAO.delete(id);
    }

    @Override
    public DriverDTO searchByID(String id) throws SQLException, ClassNotFoundException {
        Driver driver=driverDAO.searchByID(id);
        return new DriverDTO(driver.getD_id(), driver.getName(),driver.getTel(),driver.getEmail(),driver.getWork_time());
    }

    @Override
    public DriverDTO searchByTel(String tell) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return driverDAO.generateNewID();
    }

    @Override
    public boolean existDriver(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
