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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.josealfredo.dao.Conexion;
import org.josealfredo.model.Usuarios;
import org.josealfredo.system.Main;
import org.josealfredo.utils.PasswordUtils;
import org.josealfredo.utils.SuperKInalAlerta;

/**
 * FXML Controller class
 *
 * @author Chepe
 */
public class LoginController implements Initializable {
    private Main stage;
    
    private int op = 0;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;

    @FXML
    TextField tfUser;
    @FXML
    PasswordField tfPassword;
    @FXML
    Button btnIniciar,btnReguistrar;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
    }    
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        
        if(event.getSource() == btnIniciar){
            Usuarios usuario = buscarUsuario();
            if(op == 0){
                if(usuario != null){
                   if(PasswordUtils.getInstance().checkPassword(tfPassword.getText(), usuario.getContrasenia())){
                        SuperKInalAlerta.getInstance().alertaSaludar(usuario.getUsuario());
                        if(usuario.getNivelAccesoId() == 1){
                            btnReguistrar.setDisable(false);
                            btnIniciar.setText("Ir al menu");
                            op = 1;
                        }else if(usuario.getNivelAccesoId() == 2){
                            stage.menuPrincipalView();
                        }   
                    }else{
                SuperKInalAlerta.getInstance().mostrarAlertaInfo(004);
               } 
            }else{
                SuperKInalAlerta.getInstance().mostrarAlertaInfo(003);
            }
            }else{
                stage.menuPrincipalView();
            }
        }else if (event.getSource() == btnReguistrar){
            stage.registroUsuariosController();
        }
    }
    
    public Usuarios buscarUsuario(){
        Usuarios usuarios = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_BuscarUsuarios(?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfUser.getText());
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int usuarioId = resultSet.getInt("usuarioId");
                String user = resultSet.getString("usuario");
                String contrasenia = resultSet.getString("contrasenia");
                int nivelAccesoId = resultSet.getInt("nivelAccesoId");
                int empleadoId = resultSet.getInt("empleadoId");
                
                usuarios = new Usuarios(usuarioId,user,contrasenia,nivelAccesoId,empleadoId);
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
        
        return usuarios;
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    
}
