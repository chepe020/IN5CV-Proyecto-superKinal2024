/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josealfredo.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.josealfredo.dao.Conexion;
import org.josealfredo.dto.DistribuidoresDTO;
import org.josealfredo.model.Distribuidores;
import org.josealfredo.system.Main;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class FormDistribuidoresController implements Initializable {
    private Main Stage;
    private int op;
    
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
        if(DistribuidoresDTO.getDistribuidoresDTO().getDistribuidores() != null){
            cargarDatos(DistribuidoresDTO.getDistribuidoresDTO().getDistribuidores());
        }
    }    
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnCancelar){
            Stage.menuDistribuidoresView();
        }if(event.getSource() == btnAceptar){
            if(op == 1){
                agregarDistribuidores();
                Stage.menuDistribuidoresView();
            }else if(op == 2){
                editarDistribuidores();
                DistribuidoresDTO.getDistribuidoresDTO().setDistribuidores(null);
                Stage.menuDistribuidoresView();
            }
        }
    }

    public void cargarDatos(Distribuidores distribuidores){
        tfDistribuidorId.setText(Integer.toString(distribuidores.getDistribuidorId()));
        tfNombreDistribuidor.setText(distribuidores.getNombreDistribuidor());
        tfDireccionDistribuidor.setText(distribuidores.getDireccionDistribuidor());
        tfNitDistribuidor.setText(distribuidores.getNitDistribuidor());
        tfTelefonoDistribuidor.setText(distribuidores.getTelefonoDistribuidor());
        tfWeb.setText(distribuidores.getWeb());
        
    }
    
    public void agregarDistribuidores(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_AgregarDistribuidores(?,?,?,?,?)";
            statement = conexion.prepareCall(sql);
            statement.setString(1,tfNombreDistribuidor.getText());
            statement.setString(2,tfDireccionDistribuidor.getText());
            statement.setString(3,tfNitDistribuidor.getText());
            statement.setString(4,tfTelefonoDistribuidor.getText());
            statement.setString(5,tfWeb.getText());
            statement.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null){
                    statement.close();
                }if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    } 
    
    public void editarDistribuidores(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_EditarDistribuidores(?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfDistribuidorId.getText()));
            statement.setString(2, tfNombreDistribuidor.getText());
            statement.setString(3, tfDireccionDistribuidor.getText());
            statement.setString(4, tfNitDistribuidor.getText());
            statement.setString(5, tfTelefonoDistribuidor.getText());
            statement.setString(6, tfWeb.getText());
            statement.execute();
                    
        }catch(SQLException e){
           System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null){
                    statement.close();
                }if(conexion != null){
                    conexion.close();
                }        
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    public Main getStage(){ 
        return Stage;
    }

    public void setStage(Main Stage) {
        this.Stage = Stage;
    }

    public void setOp(int op) {
        this.op = op;
    }
}
