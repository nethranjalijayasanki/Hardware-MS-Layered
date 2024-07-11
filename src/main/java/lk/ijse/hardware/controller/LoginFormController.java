package lk.ijse.hardware.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.hardware.Launcher;
import lk.ijse.hardware.db.DbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {

    @FXML
    private JFXButton btnLogin;

    @FXML
    private AnchorPane dashboardRoot;

    @FXML
    private AnchorPane formRoot;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtPassword;

    public void initialize(){
        txtId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtPassword.requestFocus();
            }
        });
        txtPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnLogin.requestFocus();
            }
        });

    }

    @FXML
    void btnLoginOnAction(ActionEvent event) throws SQLException, IOException {
        String password = txtPassword.getText();
        String username = txtId.getText();

        checkCredential(password, username);

    }

    private void checkCredential(String password, String username) throws SQLException,IOException {
        String sql = "SELECT userId, password from users where userId = ? ";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, username);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String dbPw = resultSet.getString("password");

            if(password.equals(dbPw)) {
                navigateToTheDashboard();
            } else {
                new Alert(Alert.AlertType.ERROR, "sorry! password is incorrect!").show();
            }

        } else {
            new Alert(Alert.AlertType.INFORMATION, "sorry! user id can't be find!").show();
        }

    }

    private void navigateToTheDashboard() throws IOException {
        AnchorPane root = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        Scene scene = new Scene(root);

        //Stage stage = (Stage) this.root.getScene().getWindow();

        Stage stage = Launcher.stage;

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
    }


}


