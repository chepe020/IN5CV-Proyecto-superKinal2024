/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josealfredo.dto;
import org.josealfredo.model.Empleados;
/**
 *
 * @author Chepe
 */
public class EmpleadosDTO {
    private static EmpleadosDTO instance;
    private Empleados empleados;
    
    private EmpleadosDTO(){
    
    }
    
    public static EmpleadosDTO getEmpleadosDTO(){
        if(instance == null){
            instance = new EmpleadosDTO();
        }
        
        return instance;
    }

    public Empleados getEmpleados() {
        return empleados;
    }

    public void setEmpleados(Empleados empleados) {
        this.empleados = empleados;
    }
    
    
}
