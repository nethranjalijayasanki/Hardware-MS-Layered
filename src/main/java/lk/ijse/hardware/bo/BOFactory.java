package lk.ijse.hardware.bo;

import lk.ijse.hardware.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){
    }
    public static BOFactory getBoFactory(){
        return (boFactory==null)? boFactory=new BOFactory() : boFactory;
    }

    public enum BOTypes{
        CUSTOMER,DRIVER,EMPLOYEE,ITEM,PLACE_ORDER,SUPPLIER,TRANSPORT
    }
    public SuperBO getBO(BOTypes types){
        switch (types){
            case CUSTOMER:
                return new CustomerBOImpl();
            case DRIVER:
                return new DriverBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case PLACE_ORDER:
                return new PlaceOrderBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            case TRANSPORT:
                return new TransportBOImpl();
            default:
                return null;
        }
    }

}
