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
import lk.ijse.hardware.model.Employee;
import lk.ijse.hardware.tm.EmployeeTm;
import lk.ijse.hardware.dao.EmployeeRepo;
import lk.ijse.hardware.util.Regex;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EmployeeManageFormController {

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private AnchorPane employeeRoot;

    @FXML
    private TableView<EmployeeTm> tblEmployee;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSalary;

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

        txtEmail.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtSalary.requestFocus();
            }
        });
        getCurrentEmployeeId();
        setCellValueFactory();
        loadAllEmployees();
    }

    private void loadAllEmployees() {
        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();

        try {
            List<Employee> employeeList;
            employeeList = EmployeeRepo.getAll();
            for (Employee employee : employeeList) {
                EmployeeTm tm = new EmployeeTm(
                        employee.getE_id(),
                        employee.getName(),
                        employee.getAddress(),
                        employee.getTel(),
                        employee.getEmail(),
                        employee.getSalary()

                );

                obList.add(tm);
            }

            tblEmployee.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("e_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("Tel"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("Salary"));
    }


    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Stage stage = (Stage) employeeRoot.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String e_id = txtId.getText();

        try {
            boolean isDeleted = EmployeeRepo.delete(e_id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "employee deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String e_id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String tel = txtTel.getText();
        String email = txtEmail.getText();
        double salary = Double.parseDouble(txtSalary.getText());


        Employee employee = new Employee(e_id, name, address, tel, email,salary);

        try {
            boolean isSaved = EmployeeRepo.save(employee);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "employee saved!").show();
                clearFields();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtTel.setText("");
        txtEmail.setText("");
        txtSalary.setText("");
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String e_id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String tel = txtTel.getText();
        String email = txtEmail.getText();
        double salary = Double.parseDouble(txtSalary.getText());

        Employee employee = new Employee(e_id, name, address, tel, email,salary);

        try {
            boolean isUpdated = EmployeeRepo.update(employee);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "employee updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException {
        String e_id = txtId.getText();

        Employee employee = EmployeeRepo.searchById(e_id);
        if (employee != null) {
            txtId.setText(employee.getE_id());
            txtName.setText(employee.getName());
            txtAddress.setText(employee.getAddress());
            txtTel.setText(employee.getTel());
            txtEmail.setText(employee.getEmail());
            txtSalary.setText(String.valueOf(employee.getSalary()));
        } else {
            new Alert(Alert.AlertType.INFORMATION, "employee not found!").show();
        }

    }
    private void getCurrentEmployeeId() {
        try {
            String currentId = EmployeeRepo.getCurrentId();

            String nextEmployeeId = generateNextEmployeeId(currentId);
            txtId.setText(nextEmployeeId);

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

    private String generateNextEmployeeId(String currentId) {
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

    public void txtSalaryOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.hardware.util.TextField.PRICE,txtSalary);
    }
}
