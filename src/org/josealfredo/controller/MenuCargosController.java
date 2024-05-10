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
import org.josealfredo.model.Cargos;
import org.josealfredo.system.Main;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class MenuCargosController implements Initializable {
    private Main stage;

    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    @FXML
    Button btnagregar,btnVaciar,btnEliminar,btnRegresar,btnBuscar;
    @FXML
    TableView tblCargos;
    @FXML
    TableColumn colCargoId,colNombreCargo,colDescripcionCargo;
    @FXML
    TextField tfCargoId,tfNombreCargo,tfDescripcionCargo,tfBuscar;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location,ResourceBundle resources) {
        // TODO
        cargarLista();
    }
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar){
           stage.menuPrincipalView();
        }else if(event.getSource() == btnagregar){
            if(tfCargoId.getText().equals("")){
                agregarCargos();
                cargarLista();
            }else{
                editarCargos();
                cargarLista();
            }
        }else if(event.getSource() == btnVaciar){
            vaciarcampos();
        }else if(event.getSource() == btnEliminar){
            int carId =(((Cargos)tblCargos.getSelectionModel().getSelectedItem()).getCargoId());
            eliminarCargos(carId);
            cargarLista();
        }else if(event.getSource() == btnBuscar){
            tblCargos.getItems().clear();
            if(tfBuscar.getText().equals("")){
                cargarLista();
            }else{
                tblCargos.getItems().add(buscarCargo());
                colCargoId.setCellValueFactory(new PropertyValueFactory<Cargos, Integer>("cargoId"));
                colNombreCargo.setCellValueFactory(new PropertyValueFactory<Cargos, String>("nombreCargo"));
                colDescripcionCargo.setCellValueFactory(new PropertyValueFactory<Cargos, String>("descripcionCargo"));
            }
        }
    } 
    
    public void vaciarcampos(){
        tfCargoId.clear();
        tfNombreCargo.clear();
        tfDescripcionCargo.clear();
    }
    public void cargarLista(){
        tblCargos.setItems(listarCargos());
        colCargoId.setCellValueFactory(new PropertyValueFactory<Cargos, Integer>("cargoId"));
        colNombreCargo.setCellValueFactory(new PropertyValueFactory<Cargos, String>("nombreCargo"));
        colDescripcionCargo.setCellValueFactory(new PropertyValueFactory<Cargos, String>("descripcionCargo"));
    }
    
    public void cargarDatosEditar(){
        Cargos cs = (Cargos)tblCargos.getSelectionModel().getSelectedItem();
        if(cs != null){
            tfCargoId.setText(Integer.toString(cs.getCargoId()));
            tfNombreCargo.setText(cs.getNombreCargo());
            tfDescripcionCargo.setText(cs.getDescripcionCargo());
        } 
    }
    public ObservableList<Cargos> listarCargos(){
        ArrayList<Cargos> cargo = new ArrayList<>();
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarCargos();";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int cargoId = resultSet.getInt("cargoId");
                String nombreCargo = resultSet.getString("nombreCargo");
                String descripcionCargo = resultSet.getString("descripcionCargo");
                
                cargo.add(new Cargos(cargoId,nombreCargo,descripcionCargo));
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
        return FXCollections.observableList(cargo);
    }
    
    public void agregarCargos(){
        try{
           conexion = Conexion.getInstance().obtenerConexion();
           String sql = "call sp_AgregarCargos(?,?)";
           statement = conexion.prepareStatement(sql);
           statement.setString(1,tfNombreCargo.getText());
           statement.setString(2,tfDescripcionCargo.getText());
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
    
    public void editarCargos(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_EditarCargos(?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCargoId.getText()));
            statement.setString(2, tfNombreCargo.getText());
            statement.setString(3, tfDescripcionCargo.getText());
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
    
    public void eliminarCargos(int carId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_EliminarCargos(?);";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, carId);
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
    
    public Cargos buscarCargo(){
        Cargos cargos = null;
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_BuscarCargos(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,Integer.parseInt(tfBuscar.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int cargoId = resultSet.getInt("cargoId");
                String nombreCargo = resultSet.getString("nombreCargo");
                String descripcionCargo = resultSet.getString("descripcionCargo");
                
                cargos = new Cargos(cargoId,nombreCargo,descripcionCargo); 
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
        
        return cargos;
    }
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    
    
}
