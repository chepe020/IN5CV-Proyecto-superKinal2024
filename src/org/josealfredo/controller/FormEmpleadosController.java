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
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.josealfredo.dao.Conexion;
import org.josealfredo.dto.EmpleadosDTO;
import org.josealfredo.model.Cargos;
import org.josealfredo.model.Empleados;
import org.josealfredo.system.Main;

/**
 * FXML Controller class
 *
 * @author Chepe
 */
public class FormEmpleadosController implements Initializable {
    private Main Stage;
    private int op;
    
    @FXML
    TextField tfempleadoId,tfnombreEmpleado,tfapellidoEmpleado,tfsueldo,tfhoraEntrada,tfhoraSalida;
    @FXML
    ComboBox cmbcargoId,cmbencargadoId;
    @FXML   
    Button btnAceptar,btnCancelar;

    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbcargoId.setItems(listarCargos());
        cmbencargadoId.setItems(listarEmpleados());
        
        if(EmpleadosDTO.getEmpleadosDTO().getEmpleados() != null){
            carcargarDatos(EmpleadosDTO.getEmpleadosDTO().getEmpleados());
        }
    }    
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnCancelar){
            Stage.MenuEmpleadosView();
        }if(event.getSource() == btnAceptar){
            if(op == 1){
                agregarEmpleados();
                Stage.MenuEmpleadosView();
            }else if(op == 2){
                editarEmpleados();
                Stage.MenuEmpleadosView();
            }
        }
    }
    

    
    public void carcargarDatos(Empleados empleados){
        tfempleadoId.setText(Integer.toString(empleados.getEmpleadoId()));
        tfnombreEmpleado.setText(empleados.getNombreEmpleado());
        tfapellidoEmpleado.setText(empleados.getApellidoEmpleado());
        tfsueldo.setText(Double.toString(empleados.getSueldo()));
        tfhoraEntrada.setText(empleados.getHoraEntrada().toString());
        tfhoraSalida.setText(empleados.getHoraSalida().toString());
        cmbcargoId.getSelectionModel().select(obtenerIndexCargos());
        cmbencargadoId.getSelectionModel().select(obtenerIndexEncargados());
    }
    
    public int obtenerIndexCargos(){
        int index = 0;
        for(int i = 0 ; i < cmbcargoId.getItems().size() ; i++){
            String cargoIdCmb = cmbcargoId.getItems().get(i).toString();
            String cargoTb1 = Integer.toString(EmpleadosDTO.getEmpleadosDTO().getEmpleados().getCargoId());
            if(cargoIdCmb.equals(cargoTb1)){
                index = i;
                break;
            }
        }
        
        return index;
    }
    
    public int obtenerIndexEncargados(){
        int index = 0;
        for(int i = 0 ; i < cmbencargadoId.getItems().size() ; i++){
            String encargadoIdCmb = cmbencargadoId.getItems().get(i).toString();
            String encargadoTb1 = Integer.toString(EmpleadosDTO.getEmpleadosDTO().getEmpleados().getEncargadoId());
            if(encargadoIdCmb.equals(encargadoTb1)){
                index = i;
            }
        }
        return index;
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
    
    
    public ObservableList<Empleados> listarEmpleados(){
        ArrayList<Empleados> empleados = new ArrayList<>();
            
            try {
                conexion = Conexion.getInstance().obtenerConexion();
                String sql = "call sp_listarEmpleados()";
                statement = conexion.prepareStatement(sql);
                resultSet = statement.executeQuery();
                
                while(resultSet.next()){
                    int empleadoId = resultSet.getInt("empleadoId");
                    String nombreEmpleado = resultSet.getString("nombreEmpleado");
                    String apellidoEmpleado = resultSet.getString("apellidoEmpleado");
                    double sueldo = resultSet.getDouble("sueldo");
                    LocalTime horaEntrada = resultSet.getTime("horaEntrada").toLocalTime();
                    LocalTime horaSalida = resultSet.getTime("horaSalida").toLocalTime();
                    String cargo = resultSet.getString("cargo");
                    String encargado = resultSet.getString("encargado");
                    
                    empleados.add(new Empleados(empleadoId,nombreEmpleado,apellidoEmpleado,sueldo,horaEntrada,horaSalida,cargo,encargado));
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
                e.printStackTrace();

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
                    e.printStackTrace();

                } 
            }
            
        return FXCollections.observableList(empleados);
    }

    public void agregarEmpleados(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_AgregarEmpleados(?,?,?,?,?,?,?);";
            statement = conexion.prepareStatement(sql);
            statement.setString(1,tfnombreEmpleado.getText());
            statement.setString(2, tfapellidoEmpleado.getText());
            statement.setDouble(3, Double.parseDouble(tfsueldo.getText()));
            statement.setTime(4, Time.valueOf(tfhoraEntrada.getText()));
            statement.setTime(5, Time.valueOf(tfhoraSalida.getText()));
            statement.setInt(6, ((Cargos)cmbcargoId.getSelectionModel().getSelectedItem()).getCargoId());
            statement.setInt(7, ((Empleados)cmbencargadoId.getSelectionModel().getSelectedItem()).getEmpleadoId());
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
    
    public void editarEmpleados(){
        try{
           conexion = Conexion.getInstance().obtenerConexion();
           String sql = "call sp_EditarEmpleados(?,?,?,?,?,?,?,?)";
           statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfempleadoId.getText()));            
            statement.setString(2,tfnombreEmpleado.getText());
            statement.setString(3, tfapellidoEmpleado.getText());
            statement.setDouble(4, Double.parseDouble(tfsueldo.getText()));
            statement.setTime(5, Time.valueOf(LocalTime.now()));
            statement.setTime(6, Time.valueOf(LocalTime.now()));
            statement.setInt(7, ((Cargos)cmbcargoId.getSelectionModel().getSelectedItem()).getCargoId());
            statement.setInt(8, ((Empleados)cmbencargadoId.getSelectionModel().getSelectedItem()).getEmpleadoId());
            statement.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
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
    }
    
    /**
     * Initializes the controller class.
     */

    public Main getStage() {
        return Stage;
    }

    public void setStage(Main Stage) {
        this.Stage = Stage;
    }

    public void setOp(int op) {
        this.op = op;
    }
    
}
