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
import org.josealfredo.controller.FormDetallesComprasController;
import org.josealfredo.controller.FormDistribuidoresController;
import org.josealfredo.controller.FormEmpleadosController;
import org.josealfredo.controller.LoginController;
import org.josealfredo.controller.MenuCargosController;
import org.josealfredo.controller.MenuCategoriaProductosController;
import org.josealfredo.controller.MenuClientesController;
import org.josealfredo.controller.MenuComprasController;
import org.josealfredo.controller.MenuDistribuidoresController;
import org.josealfredo.controller.MenuEmpleadosController;
import org.josealfredo.controller.MenuPrincipalController;
import org.josealfredo.controller.MenuProductosController;
import org.josealfredo.controller.MenuTicketSoporteController;
import org.josealfredo.controller.RegistroUsuariosController;

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
        loginView();
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
    public void menuCargosView(){
        try{
            MenuCargosController menuCargosController = (MenuCargosController)switchScene("MenuCargosView.fxml",950,700);
            menuCargosController.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void menuComprasview(){
        try{
            MenuComprasController menuComprasController = (MenuComprasController)switchScene("MenuComprasView.fxml",1200,750);
            menuComprasController.setStage(this);
        }catch(Exception e){
           System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void menuCategoriaProductosView(){
        try{
            MenuCategoriaProductosController menuCategoriaProductosController = (MenuCategoriaProductosController)switchScene("MenuCategoriaProductosView.fxml",950,700);
            menuCategoriaProductosController.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void formDetallesComprasView(){
        try{
            FormDetallesComprasController formDetallesComprasController = (FormDetallesComprasController)switchScene("FormDetallesComprasView.fxml",1200,750);
            formDetallesComprasController.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void menuDistribuidoresView(){
        try{
            MenuDistribuidoresController menuDistribuidoresController = (MenuDistribuidoresController)switchScene("MenuDistribuidoresView.fxml",1200,750);
            menuDistribuidoresController.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void forDistribuidores(int op){
        try{
            FormDistribuidoresController formDistribuidoresController =(FormDistribuidoresController)switchScene("FormDistribuidoresView.fxml",500,750);
            formDistribuidoresController.setOp(op);
            formDistribuidoresController.setStage(this);
        
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
    
    public void MenuEmpleadosView(){
        try{
            MenuEmpleadosController menuEmpleadosController = (MenuEmpleadosController)switchScene("MenuEmpleadosView.fxml",1200,750);
            menuEmpleadosController.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void formEmpleadosView(int op){
        try{
            FormEmpleadosController formEmpleadosController = (FormEmpleadosController)switchScene("FormEmpleadosView.fxml",600,1200);
            formEmpleadosController.setOp(op);
            formEmpleadosController.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void menuProductosView(){
        try{
            MenuProductosController menuProductosController = (MenuProductosController)switchScene("MenuProductosView.fxml",1200,750);
            menuProductosController.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void loginView(){
        try{
            LoginController loginController = (LoginController)switchScene("LoginView.fxml",500,750);
            loginController.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void registroUsuariosController(){
        try{
            RegistroUsuariosController registroUsuariosController = (RegistroUsuariosController)switchScene("RegistroUsuariosView.fxml",500,750);
            registroUsuariosController.setStage(this);
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
