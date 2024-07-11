package lk.ijse.hardware.bo.custom.impl;

import lk.ijse.hardware.bo.custom.PlaceOrderBO;
import lk.ijse.hardware.dao.DAOFactory;
import lk.ijse.hardware.dao.custom.*;
import lk.ijse.hardware.dao.custom.impl.ItemDAOImpl;
import lk.ijse.hardware.dao.custom.impl.OrderDAOImpl;
import lk.ijse.hardware.dao.custom.impl.OrderDetailDAOImpl;
import lk.ijse.hardware.db.DbConnection;
import lk.ijse.hardware.dto.PlacOrderDTO;
import lk.ijse.hardware.dto.TransportDTO;
import lk.ijse.hardware.entity.Place_Order;
import lk.ijse.hardware.entity.Transport;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaceOrderBOImpl implements PlaceOrderBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS);
    PlaceOrderDAO placeOrderDAO=(PlaceOrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PLACE_ORDER);
    @Override
    public ArrayList<PlacOrderDTO> getAllPlacOrder() throws SQLException, ClassNotFoundException {
        ArrayList<PlacOrderDTO> placOrderDTOS = new ArrayList<>();
        ArrayList<Place_Order> placeOrders = placeOrderDAO.getAll();

        for (Place_Order placeOrder : placeOrders) {
            PlacOrderDTO placOrderDTO = new PlacOrderDTO(placeOrder.getOrder(),placeOrder.getOdList());
            placOrderDTOS.add(placOrderDTO);
        }
        return placOrderDTOS;
    }

    @Override
    public ArrayList<String> getPlacOrderIDs() throws SQLException, ClassNotFoundException {
        return placeOrderDAO.getIDs();
    }

    @Override
    public boolean addPlacOrder(PlacOrderDTO placOrderDTO) throws SQLException, ClassNotFoundException {
        return placeOrderDAO.add(new Place_Order(placOrderDTO.getOrder(),placOrderDTO.getOdList()));
    }

    @Override
    public boolean updatePlacOrder(PlacOrderDTO placOrderDTO) throws SQLException, ClassNotFoundException {
        return placeOrderDAO.update(new Place_Order(placOrderDTO.getOrder(),placOrderDTO.getOdList()));
    }

    @Override
    public boolean deletePlacOrder(String id) throws SQLException, ClassNotFoundException {
        return placeOrderDAO.delete(id);
    }

    @Override
    public PlacOrderDTO searchByID(String id) throws SQLException, ClassNotFoundException {
        Place_Order placeOrder=placeOrderDAO.searchByID(id);
        return new PlacOrderDTO(placeOrder.getOrder(),placeOrder.getOdList());
    }

    @Override
    public PlacOrderDTO searchByTel(String tell) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return placeOrderDAO.generateNewID();
    }

    @Override
    public boolean existPlacOrder(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
    public boolean placeOrder(Place_Order po) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isOrderSaved = OrderDAO.add(po.getOrder());
            System.out.println("01"+isOrderSaved);
            if (isOrderSaved) {
                boolean isQtyUpdated = ItemDAOImpl.updateQty(po.getOdList());
                System.out.println("02"+isQtyUpdated);
                if (isQtyUpdated) {
                    boolean isOrderDetailSaved = OrderDetailDAOImpl.save(po.getOdList());
                    System.out.println("03"+isOrderDetailSaved);
                    if (isOrderDetailSaved) {
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }

    }
}
