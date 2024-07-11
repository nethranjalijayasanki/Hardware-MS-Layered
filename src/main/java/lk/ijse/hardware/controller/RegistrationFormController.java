package lk.ijse.hardware.controller;


import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;


import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import lk.ijse.hardware.db.DbConnection;
import lk.ijse.hardware.util.Regex;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationFormController {

    @FXML
    private JFXButton btnRegister;

    @FXML
    private AnchorPane regRoot;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserId;

    public void initialize() {

        txtUserId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtPassword.requestFocus();
            }
        });
        txtPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtEmail.requestFocus();
            }
        });

        txtEmail.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtContact.requestFocus();
            }
        });

        txtContact.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnRegister.requestFocus();
            }
        });

    }

    @FXML
    void btnBackOnaction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Stage stage = (Stage) regRoot.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnRegisterOnAction(ActionEvent event) {
        String userId = txtUserId.getText();
        String password = txtPassword.getText();
        String email = txtEmail.getText();
        String tel = txtContact.getText();

        try {
            isValied();
            boolean isSaved = saveUser(userId, password, email, tel);
            if(isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "user saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }
    private boolean saveUser(String userId, String password, String email , String tel) throws SQLException {
        String sql = "INSERT INTO users VALUES(?, ?, ?, ?)";

       /* DbConnection dbConnection = DbConnection.getInstance();
        Connection connection = dbConnection.getConnection();*/

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, userId);
        pstm.setObject(2, password);
        pstm.setObject(3, email);
        pstm.setObject(4, tel);

        return pstm.executeUpdate() > 0;
    }

    public void txtEmailOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.hardware.util.TextField.EMAIL,txtEmail);
    }

    public void txtContactOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.hardware.util.TextField.TEL,txtContact);
    }
    private boolean isValied(){
        if (!Regex.setTextColor(lk.ijse.hardware.util.TextField.EMAIL,txtEmail)) return false;
        if (!Regex.setTextColor(lk.ijse.hardware.util.TextField.TEL,txtContact)) return false;
        return false;
    }
}
