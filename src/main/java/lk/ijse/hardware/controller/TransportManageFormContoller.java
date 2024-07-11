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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.hardware.model.Transport;
import lk.ijse.hardware.tm.TransportTm;
import lk.ijse.hardware.dao.TransportRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class TransportManageFormContoller {

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colDriverId;

    @FXML
    private TableColumn<?, ?> colTransportId;

    @FXML
    private TableView<TransportTm> tblTransport;

    @FXML
    private AnchorPane transportRoot;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtDriId;

    @FXML
    private TextField txtTransId;
    public void initialize() {

        txtTransId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtDriId.requestFocus();
            }
        });

        txtDriId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtDescription.requestFocus();
            }
        });
        getCurrentTransportId();
        setCellValueFactory();
        loadAllTransport();
    }

    private void loadAllTransport() {
        ObservableList<TransportTm> obList = FXCollections.observableArrayList();

        try {
            List<Transport> transportList;
            transportList = TransportRepo.getAll();
            for (Transport transport : transportList) {
                TransportTm tm = new TransportTm(
                        transport.getT_id(),
                        transport.getD_id(),
                        transport.getDescription()

                );

                obList.add(tm);
            }

            tblTransport.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colTransportId.setCellValueFactory(new PropertyValueFactory<>("t_id"));
        colDriverId.setCellValueFactory(new PropertyValueFactory<>("d_id"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

    }
    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Stage stage = (Stage) transportRoot.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String t_id = txtTransId.getText();

        try {
            boolean isDeleted = TransportRepo.delete(t_id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "transport deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String t_id = txtTransId.getText();
        String d_id = txtDriId.getText();
        String description = txtDescription.getText();

        Transport transport = new Transport(t_id, d_id, description);

        try {
            boolean isSaved = TransportRepo.save(transport);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "transport saved!").show();
                clearFields();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        txtTransId.setText("");
        txtDriId.setText("");
        txtDescription.setText("");
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String t_id = txtTransId.getText();
        String d_id = txtDriId.getText();
        String description = txtDescription.getText();

        Transport transport = new Transport(t_id, d_id, description);

        try {
            boolean isUpdated = TransportRepo.update(transport);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "transport updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException {
        String id = txtTransId.getText();

        Transport transport = TransportRepo.searchById(id);
        if (transport != null) {
            txtTransId.setText(transport.getT_id());
            txtDriId.setText(transport.getD_id());
            txtDescription.setText(transport.getDescription());

        } else {
            new Alert(Alert.AlertType.INFORMATION, "transport not found!").show();
        }
    }
    private void getCurrentTransportId() {
        try {
            String currentId = TransportRepo.getCurrentId();

            String nextTransportId = generateNextTransportId(currentId);
            txtTransId.setText(nextTransportId);

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

    private String generateNextTransportId(String currentId) {
        if(currentId != null) {
           /* String[] split = currentId.split("O");  //" ", "2"
            int idNum = Integer.parseInt(split[0]);*/
            int c_id = Integer.parseInt(currentId);
            c_id++;

            return String.valueOf(c_id);
        }
        return "1";
    }

}
