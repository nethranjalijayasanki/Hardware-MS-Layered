package lk.ijse.hardware.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.hardware.bo.BOFactory;
import lk.ijse.hardware.bo.custom.CustomerBO;
import lk.ijse.hardware.dto.CustomerDTO;
import lk.ijse.hardware.entity.Customer;
import lk.ijse.hardware.tm.CustomerTm;
import lk.ijse.hardware.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.hardware.util.Regex;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CustomerManageFormController {

    @FXML
    public JFXButton btnDelete;

    @FXML
    public JFXButton btnSave;

    @FXML
    public JFXButton btnUpdate;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private AnchorPane customerRoot;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtTel;

    public CustomerBO customerBO=(CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    public void initialize() {

        txtId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtName.requestFocus();
            }
        });

        txtName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtAddress.requestFocus();
            }
        });

        txtAddress.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtTel.requestFocus();
            }
        });

        txtTel.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtEmail.requestFocus();
            }
        });
        getCurrentCustomerId();
        setCellValueFactory();
        loadAllCustomers();
    }

    private void loadAllCustomers() {
        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();
        try {
            List<CustomerDTO> customerList = customerBO.getAllCustomers();
            for (CustomerDTO customer : customerList){
                CustomerTm customerTm = new CustomerTm(customer.getC_id(), customer.getName(), customer.getAddress(), customer.getTel(), customer.getEmail());
                obList.add(customerTm);
            }
            tblCustomer.setItems(obList);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("c_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("Tel"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Stage stage = (Stage) customerRoot.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }
    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerBO.existCustomer(id);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = tblCustomer.getSelectionModel().getSelectedItem().getC_id();
        try {
            if (!existCustomer(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
            }
            customerBO.deleteCustomer(id);

            tblCustomer.getItems().remove(tblCustomer.getSelectionModel().getSelectedItem());
            tblCustomer.getSelectionModel().clearSelection();
            clearFields();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the customer " + id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String c_id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String tel = txtTel.getText();
        String email = txtEmail.getText();

        if (!name.matches("[A-Za-z ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid name").show();
            txtName.requestFocus();
            return;
        } else if (!address.matches(".{3,}")) {
            new Alert(Alert.AlertType.ERROR, "Address should be at least 3 characters long").show();
            txtAddress.requestFocus();
            return;
        } if (btnSave.getText().equalsIgnoreCase("save")) {
            /*Save Customer*/
            try {
                if (existCustomer(c_id)) {
                    new Alert(Alert.AlertType.ERROR, c_id + " already exists").show();
                }

                //Add Customer
                customerBO.addCustomer(new CustomerDTO(c_id,name,address,tel,email));

                tblCustomer.getItems().add(new CustomerTm(c_id,name,address,tel,email));
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to save the customer " + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        } else {
            /*Update customer*/
            try {
                if (!existCustomer(c_id)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + c_id).show();
                }
                //Update Customer
                customerBO.updateCustomer(new CustomerDTO(c_id,name,address,tel,email));

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to update the customer " + c_id + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            CustomerTm selectedCustomer = tblCustomer.getSelectionModel().getSelectedItem();
            selectedCustomer.setName(name);
            selectedCustomer.setAddress(address);
            tblCustomer.refresh();
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtTel.setText("");
        txtEmail.setText("");
    }
    /*

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String c_id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String tel = txtTel.getText();
        String email = txtEmail.getText();

        Customer customer = new Customer(c_id, name, address, tel, email);

        try {
            boolean isUpdated = CustomerDAOImpl.update(customer);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

     */

    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException {
        String tel = txtTel.getText();

        try {
            CustomerDTO customer = customerBO.searchByTel(tel);
            if (customer != null) {
                txtId.setText(customer.getC_id());
                txtName.setText(customer.getName());
                txtAddress.setText(customer.getAddress());
                txtTel.setText(String.valueOf(customer.getTel()));
            } else {
                new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            //throw new RuntimeException(e);
        }
/*
        Customer customer = CustomerDAOImpl.searchByTel(tel);
        if (customer != null) {
            txtId.setText(customer.getC_id());
            txtName.setText(customer.getName());
            txtAddress.setText(customer.getAddress());
            txtTel.setText(customer.getTel());
            txtEmail.setText(customer.getEmail());
        }

 */

    }

    private void getCurrentCustomerId() {
        try {
            String nextOrderId = customerBO.generateNewID();
            txtId.setText(nextOrderId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void txtNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.hardware.util.TextField.NAME,txtName);
    }

    public void txtTelOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.hardware.util.TextField.TEL,txtTel);
    }

    public void txtEmailOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.hardware.util.TextField.EMAIL,txtEmail);
    }
    private boolean isValied(){
        if (!Regex.setTextColor(lk.ijse.hardware.util.TextField.NAME,txtName)) return false;
        if (!Regex.setTextColor(lk.ijse.hardware.util.TextField.TEL,txtTel)) return false;
        if (!Regex.setTextColor(lk.ijse.hardware.util.TextField.EMAIL,txtEmail)) return false;
        return false;
    }
}
