/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josealfredo.model;

/**
 *
 * @author Chepe
 */
public class NivelesAcceso {
    private int nivelAccesoId;
    private String nivelAcceso;

    public NivelesAcceso() {
    }

    public NivelesAcceso(int nivelAccesoId, String nivelAcceso) {
        this.nivelAccesoId = nivelAccesoId;
        this.nivelAcceso = nivelAcceso;
    }

    public int getNivelAccesoId() {
        return nivelAccesoId;
    }

    public void setNivelAccesoId(int nivelAccesoId) {
        this.nivelAccesoId = nivelAccesoId;
    }

    public String getNivelAcceso() {
        return nivelAcceso;
    }

    public void setNivelAcceso(String nivelAcceso) {
        this.nivelAcceso = nivelAcceso;
    }

    @Override
    public String toString() {
        return "Id: " + nivelAccesoId + " | " + nivelAcceso;
    }
    
    
}
