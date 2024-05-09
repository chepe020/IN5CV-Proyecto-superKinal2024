 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josealfredo.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
import org.josealfredo.model.Compras;
import org.josealfredo.system.Main;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class MenuComprasController implements Initializable {
    private Main stage;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    @FXML
    Button btnGuardar,btnEliminar,btnVaciar,btnRegresar,btnBuscar,btnFormDerallecompra;   
    @FXML
    TableView tblCompras;
    @FXML
    TableColumn colCompraId,colFechaCompra,colTotalCompra;
    @FXML
    TextField tfComprasId,tfFechaCompra,tfTotalCompra,tfBuscar;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
        cargarLista();
    }    
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnGuardar){
            agregarCompras();
            cargarLista();
        }else if(event.getSource() == btnFormDerallecompra){
            
        }
    }
    
    public void cargarLista(){
        tblCompras.setItems(listarCompras());
        colCompraId.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("compraId"));
        colFechaCompra.setCellValueFactory(new PropertyValueFactory<Compras, Date >("fechaCompra"));
        colTotalCompra.setCellValueFactory(new PropertyValueFactory<Compras, Double>("totalCompra"));
    }
    
    
    public ObservableList<Compras> listarCompras(){
        ArrayList<Compras> compras = new ArrayList<>();
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarCompras()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int compraId = resultSet.getInt("compraId");
                LocalDate fechaCompra = resultSet.getDate("fechaCompra").toLocalDate();
                double totalCompra = resultSet.getDouble("totalCompra");
                
                compras.add(new Compras(compraId,fechaCompra,totalCompra));
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
        return FXCollections.observableList(compras);
    }
    
    public void agregarCompras(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_AgregarCompras(?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setDate(1, Date.valueOf(tfFechaCompra.getText()));
            statement.setDouble(2, Double.parseDouble(tfTotalCompra.getText()));
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

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    
}
