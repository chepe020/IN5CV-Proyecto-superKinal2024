/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josealfredo.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.josealfredo.system.Main;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class FormDistribuidoresController implements Initializable {
    private Main Stage;
    
    private static Connection conexion;
    private static PreparedStatement statement;
    
    @FXML
    TextField tfDistribuidorId,tfNombreDistribuidor,tfDireccionDistribuidor,tfNitDistribuidor,tfTelefonoDistribuidor,tfWeb;
    @FXML
    Button btnAceptar,btnCancelar;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location,ResourceBundle resources) {
        // TODO
    }    
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnCancelar){
            Stage.menuDistribuidoresView();
        }
    }

    public Main getStage() {
        return Stage;
    }

    public void setStage(Main Stage) {
        this.Stage = Stage;
    }

    
    
}
