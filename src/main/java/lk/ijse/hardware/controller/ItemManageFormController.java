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
import lk.ijse.hardware.bo.BOFactory;
import lk.ijse.hardware.bo.custom.EmployeeBO;
import lk.ijse.hardware.bo.custom.ItemBo;
import lk.ijse.hardware.dto.ItemDTO;
import lk.ijse.hardware.entity.Item;
import lk.ijse.hardware.entity.Order_Detail;
import lk.ijse.hardware.tm.ItemTm;
import lk.ijse.hardware.util.Regex;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ItemManageFormController {


    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colI_id;

    @FXML
    private TableColumn<?, ?> colQtyOnHand;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableColumn<?, ?> colS_id;

    @FXML
    private AnchorPane itemRoot;

    @FXML
    private TableView<ItemTm> tblItem;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtI_id;

    @FXML
    private TextField txtS_id;

    @FXML
    private TextField txtQtyOnHand;

    @FXML
    private TextField txtUnitPrice;

    public ItemBo itemBo=(ItemBo) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);

    public void initialize() {

        txtI_id.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtS_id.requestFocus();
            }
        });
        txtS_id.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtDescription.requestFocus();
            }
        });

        txtDescription.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtUnitPrice.requestFocus();
            }
        });

        txtUnitPrice.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtQtyOnHand.requestFocus();
            }
        });
        getCurrentItemId();
        setCellValueFactory();
        loadAllItems();
    }

    private void loadAllItems() {
        ObservableList<ItemTm> obList = FXCollections.observableArrayList();

        try {
            List<ItemDTO> itemList;
            itemList = itemBo.getAllItem();
            for (ItemDTO item : itemList) {
                ItemTm tm = new ItemTm(
                        item.getI_id(),
                        item.getS_id(),
                        item.getDescription(),
                        item.getUnit_price(),
                        item.getQty_on_hand()

                );

                obList.add(tm);
            }

            tblItem.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colI_id.setCellValueFactory(new PropertyValueFactory<>("i_id"));
        colS_id.setCellValueFactory(new PropertyValueFactory<>("s_id"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

    }
    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Stage stage = (Stage) itemRoot.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String i_id = txtI_id.getText();

        try {
            boolean isDeleted = itemBo.deleteItem(i_id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "item deleted!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String i_id = txtI_id.getText();
        String s_id = txtS_id.getText();
        String description = txtDescription.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());

        try {
            isValied();
            ItemDTO item = new ItemDTO(i_id, s_id,description, unitPrice, qtyOnHand);
            boolean isSaved = itemBo.addItem(item);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "item saved!").show();
                clearFields();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        txtI_id.setText("");
        txtS_id.setText("");
        txtDescription.setText("");
        txtUnitPrice.setText("");
        txtQtyOnHand.setText("");
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String i_id = txtI_id.getText();
        String s_id = txtS_id.getText();
        String description = txtDescription.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());


        ItemDTO item = new ItemDTO(i_id, s_id,description, unitPrice, qtyOnHand);

        try {
            boolean isUpdated = itemBo.updateItem( item);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "item updated!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String i_id = txtI_id.getText();

        ItemDTO item = itemBo.searchByID(i_id);
        if (item != null) {
            txtI_id.setText(item.getI_id());
            txtS_id.setText(item.getS_id());
            txtDescription.setText(item.getDescription());
            txtUnitPrice.setText(String.valueOf(item.getUnit_price()));
            txtQtyOnHand.setText(String.valueOf(item.getQty_on_hand()));
        } else {
            new Alert(Alert.AlertType.INFORMATION, "item not found!").show();
        }
    }

    private void getCurrentItemId() {
        try {
            String currentId = itemBo.generateNewID();

            String nextItemId = generateNextItemId(currentId);
            txtI_id.setText(nextItemId);

        } catch (SQLException | ClassNotFoundException e) {

            throw new RuntimeException(e);
        }
    }

    private String generateNextItemId(String currentId) {
        if(currentId != null) {
           /* String[] split = currentId.split("O");  //" ", "2"
            int idNum = Integer.parseInt(split[0]);*/
            int c_id = Integer.parseInt(currentId);
            c_id++;

            return String.valueOf(c_id);
        }
        return "1";
    }

    public void txtUnitPriceOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.hardware.util.TextField.PRICE,txtUnitPrice);
    }

    public void txtQtyOnHandOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.hardware.util.TextField.QTY,txtQtyOnHand);
    }
    private boolean isValied(){
        if (!Regex.setTextColor(lk.ijse.hardware.util.TextField.PRICE,txtUnitPrice)) return false;
        if (!Regex.setTextColor(lk.ijse.hardware.util.TextField.QTY,txtQtyOnHand)) return false;
        return false;
    }
}
