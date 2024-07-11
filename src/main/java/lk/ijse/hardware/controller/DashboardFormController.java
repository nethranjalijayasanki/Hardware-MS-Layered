package lk.ijse.hardware.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DashboardFormController {

    @FXML
    private AnchorPane dashboardRoot1;

    @FXML
    public Label lblCustomerCount;


    @FXML
    public Label lblOrderCount;

    @FXML
    private AnchorPane root;

    @FXML
    private JFXTextField txtSearch;

public void initialize(){
    try {
        AnchorPane root1 = FXMLLoader.load(getClass().getResource("/view/innerDashboart.fxml"));
        dashboardRoot1.getChildren().clear();
        dashboardRoot1.getChildren().add(root1);

    } catch (IOException e) {
        throw new RuntimeException(e);
    }

}



    @FXML
    void btnCustomerOnAction(ActionEvent event) {
        try {
            AnchorPane root1 = FXMLLoader.load(getClass().getResource("/view/customerManage_form.fxml"));
            dashboardRoot1.getChildren().clear();
            dashboardRoot1.getChildren().add(root1);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnEmployeeOnAction(ActionEvent event) {
        try {
            AnchorPane root1 = FXMLLoader.load(getClass().getResource("/view/employeeManage_form.fxml"));
            dashboardRoot1.getChildren().clear();
            dashboardRoot1.getChildren().add(root1);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnItemOnAction(ActionEvent event) {
        try {
            AnchorPane root1 = FXMLLoader.load(getClass().getResource("/view/itemManage_form.fxml"));
            dashboardRoot1.getChildren().clear();
            dashboardRoot1.getChildren().add(root1);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        try {
            AnchorPane root1 = FXMLLoader.load(getClass().getResource("/view/placeOrder_form.fxml"));
            dashboardRoot1.getChildren().clear();
            dashboardRoot1.getChildren().add(root1);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnRegisterOnAction(ActionEvent event) {
        try {
            AnchorPane root1 = FXMLLoader.load(getClass().getResource("/view/registration_form.fxml"));
            dashboardRoot1.getChildren().clear();
            dashboardRoot1.getChildren().add(root1);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void btnSupplierOnAction(ActionEvent event) {
        try {
            AnchorPane root1 = FXMLLoader.load(getClass().getResource("/view/supplier_form.fxml"));
            dashboardRoot1.getChildren().clear();
            dashboardRoot1.getChildren().add(root1);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnTransportOnAction(ActionEvent event) {
        try {
            AnchorPane root1 = FXMLLoader.load(getClass().getResource("/view/transportManage_form.fxml"));
            dashboardRoot1.getChildren().clear();
            dashboardRoot1.getChildren().add(root1);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
