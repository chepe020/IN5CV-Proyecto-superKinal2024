 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josealfredo.system;

import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.josealfredo.controller.FormClienteController;
import org.josealfredo.controller.MenuClientesController;
import org.josealfredo.controller.MenuPrincipalController;
import org.josealfredo.controller.MenuTicketSoporteController;

/**
 *
 * @author Alfredo
 */
public class Main extends Application {
    private Stage stage;
    private Scene scene;
    private final String URLVIEW = "/org/josealfredo/view/" ;
    
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setTitle("----- Super Kinal APP ----");
        menuPrincipalView();
        stage.show();
    }
    
    public Initializable switchScene(String fxmlName,int width, int height)throws Exception{
        Initializable resultado = null;
        FXMLLoader loader  = new FXMLLoader();
        
        InputStream file = Main.class.getResourceAsStream(URLVIEW + fxmlName);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(URLVIEW + fxmlName));
       
        
        
        scene = new Scene((AnchorPane)loader.load(file),width,height);
        stage.setScene(scene);
        stage.sizeToScene();
        
        resultado = (Initializable)loader.getController();
        return resultado;
    }
    
    public void menuPrincipalView(){
        try{
            MenuPrincipalController menuPrincipalView = (MenuPrincipalController)switchScene("MenuPrincipalView.fxml",950,700);
            menuPrincipalView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void menuClientesView(){
        try{
            MenuClientesController menuClientesView = (MenuClientesController)switchScene("MenuClientesView.fxml",1200,750);
            menuClientesView.setStage(this);
        }catch(Exception e){
           System.out.println(e.getMessage());
           e.printStackTrace();
        }
    }
    public void formClientesView(int op){
        try{
           FormClienteController FormClientesView = (FormClienteController)switchScene("FormClientesView.fxml",500,750);
           FormClientesView.setOp(op);
           FormClientesView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void menuTicketSoporteView(){
        try{
           MenuTicketSoporteController  menuTicketSoporteController = (MenuTicketSoporteController)switchScene("MenuTicketSoporteView.fxml",1200,750);
            menuTicketSoporteController.setStage(this);
            
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }    
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
