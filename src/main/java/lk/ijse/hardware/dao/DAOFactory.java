package lk.ijse.hardware.dao;

import lk.ijse.hardware.dao.custom.PlaceOrderDAO;
import lk.ijse.hardware.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory(){

    }
    public static DAOFactory getDaoFactory(){
        return (daoFactory==null) ? daoFactory=new DAOFactory() : daoFactory;
    }
    public enum DAOTypes{
        NAME,TEL,EMAIL,SALARY,PRICE,QTY,CUSTOMER,ITEM,ORDER,ORDER_DETAILS,DRIVER,EMPLOYEE, PLACE_ORDER,SUPPLIER,TRANSPORT
    }

    public SuperDAO getDAO(DAOTypes types){
        switch (types){
            case CUSTOMER :
                return new CustomerDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case ORDER_DETAILS:
                return new OrderDetailDAOImpl();
            case DRIVER:
                return new DriverDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case PLACE_ORDER:
                return new PlaceOrderDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            case TRANSPORT:
                return new TransportDAOImpl();
            default:
                return null;
        }
    }

}
