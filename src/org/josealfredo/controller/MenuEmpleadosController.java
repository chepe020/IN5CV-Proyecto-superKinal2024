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
import java.time.LocalTime;
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
import org.josealfredo.dto.EmpleadosDTO;
import org.josealfredo.model.Cargos;
import org.josealfredo.model.Empleados;
import org.josealfredo.system.Main;

/**
 * FXML Controller class
 *
 * @author Chepe
 */
public class MenuEmpleadosController implements Initializable {
    private Main Stage;
    
    @FXML
    TableView tblEmpleados;
    @FXML
    Button btnBuscar,btnAgregar,btnEliminar,btnEditar,btnRegresar;
    @FXML
    TableColumn colEmpleadoId,colNombreEmpleado,colApellidoEmpleado,colSuelo,colHoraEntrada,colHoraSalida,colCargoId,colEncargadoId;
            
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargaLista();
        // TODO
    }    
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar){
            Stage.menuPrincipalView();
        }if(event.getSource() == btnAgregar){
            Stage.formEmpleadosView(1);
        }if(event.getSource() == btnEditar){
            EmpleadosDTO.getEmpleadosDTO().setEmpleados((Empleados)tblEmpleados.getSelectionModel().getSelectedItem());
            Stage.formEmpleadosView(2);
        }if(event.getSource() == btnEliminar){
            int empId = ((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getEmpleadoId();
            eliminarEmpleados(empId);
            cargaLista();
        }
    }
     
    public void cargaLista(){
        tblEmpleados.setItems(listarEmpleados());
        colEmpleadoId.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("empleadoId"));
        colNombreEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, String>("nombreEmpleado"));
        colApellidoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, String>("apellidoEmpleado"));
        colSuelo.setCellValueFactory(new PropertyValueFactory<Empleados, Date>("sueldo"));
        colHoraEntrada.setCellValueFactory(new PropertyValueFactory<Empleados, String>("horaEntrada"));
        colHoraSalida.setCellValueFactory(new PropertyValueFactory<Empleados, String>("horaSalida"));
        colCargoId.setCellValueFactory(new PropertyValueFactory<Empleados, String>("cargo"));
        colEncargadoId.setCellValueFactory(new PropertyValueFactory<Empleados, String>("encargado"));
        tblEmpleados.getSortOrder().add(colEmpleadoId);
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
    
    public void eliminarEmpleados(int empId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_EliminarEmpleados(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, empId);
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
             
    public Main getStage() {
        return Stage;
    }

    public void setStage(Main Stage) {
        this.Stage = Stage;
    }
    
    
}
