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
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import org.josealfredo.system.Main;
import org.josealfredo.utils.SuperKInalAlerta;
/**
 *
 * @author Alfredo
 */
public class MenuPrincipalController implements Initializable {
    private Main stage;
    
    @FXML
    MenuItem btnMenuClientes ,btnTicketSoporte,btnCargos,btnCompras,btnCategoriaProductos,btnDistribuidores,btnEmpleados,btnProductos,btnSalir;
    
    @Override
    public void initialize(URL location,ResourceBundle resources){
    
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnMenuClientes){
            stage.menuClientesView();
        }else if(event.getSource() == btnTicketSoporte){
            stage.menuTicketSoporteView();
        }else if(event.getSource() == btnCargos){
            stage.menuCargosView();
        }else if(event.getSource() == btnCompras){
            stage.menuComprasview();
        }else if(event.getSource() == btnCategoriaProductos){
            stage.menuCategoriaProductosView();
        }else if(event.getSource() == btnDistribuidores){
            stage.menuDistribuidoresView();
        }else if(event.getSource() == btnEmpleados){
            stage.MenuEmpleadosView();
        }else if(event.getSource() == btnProductos){
            stage.menuProductosView();
        }else if(event.getSource() == btnSalir){            
            if (SuperKInalAlerta.getInstance().mostrarAlertaConfi(4).get() == ButtonType.OK){
               stage.loginView();
            }else{}
          
        }
    }
}

