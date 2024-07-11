package lk.ijse.hardware.controller;

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
import lk.ijse.hardware.model.Supplier;
import lk.ijse.hardware.tm.SupplierTm;
import lk.ijse.hardware.dao.SupplierRepo;
import lk.ijse.hardware.util.Regex;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SupplierFormController {

    @FXML
    private TableColumn<?, ?> colCompany;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private AnchorPane supplierRoot;

    @FXML
    private TableView<SupplierTm> tblSupplier;

    @FXML
    private TextField txtCompany;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtTel;
    public void initialize() {

        txtId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtName.requestFocus();
            }
        });

        txtName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtCompany.requestFocus();
            }
        });

        txtCompany.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtTel.requestFocus();
            }
        });

        txtTel.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtEmail.requestFocus();
            }
        });
        getCurrentSupplierId();
        setCellValueFactory();
        loadAllSuppliers();
    }

    private void loadAllSuppliers() {
        ObservableList<SupplierTm> obList = FXCollections.observableArrayList();

        try {
            List<Supplier> supplierList;
            supplierList = SupplierRepo.getAll();
            for (Supplier supplier : supplierList) {
                SupplierTm tm = new SupplierTm(
                        supplier.getS_id(),
                        supplier.getName(),
                        supplier.getCompany(),
                        supplier.getTel(),
                        supplier.getEmail()
                );

                obList.add(tm);
            }

            tblSupplier.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("s_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colCompany.setCellValueFactory(new PropertyValueFactory<>("Company"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("Tel"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
    }
    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Stage stage = (Stage) supplierRoot.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String c_id = txtId.getText();

        try {
            boolean isDeleted = SupplierRepo.delete(c_id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "supplier deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String c_id = txtId.getText();
        String name = txtName.getText();
        String company = txtCompany.getText();
        String tel = txtTel.getText();
        String email = txtEmail.getText();



        try {
            isValied();
            Supplier supplier = new Supplier(c_id, name, company, tel, email);
            boolean isSaved = SupplierRepo.save(supplier);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "supplier saved!").show();
                clearFields();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtCompany.setText("");
        txtTel.setText("");
        txtEmail.setText("");
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String s_id = txtId.getText();
        String name = txtName.getText();
        String company = txtCompany.getText();
        String tel = txtTel.getText();
        String email = txtEmail.getText();

        Supplier supplier = new Supplier(s_id, name, company, tel, email);

        try {
            boolean isUpdated = SupplierRepo.update(supplier);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "supplier updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException {
        String id = txtId.getText();

        Supplier supplier = SupplierRepo.searchById(id);
        if (supplier != null) {
            txtId.setText(supplier.getS_id());
            txtName.setText(supplier.getName());
            txtCompany.setText(supplier.getCompany());
            txtTel.setText(supplier.getTel());
            txtEmail.setText(supplier.getEmail());
        } else {
            new Alert(Alert.AlertType.INFORMATION, "supplier not found!").show();
        }
    }

    private void getCurrentSupplierId() {
        try {
            String currentId = SupplierRepo.getCurrentId();

            String nextSupplierId = generateNextSupplierId(currentId);
            txtId.setText(nextSupplierId);

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

    private String generateNextSupplierId(String currentId) {
        if(currentId != null) {
           /* String[] split = currentId.split("O");  //" ", "2"
            int idNum = Integer.parseInt(split[0]);*/
            int c_id = Integer.parseInt(currentId);
            c_id++;

            return String.valueOf(c_id);
        }
        return "1";
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
