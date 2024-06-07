package org.josealfredo.report;

import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.josealfredo.dao.Conexion;

/**
 *
 * @author informatica
 */
public class GenerarReporte {
    private static GenerarReporte instance;

    private static Connection conexion = null;
    
    
    private GenerarReporte() {
    
    }

    public static GenerarReporte getInstance() {
        if(instance == null){
            instance = new GenerarReporte();
        }
        
        return instance;
    }
    
    public void reporteCliente(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            
            Map<String,Object> parametros = new HashMap<>();
            
            InputStream jasperPath = GenerarReporte.class.getResourceAsStream("/org/josealfredo/report/Clientes.jasper");
            JasperPrint reporte = JasperFillManager.fillReport(jasperPath,parametros,conexion);
            
            Stage reportStage = new Stage();
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    
    
}
