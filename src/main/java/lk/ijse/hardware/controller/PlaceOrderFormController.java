package lk.ijse.hardware.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import lk.ijse.hardware.dao.custom.impl.PlaceOrderDAOImpl;
import lk.ijse.hardware.dto.CustomerDTO;
import lk.ijse.hardware.dto.ItemDTO;
import lk.ijse.hardware.entity.*;
import lk.ijse.hardware.tm.CartTm;

import lk.ijse.hardware.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.hardware.dao.custom.OrderDAO;
import lk.ijse.hardware.dao.custom.ItemDAO;
import lk.ijse.hardware.dao.custom.PlaceOrderDAO;

import lk.ijse.hardware.bo.custom.*;
import lk.ijse.hardware.bo.BOFactory;

import lk.ijse.hardware.db.DbConnection;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import lk.ijse.hardware.util.Regex;


public class PlaceOrderFormController {

    @FXML
    private JFXButton btnAddToCart;

    @FXML
    private JFXComboBox<String> cbmCustomerId;

    @FXML
    private JFXComboBox<String> cmbItemId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemId;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtOId;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtQtyOnHand;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblOrderDate;

    @FXML
    private AnchorPane rootPlaceOrder;

    @FXML
    private TableView<CartTm> tblOrderCart;

    PlaceOrderBO placeOrderBO  = (PlaceOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PLACE_ORDER);
    CustomerBO customerBO  = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);
    ItemBo itemBo  = (ItemBo) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);

    private ObservableList<CartTm> obList = FXCollections.observableArrayList();

    public void initialize(){
        setDate();
        getCurrentOrderId();
        getCustomerId();
        getItemId();
        setCellValueFactory();

    }

    private void setCellValueFactory() {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("Qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("Total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnAction"));
    }

    private void getItemId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> codeList = itemBo.getItemIDs();

            for (String code : codeList) {
                obList.add(code);
            }
            cmbItemId.setItems(obList);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void getCustomerId() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = customerBO.getCustomerIDs();

            for(String id : idList) {
                obList.add(id);
            }

            cbmCustomerId.setItems(obList);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String getCurrentOrderId() {
        try {
            return placeOrderBO.generateNewID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new order id").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "OID-001";
    }

    private String generateNextOrderId(String currentId) {
        if(currentId != null) {
           /* String[] split = currentId.split("O");  //" ", "2"
            int idNum = Integer.parseInt(split[0]);*/
            int c_id = Integer.parseInt(currentId);
            c_id++;

            return String.valueOf(c_id);
        }
        return "1";
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        lblOrderDate.setText(String.valueOf(now));
    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String code = cmbItemId.getValue();
        String description = txtDescription.getText();
        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        double total = qty * unitPrice;
        JFXButton btnRemove = new JFXButton("remove");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if(type.orElse(no) == yes) {
                int selectedIndex = tblOrderCart.getSelectionModel().getSelectedIndex();
                obList.remove(selectedIndex);

                tblOrderCart.refresh();
                calculateNetTotal();
            }
        });

        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            if(code.equals(colItemId.getCellData(i))) {

                CartTm tm = obList.get(i);
                qty += tm.getQty();
                total = qty * unitPrice;

                tm.setQty(qty);
                tm.setTotal(total);

                tblOrderCart.refresh();

                calculateNetTotal();
                return;
            }
    }
        CartTm tm = new CartTm(code, description, qty, unitPrice, total, btnRemove);
        obList.add(tm);

        tblOrderCart.setItems(obList);
        calculateNetTotal();
        txtQty.setText("");
    }

    private void calculateNetTotal() {
        int netTotal = 0;
        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            netTotal += (double) colTotal.getCellData(i);
        }
        lblNetTotal.setText(String.valueOf(netTotal));
    }

    @FXML
    public void btnPrintOnAction(ActionEvent event) throws JRException, SQLException {
        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/report/Bill2.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();
        data.put("Total", lblNetTotal.getText());
        data.put("OrderId",txtOId.getText());


        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, data, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);

    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Stage stage = (Stage) rootPlaceOrder.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnNewCustomerOnAction(ActionEvent event) {

    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        String o_id = txtOId.getText();
        Date date = Date.valueOf(LocalDate.now());
        String c_id = cbmCustomerId.getValue();

       var order = new Order(o_id,date,c_id);

        List<Order_Detail> odList = new ArrayList<>();

        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            CartTm tm = obList.get(i);

            Order_Detail od = new Order_Detail(
                    o_id,
                    tm.getId(),
                    tm.getQty(),
                    tm.getUnitPrice()
            );

            odList.add(od);
        }

        Place_Order po = new Place_Order(order, odList);
        try {
            boolean isPlaced = placeOrderBO.placeOrder(po);
            if(isPlaced) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Order Placed Unsuccessfully!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void cmbCustomerIdOnAction(ActionEvent event) throws RuntimeException {
        String id = cbmCustomerId.getValue();
        try {
            CustomerDTO customer = customerBO.searchByID(id);

            txtName.setText(customer.getName());

        } catch (SQLException| ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbItemIdOnAction(ActionEvent event) {
        String code = cmbItemId.getValue();

        try {
            ItemDTO item = itemBo.searchByID(code);
            if(item != null) {
                txtDescription.setText(item.getDescription());
                txtUnitPrice.setText(String.valueOf(item.getUnit_price()));
                txtQtyOnHand.setText(String.valueOf(item.getQty_on_hand()));
            }

            txtQty.requestFocus();

        } catch (SQLException| ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {
        btnAddToCartOnAction(event);
    }

    public void txtQtyOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.hardware.util.TextField.QTY,txtQty);
    }
    private boolean isValied(){
       if (!Regex.setTextColor(lk.ijse.hardware.util.TextField.QTY,txtQty)) return false;
        return false;
    }
}
