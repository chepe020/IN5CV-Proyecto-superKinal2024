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
import javafx.scene.control.cell.PropertyValueFactory;
import org.josealfredo.dao.Conexion;
import org.josealfredo.model.Productos;
import org.josealfredo.system.Main;

/**
 * FXML Controller class
 *
 * @author Chepe
 */
public class MenuProductosController implements Initializable {
    private Main stage;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    @FXML
    Button btnAgregar,btnEditar,btnEliminar,btnRegresar;
    @FXML
    TableView tblProductos;
    @FXML
    TableColumn colproductoId,colnombreProducto,coldescripcionProducto,colcantidadStock,colprecioVentaUnitario,colprecioVentaMayor,colprecioCompra,coldistribuidorId,colcategoriaProductoId;
    
            
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarLista();
        // TODO
    }    
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
        }
    }

    public void cargarLista(){
        tblProductos.setItems(listarProductos());
        colproductoId.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("productoId"));
        colnombreProducto.setCellValueFactory(new PropertyValueFactory<Productos, String>("nombreProducto"));
        coldescripcionProducto.setCellValueFactory(new PropertyValueFactory<Productos, String>("descripcionProducto"));
        colcantidadStock.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("cantidadStock"));
        colprecioVentaUnitario.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioVentaUnitario"));
        colprecioVentaMayor.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioVentaMayor"));
        colprecioCompra.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioCompra"));
        coldistribuidorId.setCellValueFactory(new PropertyValueFactory<Productos, String>("distribuidorId"));
        colcategoriaProductoId.setCellValueFactory(new PropertyValueFactory<Productos, String>("categoriaProductoId"));
        tblProductos.getSortOrder().add(colproductoId);
        
    }
    
    public ObservableList<Productos> listarProductos(){
        ArrayList<Productos> productos = new ArrayList<>(); 
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarProducto()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int productoId = resultSet.getInt("productoId");
                String nombreProducto = resultSet.getString("nombreProducto");
                String descripcionProducto = resultSet.getString("descripcionProducto");
                int cantidadStock = resultSet.getInt("cantidadStock");
                double precioVentaUnitario = resultSet.getDouble("precioVentaUnitario");
                double precioVentaMayor = resultSet.getDouble("precioVentaMayor");
                double precioCompra = resultSet.getDouble("precioCompra");
                int distribuidorId = resultSet.getInt("distribuidorId");
                int categoriaProductoId = resultSet.getInt("categoriaProductoId");
                
                productos.add(new Productos(productoId,nombreProducto,descripcionProducto,cantidadStock,precioVentaUnitario,precioVentaMayor,precioCompra,distribuidorId,categoriaProductoId));
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
        
        return FXCollections.observableList(productos);
    }    
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    
    
}
