package lk.ijse.hardware.bo.custom.impl;

import lk.ijse.hardware.bo.custom.TransportBO;
import lk.ijse.hardware.dao.DAOFactory;
import lk.ijse.hardware.dao.custom.CustomerDAO;
import lk.ijse.hardware.dao.custom.TransportDAO;
import lk.ijse.hardware.dto.CustomerDTO;
import lk.ijse.hardware.dto.TransportDTO;
import lk.ijse.hardware.entity.Customer;
import lk.ijse.hardware.entity.Transport;

import java.sql.SQLException;
import java.util.ArrayList;

public class TransportBOImpl implements TransportBO {
    TransportDAO transportDAO=(TransportDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.TRANSPORT);
    @Override
    public ArrayList<TransportDTO> getAllTransport() throws SQLException, ClassNotFoundException {
        ArrayList<TransportDTO> transportDTOS = new ArrayList<>();
        ArrayList<Transport> transports = transportDAO.getAll();

        for (Transport transport : transports) {
            TransportDTO transportDTO = new TransportDTO(transport.getT_id(),transport.getD_id(),transport.getDescription());
            transportDTOS.add(transportDTO);
        }
        return transportDTOS;
    }

    @Override
    public ArrayList<String> getTransportIDs() throws SQLException, ClassNotFoundException {
        return transportDAO.getIDs();
    }

    @Override
    public boolean addTransport(TransportDTO transportDTO) throws SQLException, ClassNotFoundException {
        return transportDAO.add(new Transport(transportDTO.getT_id(),transportDTO.getD_id(),transportDTO.getDescription()));
    }

    @Override
    public boolean updateTransport(TransportDTO transportDTO) throws SQLException, ClassNotFoundException {
        return transportDAO.update(new Transport(transportDTO.getT_id(),transportDTO.getD_id(),transportDTO.getDescription()));

    }

    @Override
    public boolean deleteTransport(String id) throws SQLException, ClassNotFoundException {
        return transportDAO.delete(id);
    }

    @Override
    public TransportDTO searchByID(String id) throws SQLException, ClassNotFoundException {
        Transport transport=transportDAO.searchByID(id);
        return new TransportDTO(transport.getT_id(), transport.getD_id(), transport.getDescription());
    }

    @Override
    public TransportDTO searchByTel(String tell) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return transportDAO.generateNewID();
    }

    @Override
    public boolean existTransport(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
