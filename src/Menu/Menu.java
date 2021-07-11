package Menu;

import Notificacion.Notificacion;
import Notificacion.NotificacionPropiedad;
import Usuario.IniciarSesion;
import Usuario.Usuario;
import Sensor.Observacion;
import Sensor.Sensor;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class Menu {
    public static ArrayList<Sensor> sensores = new ArrayList<>();      
    public static void main(String[] args) {  
        Usuario u = inicioSistema();
        if(u!=null){            
            Observacion.leerObservaciones();              
            JOptionPane.showMessageDialog(null, "BIENVENIDO "+u.getNombreUsuario());            
            JOptionPane.showMessageDialog(null, "ESTA ENROLADO A LA PROPIEDAD "+u.getPropiedadEvaluar());          
            sensores.forEach((s) -> { 
                s.generarObservacion();});      
                String[] opciones = {"PROGRAMAR NOTIFICACIONES","GENERAR NOTIFICACIONES","DESACTIVAR NOTIFICACIONES"};                   
                switch(JOptionPane.showOptionDialog(null, "Elija la opcion que desea", "ventana"
                    ,JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE
                    ,null, opciones, opciones[0])){
                    case 0:
                        if(u.getSensoresIds().size() == 0){
                            JOptionPane.showInternalMessageDialog(null, "TIENE "+u.getSensoresIds().size()+" sensores enrolados");   
                            switch(JOptionPane.showConfirmDialog(null,"DESEA ENROLARSEA ALGUN SENSOR" )){
                                case 0:
                                    u.getSensoresIds().add(JOptionPane.showInputDialog("Escriba el nombre del sensor: ")); 
                                case 1:
                                    break;
                                case 2:
                                    break;
                                    } 
                                }           
                        crearNotificaciones(u);
                        JOptionPane.showMessageDialog(null,"TIENE "+u.getNotificaciones().size()+
                        " NOTIFICACIONES PARA LA PROPIEDAD "+u.getPropiedadEvaluar());                            
                        break;
                    case 1:       
                        crearNotificaciones(u);
                        generarNotificaciones(u,JOptionPane.showInputDialog("INGRESE UN RANGO DE FECHAS"));
                        break;
                    case 2:
                        break;
                        }  
                    }
                }                   
        
    public static Usuario RegistrarUsuario(String nombre, String contraseña,String rangoPeligro,String rangoModerado){    
        File archivo = new File("ListaUsuarios.txt");
        Usuario usuario= new Usuario(nombre,contraseña,rangoPeligro,rangoModerado);               
        if(!archivo.exists()){            
            try{
                archivo.createNewFile();
                FileWriter escribir = new FileWriter(archivo,true);
                PrintWriter line = new PrintWriter(escribir);                                   
                line.println("Usuario:"+usuario.getNombreUsuario()+"-"+"Contraseña:"
                        +usuario.getContraseña()+"-"+String.valueOf(rangoPeligro)+"/"+String.valueOf(rangoModerado));               
                line.close();
                escribir.close();                
            }
            catch(IOException ex){
                ex.printStackTrace();                    
            }  
            return usuario;        
        } 
        else{
            try{                
                FileWriter escribir = new FileWriter(archivo,true);
                PrintWriter line = new PrintWriter(escribir);                                                     
                line.println("Usuario:"+usuario.getNombreUsuario()+"-"+"Contraseña:"
                        +usuario.getContraseña()+"-"+String.valueOf(rangoPeligro)+"/"+String.valueOf(rangoModerado));                      
                line.close();
                escribir.close();                
            }
            catch(IOException ex){
                ex.printStackTrace();                  
            }  
            return usuario;
        } 
    }
    public static Usuario inicioSistema(){
        String[] opciones = {"INICIAR SESION","REGISTRARSE","SALIR"};
        JOptionPane.showMessageDialog(null,"BIENVENIDO A SU SISTEMA DE NOTIFICACIONES");        
        int eleccion = JOptionPane.showOptionDialog(null, "INICIE SESION O REGISTRESE PARA CONTINUAR", "SENSORES"
                    ,JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE
                    ,null, opciones, opciones[0]);
        switch(eleccion){
            case 0:
                boolean iniciar = false;
                Usuario u;                
                while(iniciar!=true){
                String nombre = JOptionPane.showInputDialog("Escriba su nombre de usuario: ");
                String pass = JOptionPane.showInputDialog("Escriba su contraseña: ");                  
                IniciarSesion.leerListaUsuarios();                
                u = IniciarSesion.login(nombre,pass);  
                if(u!=null){
                    iniciar = true;
                    return u;                   
                    }
                }                
                return null;                 
            case 1:    
                u=new Usuario();
                String nombre = JOptionPane.showInputDialog("Escriba el nombre de usuario que desea: ");
                String pass = JOptionPane.showInputDialog("Escriba su contraseña: ");
                JOptionPane.showMessageDialog(null, "SE HA ENROLADO A LA PROPIEDAD "+u.getPropiedadEvaluar());
                u = RegistrarUsuario(nombre,pass,JOptionPane.showInputDialog("ingrese el valor de peligro")
                        ,JOptionPane.showInputDialog("ingrese el valor de moderado"));
                IniciarSesion.leerListaUsuarios();
                u = IniciarSesion.login(nombre,pass);  
                if(u!=null){
                    iniciar = true;
                    return u;                   
                    }                               
                return null;          
            case 2:
                break;
            }
        return null;
        }
    public static void crearNotificaciones(Usuario u){                            
        if(u.getSensoresIds().size() == 0){   
            switch(u.getPropiedadEvaluar()){                
                case "co":                    
                    for(Observacion o:Notificacion.observaciones){
                        if(o.getObservacion().contains(u.getPropiedadEvaluar())){
                        String [] s = o.getObservacion().split(" ");        
                        float valor = Float.parseFloat(s[19]);                  
                            if(valor >  Float.parseFloat(u.getRangoPeligro())){
                            Notificacion n = new NotificacionPropiedad(u.getPropiedadEvaluar(),valor,s[8]); 
                            n.setEtiqueta("PELIGRO");
                            u.getNotificaciones().add(n);                            
                            }                    
                            else if(valor < Float.parseFloat(u.getRangoPeligro()) && valor > Float.parseFloat(u.getRangoModerado())){
                                Notificacion n = new NotificacionPropiedad(u.getPropiedadEvaluar(),valor,s[8]); 
                                n.setEtiqueta("MODERADO");  
                                u.getNotificaciones().add(n);                                                        
                            }
                        }
                    }
                            break;  
                    case "humidity":                        
                        for(Observacion o:Notificacion.observaciones){
                            if(o.getObservacion().contains(u.getPropiedadEvaluar()) ){
                            String [] s = o.getObservacion().split(" ");        
                            float valor = Float.parseFloat(s[19]);    
                            if(valor >  Float.parseFloat(u.getRangoPeligro())){
                                Notificacion n = new NotificacionPropiedad(u.getPropiedadEvaluar(),valor,s[8]); 
                                n.setEtiqueta("PELIGRO");
                                u.getNotificaciones().add(n);                           
                        }                    
                            else if(valor < Float.parseFloat(u.getRangoPeligro()) && valor > Float.parseFloat(u.getRangoModerado())){
                             Notificacion n = new NotificacionPropiedad(u.getPropiedadEvaluar(),valor,s[8]); 
                                n.setEtiqueta("MODERADO"); 
                                u.getNotificaciones().add(n);                       
                            }
                        }
                    }
                    break; 
                case "ligth":   
                    for(Observacion o:Notificacion.observaciones){
                    if(o.getObservacion().contains(u.getPropiedadEvaluar()) ){
                       String [] s = o.getObservacion().split(" ");     
                    boolean value = Boolean.parseBoolean(s[19]);
                    if(value == false){
                        Notificacion n = new NotificacionPropiedad(u.getPropiedadEvaluar(),1.0f,s[8]); 
                        n.setEtiqueta("PELIGRO");
                        u.getNotificaciones().add(n);                        
                    }                    
                    else if(value == true){
                        Notificacion n = new NotificacionPropiedad(u.getPropiedadEvaluar(),0.0f,s[8]); 
                        n.setEtiqueta("MODERADO");    
                        u.getNotificaciones().add(n);                        
                            }
                        }
                    }
                    break; 
                case "lpg":                    
                    for(Observacion o:Notificacion.observaciones){
                        if(o.getObservacion().contains(u.getPropiedadEvaluar()) ){
                         String [] s = o.getObservacion().split(" ");        
                         float valor = Float.parseFloat(s[19]);                     
                        if(valor >  Float.parseFloat(u.getRangoPeligro())){
                            Notificacion n = new NotificacionPropiedad(u.getPropiedadEvaluar(),valor,s[8]); 
                            n.setEtiqueta("PELIGRO");
                            u.getNotificaciones().add(n);
                        }                    
                        else if(valor < Float.parseFloat(u.getRangoPeligro()) && valor > Float.parseFloat(u.getRangoModerado())){
                            Notificacion n = new NotificacionPropiedad(u.getPropiedadEvaluar(),valor,s[8]); 
                            n.setEtiqueta("MODERADO");  
                            u.getNotificaciones().add(n);
                            }
                        }
                    }
                    break; 
                case "motion":
                 for(Observacion o:Notificacion.observaciones){
                    if(o.getObservacion().contains(u.getPropiedadEvaluar()) ){
                       String [] s = o.getObservacion().split(" ");        
                        
                    boolean value = Boolean.parseBoolean(s[19]);
                    if(value == false){
                        Notificacion n = new NotificacionPropiedad(u.getPropiedadEvaluar(),1.0f,s[8]); 
                        n.setEtiqueta("PELIGRO");
                        u.getNotificaciones().add(n);                        
                    }                    
                    else if(value == true){
                        Notificacion n = new NotificacionPropiedad(u.getPropiedadEvaluar(),0.0f,s[8]); 
                        n.setEtiqueta("MODERADO"); 
                        u.getNotificaciones().add(n);                        
                        }
                    }
                 }
                    break; 
                case "smoke":                    
                    for(Observacion o:Notificacion.observaciones){
                        if(o.getObservacion().contains(u.getPropiedadEvaluar()) ){
                        String [] s = o.getObservacion().split(" ");        
                        float valor = Float.parseFloat(s[19]);                     
                        if(valor >  Float.parseFloat(u.getRangoPeligro())){
                            Notificacion n = new NotificacionPropiedad(u.getPropiedadEvaluar(),valor,s[8]); 
                            n.setEtiqueta("PELIGRO");
                            u.getNotificaciones().add(n);

                        }                    
                        else if(valor < Float.parseFloat(u.getRangoPeligro()) && valor > Float.parseFloat(u.getRangoModerado())){
                            Notificacion n = new NotificacionPropiedad(u.getPropiedadEvaluar(),valor,s[8]); 
                            n.setEtiqueta("MODERADO"); 
                            u.getNotificaciones().add(n);
                            }
                        }
                    }
                    break; 
                case "temp":                    
                    for(Observacion o:Notificacion.observaciones){
                        if(o.getObservacion().contains(u.getPropiedadEvaluar()) ){
                        String [] s = o.getObservacion().split(" ");        
                        float valor = Float.parseFloat(s[19]);                     
                        if(valor >  Float.parseFloat(u.getRangoPeligro())){
                            Notificacion n = new NotificacionPropiedad(u.getPropiedadEvaluar(),valor,s[8]); 
                            n.setEtiqueta("PELIGRO");
                            u.getNotificaciones().add(n);
                        }                    
                        else if(valor < Float.parseFloat(u.getRangoPeligro()) && valor > Float.parseFloat(u.getRangoModerado())){
                            Notificacion n = new NotificacionPropiedad(u.getPropiedadEvaluar(),valor,s[8]); 
                            n.setEtiqueta("MODERADO"); 
                            u.getNotificaciones().add(n);
                            }
                        }
                    }
                    break;               
                } 
            }
        else{
            switch(u.getPropiedadEvaluar()){                
                case "co":                    
                    for(Observacion o:Notificacion.observaciones){
                        for(String id: u.getSensoresIds()){
                        if(o.getObservacion().contains(u.getPropiedadEvaluar()) && o.getObservacion().contains(id)){
                        String [] s = o.getObservacion().split(" ");        
                        float valor = Float.parseFloat(s[19]);                  
                            if(valor >  Float.parseFloat(u.getRangoPeligro())){
                            Notificacion n = new NotificacionPropiedad(u.getPropiedadEvaluar(),valor,s[8]); 
                            n.setEtiqueta("PELIGRO");
                            u.getNotificaciones().add(n);                            
                            }                    
                            else if(valor < Float.parseFloat(u.getRangoPeligro()) && valor > Float.parseFloat(u.getRangoModerado())){
                                Notificacion n = new NotificacionPropiedad(u.getPropiedadEvaluar(),valor,s[8]); 
                                n.setEtiqueta("MODERADO");  
                                u.getNotificaciones().add(n);                                                        
                            }
                        }
                    }
                }
                            break;  
                    case "humidity":                        
                        for(Observacion o:Notificacion.observaciones){
                            for(String id: u.getSensoresIds()){
                                if(o.getObservacion().contains(u.getPropiedadEvaluar()) && o.getObservacion().contains(id)){
                                String [] s = o.getObservacion().split(" ");        
                                float valor = Float.parseFloat(s[19]);    
                                if(valor >  Float.parseFloat(u.getRangoPeligro())){
                                    Notificacion n = new NotificacionPropiedad(u.getPropiedadEvaluar(),valor,s[8]); 
                                    n.setEtiqueta("PELIGRO");
                                    u.getNotificaciones().add(n);                           
                                }                    
                                else if(valor < Float.parseFloat(u.getRangoPeligro()) && valor > Float.parseFloat(u.getRangoModerado())){
                                 Notificacion n = new NotificacionPropiedad(u.getPropiedadEvaluar(),valor,s[8]); 
                                    n.setEtiqueta("MODERADO"); 
                                    u.getNotificaciones().add(n);                       
                                }
                            }
                        }
                    }
                    break; 
                case "ligth":   
                    for(Observacion o:Notificacion.observaciones){
                        for(String id: u.getSensoresIds()){
                           if(o.getObservacion().contains(u.getPropiedadEvaluar()) && o.getObservacion().contains(id)){
                           String [] s = o.getObservacion().split(" ");     
                        boolean value = Boolean.parseBoolean(s[19]);
                        if(value == false){
                            Notificacion n = new NotificacionPropiedad(u.getPropiedadEvaluar(),1.0f,s[8]); 
                            n.setEtiqueta("PELIGRO");
                            u.getNotificaciones().add(n);                        
                        }                    
                        else if(value == true){
                            Notificacion n = new NotificacionPropiedad(u.getPropiedadEvaluar(),0.0f,s[8]); 
                            n.setEtiqueta("MODERADO");    
                            u.getNotificaciones().add(n);                        
                                }
                            }
                        }
                    }
                    break; 
                case "lpg":                    
                    for(Observacion o:Notificacion.observaciones){
                        for(String id: u.getSensoresIds()){
                            if(o.getObservacion().contains(u.getPropiedadEvaluar()) && o.getObservacion().contains(id)){
                             String [] s = o.getObservacion().split(" ");        
                             float valor = Float.parseFloat(s[19]);                     
                            if(valor >  Float.parseFloat(u.getRangoPeligro())){
                                Notificacion n = new NotificacionPropiedad(u.getPropiedadEvaluar(),valor,s[8]); 
                                n.setEtiqueta("PELIGRO");
                                u.getNotificaciones().add(n);
                            }                    
                            else if(valor < Float.parseFloat(u.getRangoPeligro()) && valor > Float.parseFloat(u.getRangoModerado())){
                                Notificacion n = new NotificacionPropiedad(u.getPropiedadEvaluar(),valor,s[8]); 
                                n.setEtiqueta("MODERADO");  
                                u.getNotificaciones().add(n);
                                }
                            }
                        }
                    }
                    break; 
                case "motion":
                 for(Observacion o:Notificacion.observaciones){
                    for(String id: u.getSensoresIds()){
                        if(o.getObservacion().contains(u.getPropiedadEvaluar()) && o.getObservacion().contains(id)){
                        String [] s = o.getObservacion().split(" ");                              
                        boolean value = Boolean.parseBoolean(s[19]);
                        if(value == false){
                            Notificacion n = new NotificacionPropiedad(u.getPropiedadEvaluar(),1.0f,s[8]); 
                            n.setEtiqueta("PELIGRO");
                            u.getNotificaciones().add(n);                        
                        }                    
                        else if(value == true){
                            Notificacion n = new NotificacionPropiedad(u.getPropiedadEvaluar(),0.0f,s[8]); 
                            n.setEtiqueta("MODERADO"); 
                            u.getNotificaciones().add(n);                        
                            }
                        }
                    }
                }
                    break; 
                case "smoke":                    
                    for(Observacion o:Notificacion.observaciones){
                        for(String id: u.getSensoresIds()){
                            if(o.getObservacion().contains(u.getPropiedadEvaluar()) && o.getObservacion().contains(id)){
                            String [] s = o.getObservacion().split(" ");        
                            float valor = Float.parseFloat(s[19]);                     
                            if(valor >  Float.parseFloat(u.getRangoPeligro())){
                                Notificacion n = new NotificacionPropiedad(u.getPropiedadEvaluar(),valor,s[8]); 
                                n.setEtiqueta("PELIGRO");
                                u.getNotificaciones().add(n);
                            }                    
                            else if(valor < Float.parseFloat(u.getRangoPeligro()) && valor > Float.parseFloat(u.getRangoModerado())){
                                Notificacion n = new NotificacionPropiedad(u.getPropiedadEvaluar(),valor,s[8]); 
                                n.setEtiqueta("MODERADO"); 
                                u.getNotificaciones().add(n);
                                }
                            }
                        }
                    }
                    break; 
                case "temp":                    
                    for(Observacion o:Notificacion.observaciones){
                        for(String id: u.getSensoresIds()){
                            if(o.getObservacion().contains(u.getPropiedadEvaluar()) && o.getObservacion().contains(id)){
                            String [] s = o.getObservacion().split(" ");        
                            float valor = Float.parseFloat(s[19]);                     
                            if(valor >  Float.parseFloat(u.getRangoPeligro())){
                                Notificacion n = new NotificacionPropiedad(u.getPropiedadEvaluar(),valor,s[8]); 
                                n.setEtiqueta("PELIGRO");
                                u.getNotificaciones().add(n);
                            }                    
                            else if(valor < Float.parseFloat(u.getRangoPeligro()) && valor > Float.parseFloat(u.getRangoModerado())){
                                Notificacion n = new NotificacionPropiedad(u.getPropiedadEvaluar(),valor,s[8]); 
                                n.setEtiqueta("MODERADO"); 
                                u.getNotificaciones().add(n);
                                }
                            }
                        }
                    }
                    break;               
                } 
            }
        }
    public static void desactivarNotificaciones(){
    
    }
    public static boolean programarNotificaciones(){
        
        return false;}
    public static void generarNotificaciones(Usuario u, String rangoFechas){
        String[] rango = rangoFechas.split("-");    
        for(Notificacion n:u.getNotificaciones()){
            if(n.getEtiqueta().equals("PELIGRO") && n.toString().contains(rango[0])&& n.toString().contains(rango[1])){
                File archivo = new File("NOTIFICACIONES"+rangoFechas+".txt");               
            if(!archivo.exists()){            
            try{
                archivo.createNewFile();
                FileWriter escribir = new FileWriter(archivo,true);
                PrintWriter line = new PrintWriter(escribir);                                   
                line.println(n.toString());               
                line.close();
                escribir.close();                
            }
            catch(IOException ex){
                ex.printStackTrace();                    
            }                     
        } 
        else{
            try{                
                FileWriter escribir = new FileWriter(archivo,true);
                PrintWriter line = new PrintWriter(escribir);                                                     
                line.println(n.toString());                      
                line.close();
                escribir.close();                
            }
            catch(IOException ex){
                ex.printStackTrace();                  
                    }  
                }
            }
        }
        for(Notificacion n:u.getNotificaciones()){
            if(n.getEtiqueta().equals("MODERADO") && n.toString().contains(rango[0])&& n.toString().contains(rango[1])){
                File archivo = new File("NOTIFICACIONES"+rangoFechas+".txt");               
            if(!archivo.exists()){            
            try{
                archivo.createNewFile();
                FileWriter escribir = new FileWriter(archivo,true);
                PrintWriter line = new PrintWriter(escribir);                                   
                line.println(n.toString());               
                line.close();
                escribir.close();                
            }
            catch(IOException ex){
                ex.printStackTrace();                    
            }                     
        } 
        else{
            try{                
                FileWriter escribir = new FileWriter(archivo,true);
                PrintWriter line = new PrintWriter(escribir);                                                     
                line.println(n.toString());                      
                line.close();
                escribir.close();                
            }
            catch(IOException ex){
                ex.printStackTrace();                  
                    }  
                }
            }
        }
         JOptionPane.showMessageDialog(null,"Se ha creado el archivo '"+"NOTIFICACIONES"+rangoFechas+".txt'");
    }
}
    

