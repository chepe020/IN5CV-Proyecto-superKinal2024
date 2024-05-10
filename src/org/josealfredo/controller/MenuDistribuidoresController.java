/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josealfredo.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.josealfredo.dao.Conexion;
import org.josealfredo.model.Distribuidores;
import org.josealfredo.system.Main;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class MenuDistribuidoresController implements Initializable {
    public Main stage;

    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    @FXML
    Button btnAgregar,btnEditar,btnEliminar,btnRegresar,btnBuscar;
    @FXML
    TableView tblMenuDistribuidores;
    @FXML
    TableColumn coldistribuidorId,colnombreDistribuidor,coldireccionDistribuidor,colnitDistribuidor,coltelefonoDistribuidor,colWeb;
    @FXML
    TextField tfBuscar;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location,ResourceBundle resources) {
        // TODO
        cargarLista();
    }
    
    public void cargarLista(){
        tblMenuDistribuidores.setItems(listarDistribuidores());
        coldistribuidorId.setCellValueFactory(new PropertyValueFactory<Distribuidores, Integer>("distribuidorId"));
        colnombreDistribuidor.setCellValueFactory(new PropertyValueFactory<Distribuidores, String>("nomnombreDistribuidorbre"));
        coldireccionDistribuidor.setCellValueFactory(new PropertyValueFactory<Distribuidores, String>("direccionDistribuidor"));
        colnitDistribuidor.setCellValueFactory(new PropertyValueFactory<Distribuidores, String>("nitDistribuidor"));
        coltelefonoDistribuidor.setCellValueFactory(new PropertyValueFactory<Distribuidores, String>("telefonoDistribuidor"));
        colWeb.setCellValueFactory(new PropertyValueFactory<Distribuidores, String>("web"));
    }
    
    public ObservableList<Distribuidores> listarDistribuidores(){
        ArrayList<Distribuidores> distribuidores = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarDistribuidores();";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
               int distribuidorId = resultSet.getInt("distribuidorId");
               String nombreDistribuidor = resultSet.getString("nombreDistribuidor");
               String direccionDistribuidor = resultSet.getString("direccionDistribuidor");
               String nitDistribuidor = resultSet.getString("nitDistribuidor");
               String telefonoDistribuidor = resultSet.getString("telefonoDistribuidor");
               String web = resultSet.getString("web");
               
               distribuidores.add(new Distribuidores(distribuidorId,nombreDistribuidor,direccionDistribuidor,nitDistribuidor,telefonoDistribuidor,web));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(resultSet != null){
                    resultSet.close();
                }
                if(statement != null){
                    statement.close();
                }
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
               System.out.println(e.getMessage());
            }
        }
        
        return FXCollections.observableList(distribuidores);

     }
     
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar){
           stage.menuPrincipalView();
        }else if(event.getSource() == btnAgregar){
            stage.forDistribuidores();
        }
    }
    
    

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    
}
