/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josealfredo.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.josealfredo.system.Main;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class FormDetallesComprasController implements Initializable {
    private Main stage;
    
    @FXML
    ComboBox cmbproductoId,cmbcompraId;
    @FXML
    Button btnAceptar,btnRegresar,btnVaciar;
    @FXML
    TableView tfDetallesCompras;
    @FXML
    TableColumn colDetalleCompraId, colCantidadCompra, colProductoId, colCompraId;
    @FXML
    TextField tfDetalleCompraId,tfCantidadCompra;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location,ResourceBundle resources) {
        // TODO
    }    
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar){
            stage.menuComprasview();
        }
    }
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    
    
}
