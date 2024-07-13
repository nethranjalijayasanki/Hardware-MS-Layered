package lk.ijse.hardware.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.hardware.bo.BOFactory;
import lk.ijse.hardware.bo.custom.CustomerBO;
import lk.ijse.hardware.bo.custom.DriverBO;
import lk.ijse.hardware.dto.DriverDTO;
import lk.ijse.hardware.entity.Driver;
import lk.ijse.hardware.tm.DriverTm;
import lk.ijse.hardware.dao.custom.impl.CustomerDAOImpl;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DriverFormController {

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private TableColumn<?, ?> colWorkTime;

    @FXML
    private AnchorPane driverRoot;

    @FXML
    private TableView<DriverTm> tblDriver;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtTel;

    @FXML
    private JFXTextField txtWorkTime;

     public DriverBO driverBO=(DriverBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.DRIVER);

    public void initialize() {

        txtId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtName.requestFocus();
            }
        });

        txtName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtWorkTime.requestFocus();
            }
        });

        txtWorkTime.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtTel.requestFocus();
            }
        });

        txtTel.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtEmail.requestFocus();
            }
        });

        setCellValueFactory();
        loadAllDrivers();
    }

    private void loadAllDrivers() {
        ObservableList<DriverTm> obList = FXCollections.observableArrayList();

        try {
            List<DriverDTO> driverList;
            driverList = driverBO.getAllDriver();
            for (DriverDTO driver : driverList) {
                DriverTm tm = new DriverTm(
                        driver.getD_id(),
                        driver.getName(),
                        driver.getTel(),
                        driver.getEmail(),
                        driver.getWork_time()
                );

                obList.add(tm);
            }

            tblDriver.setItems(obList);
        } catch (SQLException| ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("ID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colWorkTime.setCellValueFactory(new PropertyValueFactory<>("Work Hour"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("Tel"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Stage stage = (Stage) driverRoot.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String d_id = txtId.getText();

        try {
            boolean isDeleted = driverBO.deleteDriver(d_id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "driver deleted!").show();
            }
        } catch (SQLException| ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String d_id = txtId.getText();
        String name = txtName.getText();
        String tel = txtTel.getText();
        String email = txtEmail.getText();
        String work_time = txtWorkTime.getText();

        DriverDTO driver = new DriverDTO(d_id, name, tel, email,work_time);

        try {
            boolean isSaved = driverBO.addDriver(driver);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "driver saved!").show();
                clearFields();
            }
        } catch (SQLException| ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtTel.setText("");
        txtEmail.setText("");
        txtWorkTime.setText("");
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String d_id = txtId.getText();
        String name = txtName.getText();
        String tel = txtTel.getText();
        String email = txtEmail.getText();
        String work_time = txtWorkTime.getText();

        DriverDTO driver = new DriverDTO(d_id, name, tel, email,work_time);

        try {
            boolean isUpdated = driverBO.updateDriver(driver);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "driver updated!").show();
            }
        } catch (SQLException| ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = txtId.getText();

        DriverDTO driver = driverBO.searchByID(id);
        if (driver != null) {
            txtId.setText(driver.getD_id());
            txtName.setText(driver.getName());
            txtTel.setText(driver.getTel());
            txtEmail.setText(driver.getEmail());
            txtWorkTime.setText(driver.getWork_time());
        } else {
            new Alert(Alert.AlertType.INFORMATION, "driver not found!").show();
        }
    }

}
