/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josealfredo.dto;

import org.josealfredo.model.Distribuidores;


/**
 *
 * @author Chepe
 */
public class DistribuidoresDTO {
    
    private static DistribuidoresDTO instance;
    private Distribuidores distribuidores;

    public DistribuidoresDTO() {
        
    }
    
    public static DistribuidoresDTO getDistribuidoresDTO(){
       if(instance == null){
           instance = new DistribuidoresDTO();
       } 
       
        return instance;
    }    
    

    public  Distribuidores getDistribuidores(){
        return distribuidores;
    }
    
    public void setDistribuidores(Distribuidores distribuidores){
        this.distribuidores = distribuidores;
    }
}
