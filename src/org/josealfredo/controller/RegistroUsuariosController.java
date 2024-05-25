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
import javafx.scene.control.TextField;
import org.josealfredo.dao.Conexion;
import org.josealfredo.model.Empleados;
import org.josealfredo.model.NivelesAcceso;
import org.josealfredo.system.Main;
import org.josealfredo.utils.PasswordUtils;
import org.josealfredo.utils.SuperKInalAlerta;

/**
 * FXML Controller class
 *
 * @author Chepe
 */
public class RegistroUsuariosController implements Initializable {
    private Main Stage;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    @FXML
    TextField tfUser,tfPassword;
    @FXML
    ComboBox cmbEmpleados,cmbNivel;
    @FXML
    Button btnRegistrar,btnRegresar,btnEmpleados;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbEmpleados.setItems(listarEmpleados());
        cmbNivel.setItems(listarNivelesAcceso());
        
    }

    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegistrar){
            
            if(!tfUser.getText().equals("") && !tfPassword.getText().equals("") && !cmbEmpleados.getValue().equals("") && !cmbNivel.getValue().equals("")){
                SuperKInalAlerta.getInstance().mostrarAlertaInfo(002);
                agregarUsuarios();
                Stage.loginView();
            }else{
               SuperKInalAlerta.getInstance().mostrarAlertaInfo(001);
               tfUser.requestFocus();
               return;
            }
          
        }else if(event.getSource() == btnRegresar){
            Stage.loginView();
        }else if (event.getSource() == btnEmpleados){
            Stage.formEmpleadosView(3);
        } 
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
    
    public void agregarUsuarios(){
        try{
           conexion = Conexion.getInstance().obtenerConexion();
           String sql = "call sp_AgregarUsuarios(?,?,?,?)";
           statement = conexion.prepareStatement(sql);
           statement.setString(1, tfUser.getText());
           statement.setString(2, PasswordUtils.getInstance().encrytedPaaword(tfPassword.getText()));
           statement.setInt(3, ((NivelesAcceso)cmbNivel.getSelectionModel().getSelectedItem()).getNivelAccesoId());
           statement.setInt(4, ((Empleados)cmbEmpleados.getSelectionModel().getSelectedItem()).getEmpleadoId());
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
        return Stage;
    }
    
    
    public ObservableList<NivelesAcceso> listarNivelesAcceso(){
        ArrayList<NivelesAcceso> nivelesAcceso = new ArrayList<>();
         
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarNivelesAcceso()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int nivelAccesoId = resultSet.getInt("nivelAccesoId");
                String nivelAcceso = resultSet.getString("nivelAcceso");
                
                nivelesAcceso.add(new NivelesAcceso(nivelAccesoId,nivelAcceso));
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
        
        return FXCollections.observableList(nivelesAcceso);
    }

    public void setStage(Main Stage) {
        this.Stage = Stage;
    }
    
    
}
