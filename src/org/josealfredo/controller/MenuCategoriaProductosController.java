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
import org.josealfredo.model.CategoriaProductos;
import org.josealfredo.system.Main;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class MenuCategoriaProductosController implements Initializable {
    private Main stage;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    @FXML
    TableView tblCategoriaProducto;
    @FXML
    TableColumn colCategoriaProductoId , colNombreCategoria , colDescripcionCategoria;
    @FXML
    Button btnAgregar,btnEliminar,btnVaciar,btnRegresar,btnBuscar;
    @FXML
    TextField tfCategoriaProductoId,tfNombreCategoria,tfDescripcionCategoria,tfBuscar;
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
        }else if(event.getSource() == btnAgregar){
            if(tfCategoriaProductoId.getText().equals("")){
                agregarCategoriaProductos();
                cargarLista();
            }else{
                editarCategoriaProductos();
               cargarLista();
            }
        }else if(event.getSource() == btnVaciar){
            vaciarcampos();
        }else if(event.getSource() == btnEliminar){
            int catId = (((CategoriaProductos)tblCategoriaProducto.getSelectionModel().getSelectedItem()).getCategoriaProductoId());
            eliminarCategoriaProductos(catId);
            cargarLista();
        }else if(event.getSource() == btnBuscar){
            tblCategoriaProducto.getItems().clear();
            if(tfBuscar.getText().equals("")){
                cargarLista();
            }else{
                tblCategoriaProducto.getItems().add(buscarCategoriaProductos());
                colCategoriaProductoId.setCellValueFactory(new PropertyValueFactory<CategoriaProductos, Integer>("categoriaProductoId"));
                colNombreCategoria.setCellValueFactory(new PropertyValueFactory<CategoriaProductos, String>("nombreCategoria"));
                colDescripcionCategoria.setCellValueFactory(new PropertyValueFactory<CategoriaProductos, String>("descripcionCategoria"));
            }
        }
    }
    
    
    
    public void vaciarcampos(){
        tfCategoriaProductoId.clear();
        tfNombreCategoria.clear();
        tfDescripcionCategoria.clear();
    }
    
    public void cargarLista(){
        tblCategoriaProducto.setItems(listarCargos());
        colCategoriaProductoId.setCellValueFactory(new PropertyValueFactory<CategoriaProductos, Integer>("categoriaProductoId"));
        colNombreCategoria.setCellValueFactory(new PropertyValueFactory<CategoriaProductos, String>("nombreCategoria"));
        colDescripcionCategoria.setCellValueFactory(new PropertyValueFactory<CategoriaProductos, String>("descripcionCategoria"));
    }
    
        public void cargarDatosEditar(){
        CategoriaProductos cp = (CategoriaProductos)tblCategoriaProducto.getSelectionModel().getSelectedItem();
        if(cp != null){
            tfCategoriaProductoId.setText(Integer.toString(cp.getCategoriaProductoId()));
            tfNombreCategoria.setText(cp.getNombreCategoria());
            tfDescripcionCategoria.setText(cp.getDescripcionCategoria());
        } 
    }
    public ObservableList<CategoriaProductos> listarCargos(){
        ArrayList<CategoriaProductos> categoriaProductos = new ArrayList<>();
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarCategoriaProductos()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int categoriaProductoId = resultSet.getInt("categoriaProductoId");
                String nombreCategoria = resultSet.getString("nombreCategoria");
                String descripcionCategoria = resultSet.getString("descripcionCategoria");
                
                categoriaProductos.add(new CategoriaProductos(categoriaProductoId,nombreCategoria,descripcionCategoria));
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
        
        return FXCollections.observableList(categoriaProductos);
    }    
    
    public void agregarCategoriaProductos(){
        try{
           conexion = Conexion.getInstance().obtenerConexion();
           String sql = "call sp_AgregarCategoriaProductos(?,?)";
           statement = conexion.prepareStatement(sql);
           statement.setString(1,tfNombreCategoria.getText());
           statement.setString(2,tfDescripcionCategoria.getText());
           statement.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try{
                if(statement != null){
                    statement.close();
                }if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
        }   
    }
    
    public void editarCategoriaProductos(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_EditarCategoriaProductos(?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCategoriaProductoId.getText()));           
            statement.setString(2,tfNombreCategoria.getText());
            statement.setString(3,tfDescripcionCategoria.getText());
            statement.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();

        }finally{
            try{
                if(statement != null){
                    statement.close();
                }if(conexion != null){
                    conexion.close();
                }        
            }catch(SQLException e){
                System.out.println(e.getMessage());
                e.printStackTrace();

            }
        }
    }
    
    public void eliminarCategoriaProductos(int catId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_EliminarCategoriaProductos(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, catId);
            statement.execute();   
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null){
                    statement.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    public CategoriaProductos buscarCategoriaProductos(){
        CategoriaProductos categoriaProductos = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_BuscarCategoriaProductos(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,Integer.parseInt(tfBuscar.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int categoriaProductoId = resultSet.getInt("categoriaProductoId");
                String nombreCategoria = resultSet.getString("nombreCategoria");
                String descripcionCategoria = resultSet.getString("descripcionCategoria");
                
                categoriaProductos = new CategoriaProductos(categoriaProductoId,nombreCategoria,descripcionCategoria);
            }
        }catch(SQLException e){
           System.out.println(e.getMessage()); 
        }finally{
            try{
                if(resultSet != null){
                    resultSet.close();
                }if(statement != null){
                    statement.close();
                }if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return categoriaProductos;
    }
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
}
