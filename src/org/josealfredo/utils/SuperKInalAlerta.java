/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josealfredo.utils;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.josealfredo.model.Usuarios;

/**
 *
 * @author Chepe
 */
public class SuperKInalAlerta {
    
    private static SuperKInalAlerta instance;
    
    private SuperKInalAlerta(){
        
    }
    
    public static SuperKInalAlerta getInstance(){
        if(instance == null){
            instance = new SuperKInalAlerta();
        }
        
        return instance;
    }
    
    public void mostrarAlertaInfo(int code){
        // alerta de campos pendientes
        if(code == 001){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos Pendientes");
            alert.setHeaderText("Campos Pendientes");
            alert.setContentText("Algunos campos necesarios para el registro estas pendientes");
            alert.showAndWait();
        }else if(code == 0002){ // se creo bien (Agregar)
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmacion de registro");
            alert.setHeaderText("Confirmacion de registro");
            alert.setContentText("El registro se ha creado con exito!");
            alert.showAndWait(); 
        }else if(code == 003){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("--- Usuario Incorrecto --- ");
            alert.setHeaderText("===== Usuario Incorrecto =======");
            alert.setContentText("Verifique si el usuario esta escrito correstamente ");
            alert.showAndWait();  
        }else if(code == 004){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("--- Contraseña Incorecta --- ");
            alert.setHeaderText("===== Contraseña Incorecta =======");
            alert.setContentText("Verifique si la Contraseña esta escrito correstamente :0 ");
            alert.showAndWait();  
        }
        
    }
    
    public Optional<ButtonType> mostrarAlertaConfi(int code){
        Optional<ButtonType> action = null;
        if(code == 1){ // alerta de confirmar eliminar reguistros 
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminación de registro");
            alert.setHeaderText("Eliminación  de registro");
            alert.setContentText("Desea confirmar la eliminar el registro?");
            action = alert.showAndWait();
            
        }else if(code == 2){  // alerta de confirmar editar registros 
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Edición de registros");
            alert.setHeaderText("Edición de registros");
            alert.setContentText("¿Desea confirmar la edición del registro?");
            action = alert.showAndWait();
            
            
        }else if(code == 4){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Salir");
            alert.setHeaderText("Salir a Inicio de Sección");
            alert.setContentText("¿Seguro que quiere salir?");
            action = alert.showAndWait();

        }
        
        return action;
        
    }
    
    public void alertaSaludar(String usuario){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("=======) Bienvenido (=========");
        alert.setHeaderText("--------- BIENVENIDO  " + usuario + "  ----------");
        alert.showAndWait();   
        
    }
    
}
