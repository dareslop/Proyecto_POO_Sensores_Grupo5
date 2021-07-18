/**
 @authon Emanuel Guerrero
 @author Diego Vega
 @author Darien Lopez
 **/
package Menu;

import Notificacion.Notificacion;
import Notificacion.NotificacionPropiedad;
import Notificacion.NotificacionSensor;
import Usuario.IniciarSesion;
import Usuario.Usuario;
import Sensor.Observacion;
import Sensor.Sensor;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


 public class Menu {
    public static ArrayList<Sensor> sensores = new ArrayList<>(); 
    public static ArrayList<String> sensoresIds = new ArrayList<>();    
    public static void main(String[] args) {        
        system();
    }         
    public static Usuario RegistrarUsuario(){                       
        File archivo = new File("ListaUsuarios.txt");
        String[] propiedades = {"co","humidity","ligth","lpg","motion","smoke","temp"};
        int eleccion = JOptionPane.showOptionDialog(null, "ESCOJA SU PROPIEDAD INCIAL", "REGISTRO USUARIO"
                    ,JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE
                    ,null, propiedades, propiedades[0]);
        String rangoPeligro;
        String rangoModerado;        
        JOptionPane.showMessageDialog(null, "SE HA ENROLADO A LA PROPIEDAD "+propiedades[eleccion]);        
        String nombre = JOptionPane.showInputDialog("Escriba el nombre de usuario que desea: ");
        String contraseña = JOptionPane.showInputDialog("Escriba su contraseña: "); 
        if(propiedades[eleccion].equals("light")||propiedades[eleccion].equals("motion")){
            rangoPeligro =JOptionPane.showInputDialog("ingrese el valor de peligro('0.0')");
            rangoModerado =JOptionPane.showInputDialog("ingrese el valor de moderado('1.0')");
        }
        else{
        rangoPeligro =JOptionPane.showInputDialog("ingrese el valor de peligro");
        rangoModerado =JOptionPane.showInputDialog("ingrese el valor de moderado");
        }
        Usuario usuario = new Usuario(nombre,contraseña,propiedades[eleccion],rangoPeligro,rangoModerado);
        IniciarSesion.leerListaUsuarios();
        ArrayList<Usuario> usuarios = IniciarSesion.listaUsuarios;         
        if(!archivo.exists()){              
            try{
                archivo.createNewFile();
                FileWriter escribir = new FileWriter(archivo,true);
                PrintWriter line = new PrintWriter(escribir); 
                for(Usuario user:usuarios){                    
                    if(usuario.equals(user)){
                        JOptionPane.showMessageDialog(null, "USUARIO YA REGISTRADO");
                    return null;
                    }
                }
                line.println(usuario.toString());               
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
                for(Usuario user:usuarios){                    
                    if(usuario.equals(user)){
                        JOptionPane.showMessageDialog(null, "USUARIO YA REGISTRADO");
                    return null;
                    }
                }
                line.println(usuario.toString());               
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
        int eleccion = JOptionPane.showOptionDialog(null, "INICIE SESION O REGISTRESE PARA CONTINUAR", "BIENVENIDO"
                    ,JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE
                    ,null, opciones, opciones[0]);
        switch(eleccion){
            case 0:
                boolean iniciar = false;
                Usuario u;                
                while(!iniciar){
                String nombre = JOptionPane.showInputDialog("Escriba su nombre de usuario: ");
                String pass = JOptionPane.showInputDialog("Escriba su contraseña: ");                  
                if(IniciarSesion.leerListaUsuarios()==false){
                    JOptionPane.showMessageDialog(null,"USUARIO NO EXISTE, REGISTRESE PARA INCIAR SESION");
                    return null;
                }                
                u = IniciarSesion.login(nombre,pass);  
                if(u!=null){
                    iniciar = true;
                    return u;                   
                    }
                }                
                return null;                 
            case 1:
                u = null;                                                
                u = RegistrarUsuario();
                try{
                if(u != null){
                    JOptionPane.showMessageDialog(null, "REINCIE LA APLICACION PARA INICIAR SESION.");
                    break;
                    } 
                }
                catch(NullPointerException ex){}
                JOptionPane.showMessageDialog(null, "REINCIE LA APLICACION E INTENTE DE NUEVO");
                return null;          
            case 2:
                break;
            }
        return null;
        }
    public static void system(){
        Usuario u = inicioSistema();
            if(u!=null){            
                Observacion.leerObservaciones();              
                JOptionPane.showMessageDialog(null, "BIENVENIDO "+u.getNombreUsuario());  
                Object[] p = u.getPropiedades().keySet().toArray();
                for(int i=0; i<u.getPropiedades().keySet().toArray().length;i++){
                    JOptionPane.showMessageDialog(null, "ESTA ENROLADO A LA PROPIEDAD\n"+(i+1)+"."+" "+p[i]);
                }
                if(u.getPropiedades().keySet().size()<7){
                    switch(JOptionPane.showConfirmDialog(null,"DESEA AGREGAR OTRA PROPIEDAD?" )){
                        case -1:
                            break;
                        case 0:
                           Menu.agregarPropiedad(u);
                           break;
                        case 1:
                            break;
                            }
                        }
                    sensores.forEach((s) -> { 
                    s.generarObservacion();}); 
                    sensores.forEach((Sensor s)->{
                        if(!sensoresIds.contains(s.getId())){
                    sensoresIds.add(s.getId());}
                    });
                    JOptionPane.showInternalMessageDialog(null, "ESTA ENROLADO A LOS SIGIENTES SENSORES:\n"+u.getSensoresIds());
                    int eleccion = 1;
                    if(u.getSensoresIds().size()==0 ){
                        eleccion= JOptionPane.showConfirmDialog(null,"DESEA ENROLARSEA A UN SENSOR?");   
                    }
                    else if(u.getSensoresIds().size()==Menu.sensoresIds.size() ){
                        eleccion = -1;
                        }
                    else if(u.getSensoresIds().size()<Menu.sensoresIds.size() && u.getSensoresIds().size()>0 ){
                        eleccion = JOptionPane.showConfirmDialog(null,"DESEA ENROLARSEA A OTRO SENSOR?");
                    }
                    switch(eleccion){
                        case -1:
                            break;
                        case 0:
                            eleccion = JOptionPane.showOptionDialog(null, "ELIJA SENSOR QUE DESEA", "ENROLAR SENSOR"
                                    ,JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE
                                    ,null, Menu.sensoresIds.toArray(), Menu.sensoresIds.toArray()[0]);
                            String idSensor = String.valueOf(Menu.sensoresIds.toArray()[eleccion]);
                            u.getSensoresIds().add(idSensor);                                    
                            Menu.enrolarSensor();                                    
                            break;
                        case 1:
                            break;
                        case 2:
                            break;
                    }
                    String[] opciones = {"PROGRAMAR NOTIFICACIONES","GENERAR NOTIFICACIONES","DESACTIVAR NOTIFICACIONES"};                   
                    switch(JOptionPane.showOptionDialog(null, "ESCOJA LA OPERACION A REALIZAR", "SISTEMA NOTIFICACIONES"
                        ,JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE
                        ,null, opciones, opciones[0])){
                        case -1:
                            break;
                        case 0:                           
                             
                            crearNotificaciones(u);                                      
                            eleccion = JOptionPane.showOptionDialog(null, "ELIJA LA PROPIEDAD QUE DESEA PARA LA NOTIFICACION", "CREAR NOTIFICACION"
                                ,JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE
                                ,null, u.getPropiedades().keySet().toArray(), u.getPropiedades().keySet().toArray()[0]);                            
                                Menu.programarNotificaciones(u,String.valueOf(u.getPropiedades().keySet().toArray()[eleccion]));                  
                            break;
                        case 1:       
                            crearNotificaciones(u);
                            eleccion = JOptionPane.showOptionDialog(null, "ELIJA QUE OPCION QUE DESEA", "PROPIEDADES"
                                ,JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE
                                ,null, u.getPropiedades().keySet().toArray(), u.getPropiedades().keySet().toArray()[0]);
                            boolean i = false;
                            String rangoFechas = JOptionPane.showInputDialog("INGRESE UN RANGO DE FECHAS(dd-mm-año/dd-mm-año)");
                            while(i!=true){
                            try{    
                                i = generarNotificaciones(u,rangoFechas,
                                    String.valueOf(u.getPropiedades().keySet().toArray()[eleccion]));}
                            catch(ArrayIndexOutOfBoundsException ex){
                                i=false;
                                JOptionPane.showMessageDialog(null,"VUELVA A INGRESAR LA FECHA"); 
                                rangoFechas = JOptionPane.showInputDialog("INGRESE UN RANGO DE FECHAS(dd-mm-año/dd-mm-año)");
                                }                                                        
                            }
                            JOptionPane.showMessageDialog(null,"SE CREARON LAS NOTIFICACIONES EN LA FECHA ESTABLECIDA");
                            break;
                        case 2:
                            if(u.getPropiedades().size()>1){
                                ArrayList<String> notificacionesUsuario = new ArrayList<String>();
                                String[] fileRuta;        
                                fileRuta = new File("proyecto sensores").getAbsolutePath().split("\\\\");
                                String dic = "C:\\\\";
                                for(String s:fileRuta){
                                    if(!dic.contains(s.substring(0, s.length()-2))){
                                        if(s.equals(fileRuta[fileRuta.length-1])){
                                        dic+=s;
                                    }
                                        else{
                                    dic+=s+"\\\\";}
                                    }
                                }
                                File ruta = new File(dic);
                                String[] files = ruta.list();
                                for(String f:files){
                                    if((f.contains("NOTIFICACION") && f.contains(u.getNombreUsuario())) || f.contains("NOTIFICACION") && f.contains(u.getNombreUsuario().toUpperCase())){
                                        notificacionesUsuario.add(f.substring(0, f.length()-4));
                                    }
                                }
                                eleccion = JOptionPane.showOptionDialog(null, "ELIJA LA NOTIFICACION A DESACTIVAR", "DESACTIVAR NOTIFICACIONES"
                                    ,JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE
                                    ,null, notificacionesUsuario.toArray(), notificacionesUsuario.toArray()[0]);                      
                                Menu.desactivarNotificaciones(String.valueOf(notificacionesUsuario.toArray()[eleccion]),u);
                            break;
                            }
                            JOptionPane.showMessageDialog(null,"NO SE PUEDE DESACTIVAR LA NOTIFICACION PORQUE SOLO TIENE UNA PROPIEDAD ENROLADA ");
                            break;                          
                        }            
                    }
                }   
    public static void crearNotificaciones(Usuario u){ 
        u.getPropiedades().forEach((p,values)->{
        String[] rangoValores = values.split("/");    
        if(u.getSensoresIds().size() == 0){   
            switch(p){                
                case "co":                    
                    for(Observacion o:Notificacion.observaciones){
                        if(o.getObservacion().contains(p)){
                        String [] s = o.getObservacion().split(" ");        
                        float valor = Float.parseFloat(s[19]);                  
                            if(valor >  Float.parseFloat(rangoValores[0])){
                            Notificacion n = new NotificacionPropiedad(p,valor,s[9]); 
                            n.setEtiqueta("PELIGRO");
                            u.getNotificaciones().add(n);                            
                            }                    
                            else if(valor < Float.parseFloat(rangoValores[0]) && valor > Float.parseFloat(rangoValores[1])){
                                Notificacion n = new NotificacionPropiedad(p,valor,s[9]); 
                                n.setEtiqueta("MODERADO");  
                                u.getNotificaciones().add(n);                                                        
                            }
                        }
                    }
                            break;  
                    case "humidity":                        
                        for(Observacion o:Notificacion.observaciones){
                            if(o.getObservacion().contains(p)){
                            String [] s = o.getObservacion().split(" ");        
                            float valor = Float.parseFloat(s[19]);    
                            if(valor >  Float.parseFloat(rangoValores[0])){
                                Notificacion n = new NotificacionPropiedad(p,valor,s[9]); 
                                n.setEtiqueta("PELIGRO");
                                u.getNotificaciones().add(n);                           
                        }                    
                            else if(valor < Float.parseFloat(rangoValores[0]) && valor > Float.parseFloat(rangoValores[1])){
                             Notificacion n = new NotificacionPropiedad(p,valor,s[9]); 
                                n.setEtiqueta("MODERADO"); 
                                u.getNotificaciones().add(n);                       
                            }
                        }
                    }
                    break; 
                case "ligth":   
                    for(Observacion o:Notificacion.observaciones){
                    if(o.getObservacion().contains(p)){
                       String [] s = o.getObservacion().split(" ");     
                    boolean value = Boolean.parseBoolean(s[19]);
                    if(value == false){
                        Notificacion n = new NotificacionPropiedad(p,0.0f,s[9]); 
                        n.setEtiqueta("PELIGRO");
                        u.getNotificaciones().add(n);                        
                    }                    
                    else if(value == true){
                        Notificacion n = new NotificacionPropiedad(p,1.0f,s[9]); 
                        n.setEtiqueta("MODERADO");    
                        u.getNotificaciones().add(n);                        
                            }
                        }
                    }
                    break; 
                case "lpg":                    
                    for(Observacion o:Notificacion.observaciones){
                        if(o.getObservacion().contains(p) ){
                         String [] s = o.getObservacion().split(" ");        
                         float valor = Float.parseFloat(s[19]);                            
                        if(valor >  Float.parseFloat(rangoValores[0])){
                            Notificacion n = new NotificacionPropiedad(p,valor,s[9]); 
                            n.setEtiqueta("PELIGRO");
                            u.getNotificaciones().add(n);
                        }                    
                        else if(valor < Float.parseFloat(rangoValores[0]) && valor > Float.parseFloat(rangoValores[1])){
                            Notificacion n = new NotificacionPropiedad(p,valor,s[9]); 
                            n.setEtiqueta("MODERADO");  
                            u.getNotificaciones().add(n);
                            }
                        }
                    }
                    break; 
                case "motion":
                 for(Observacion o:Notificacion.observaciones){
                    if(o.getObservacion().contains(p) ){
                       String [] s = o.getObservacion().split(" ");                        
                        boolean value = Boolean.parseBoolean(s[19]);
                        if(value == false){
                            Notificacion n = new NotificacionPropiedad(p,0.0f,s[9]); 
                            n.setEtiqueta("PELIGRO");
                            u.getNotificaciones().add(n);                        
                        }                    
                        else if(value == true){
                            Notificacion n = new NotificacionPropiedad(p,1.0f,s[9]); 
                            n.setEtiqueta("MODERADO"); 
                            u.getNotificaciones().add(n);                        
                        }
                    }
                 }
                    break; 
                case "smoke":                    
                    for(Observacion o:Notificacion.observaciones){
                        if(o.getObservacion().contains(p) ){
                        String [] s = o.getObservacion().split(" ");        
                        float valor = Float.parseFloat(s[19]);                     
                        if(valor >  Float.parseFloat(rangoValores[0])){
                            Notificacion n = new NotificacionPropiedad(p,valor,s[9]); 
                            n.setEtiqueta("PELIGRO");
                            u.getNotificaciones().add(n);

                        }                    
                        else if(valor < Float.parseFloat(rangoValores[0]) && valor > Float.parseFloat(rangoValores[1])){
                            Notificacion n = new NotificacionPropiedad(p,valor,s[9]); 
                            n.setEtiqueta("MODERADO"); 
                            u.getNotificaciones().add(n);
                            }
                        }
                    }
                    break; 
                case "temp":                    
                    for(Observacion o:Notificacion.observaciones){
                        if(o.getObservacion().contains(p) ){
                        String [] s = o.getObservacion().split(" ");        
                        float valor = Float.parseFloat(s[19]);                     
                        if(valor >  Float.parseFloat(rangoValores[0])){
                            Notificacion n = new NotificacionPropiedad(p,valor,s[9]); 
                            n.setEtiqueta("PELIGRO");
                            u.getNotificaciones().add(n);
                        }                    
                        else if(valor < Float.parseFloat(rangoValores[0]) && valor > Float.parseFloat(rangoValores[1])){
                            Notificacion n = new NotificacionPropiedad(p,valor,s[9]); 
                            n.setEtiqueta("MODERADO"); 
                            u.getNotificaciones().add(n);
                            }
                        }
                    }
                    break;               
                } 
            }
        else{
            switch(p){                
                case "co":                    
                    for(Observacion o:Notificacion.observaciones){
                        for(String id: u.getSensoresIds()){
                        if(o.getObservacion().contains(p) && o.getObservacion().contains(id)){
                        String [] s = o.getObservacion().split(" ");        
                        float valor = Float.parseFloat(s[19]);                  
                            if(valor >  Float.parseFloat(rangoValores[0])){
                                for(String idSensor:u.getSensoresIds()){
                                    Notificacion n = new NotificacionSensor((p),idSensor,valor,s[9]); 
                                    n.setEtiqueta("PELIGRO");
                                    u.getNotificaciones().add(n);                            
                                }  
                            }                  
                            else if(valor < Float.parseFloat(rangoValores[0]) && valor > Float.parseFloat(rangoValores[1])){
                                for(String idSensor:u.getSensoresIds()){
                                    Notificacion n = new NotificacionSensor(p,idSensor,valor,s[9]); 
                                    n.setEtiqueta("MODERADO");  
                                    u.getNotificaciones().add(n); 
                                }                                                       
                            }
                        }
                    }
                }
                            break;  
                    case "humidity":                        
                        for(Observacion o:Notificacion.observaciones){
                            for(String id: u.getSensoresIds()){
                                if(o.getObservacion().contains(p) && o.getObservacion().contains(id)){
                                String [] s = o.getObservacion().split(" ");        
                                float valor = Float.parseFloat(s[19]);    
                                if(valor >  Float.parseFloat(rangoValores[0])){
                                    Notificacion n = new NotificacionPropiedad(p,valor,s[9]); 
                                    n.setEtiqueta("PELIGRO");
                                    u.getNotificaciones().add(n);                           
                                }                    
                                else if(valor < Float.parseFloat(rangoValores[0]) && valor > Float.parseFloat(rangoValores[1])){
                                 for(String idSensor:u.getSensoresIds()){
                                    Notificacion n = new NotificacionSensor(p,idSensor,valor,s[9]); 
                                    n.setEtiqueta("MODERADO"); 
                                    u.getNotificaciones().add(n);    
                                    }                 
                                }
                            }
                        }
                    }
                    break; 
                case "ligth":   
                    for(Observacion o:Notificacion.observaciones){
                        for(String id: u.getSensoresIds()){
                           if(o.getObservacion().contains(p) && o.getObservacion().contains(id)){
                           String [] s = o.getObservacion().split(" ");     
                        boolean value = Boolean.parseBoolean(s[19]);
                        if(value == false){
                            for(String idSensor:u.getSensoresIds()){
                            Notificacion n = new NotificacionSensor(p,idSensor,0.0f,s[9]); 
                            n.setEtiqueta("PELIGRO");
                            u.getNotificaciones().add(n);  
                            }                      
                        }                    
                        else if(value == true){
                           for(String idSensor:u.getSensoresIds()){                               
                                Notificacion n = new NotificacionSensor(p,idSensor, 1.0f,s[9]); 
                                n.setEtiqueta("MODERADO");    
                                u.getNotificaciones().add(n); }                       
                                }
                            }
                        }
                    }
                    break; 
                case "lpg":                    
                    for(Observacion o:Notificacion.observaciones){
                        for(String id: u.getSensoresIds()){
                            if(o.getObservacion().contains(p) && o.getObservacion().contains(id)){
                             String [] s = o.getObservacion().split(" ");        
                             float valor = Float.parseFloat(s[19]);                     
                            if(valor >  Float.parseFloat(rangoValores[0])){
                                for(String idSensor:u.getSensoresIds()){                               
                                    Notificacion n = new NotificacionSensor(p,idSensor, valor,s[9]); 
                                    n.setEtiqueta("MODERADO");    
                                    u.getNotificaciones().add(n); 
                                }
                            }                    
                            else if(valor < Float.parseFloat(rangoValores[0]) && valor > Float.parseFloat(rangoValores[1])){
                               for(String idSensor:u.getSensoresIds()){
                                Notificacion n = new NotificacionSensor(p,idSensor,valor,s[9]); 
                                n.setEtiqueta("MODERADO");  
                                u.getNotificaciones().add(n);}
                                } 
                            }
                        }
                    }
                    break; 
                case "motion":
                 for(Observacion o:Notificacion.observaciones){
                    for(String id: u.getSensoresIds()){
                        if(o.getObservacion().contains(p) && o.getObservacion().contains(id)){
                        String [] s = o.getObservacion().split(" ");                              
                        boolean value = Boolean.parseBoolean(s[19]);
                        if(value == false){
                            for(String idSensor:u.getSensoresIds()){                               
                                Notificacion n = new NotificacionSensor(p,idSensor, 0.0f,s[9]); 
                                n.setEtiqueta("MODERADO");    
                                u.getNotificaciones().add(n); 
                            }                        
                        }                    
                        else if(value == true){
                           for(String idSensor:u.getSensoresIds()){
                            Notificacion n = new NotificacionSensor(p,idSensor,1.0f,s[9]); 
                            n.setEtiqueta("MODERADO"); 
                            u.getNotificaciones().add(n);   }                     
                            }
                        }
                    }
                }
                    break; 
                case "smoke":                    
                    for(Observacion o:Notificacion.observaciones){
                        for(String id: u.getSensoresIds()){
                            if(o.getObservacion().contains(p) && o.getObservacion().contains(id)){
                            String [] s = o.getObservacion().split(" ");        
                            float valor = Float.parseFloat(s[19]);                     
                            if(valor >  Float.parseFloat(rangoValores[0])){
                                Notificacion n = new NotificacionPropiedad(p,valor,s[9]); 
                                n.setEtiqueta("PELIGRO");
                                u.getNotificaciones().add(n);
                            }                    
                            else if(valor < Float.parseFloat(rangoValores[0]) && valor > Float.parseFloat(rangoValores[1])){
                               for(String idSensor:u.getSensoresIds()){
                                Notificacion n = new NotificacionSensor(p,idSensor,valor,s[9]); 
                                n.setEtiqueta("MODERADO"); 
                                u.getNotificaciones().add(n);}
                                }
                            }
                        }
                    }
                    break; 
                case "temp":                    
                    for(Observacion o:Notificacion.observaciones){
                        for(String id: u.getSensoresIds()){
                            if(o.getObservacion().contains(p) && o.getObservacion().contains(id)){
                            String [] s = o.getObservacion().split(" ");        
                            float valor = Float.parseFloat(s[19]);                     
                            if(valor >  Float.parseFloat(rangoValores[0])){
                                for(String idSensor:u.getSensoresIds()){                               
                                Notificacion n = new NotificacionSensor(p,idSensor, valor,s[9]); 
                                n.setEtiqueta("MODERADO");    
                                u.getNotificaciones().add(n); 
                                }
                            }                    
                            else if(valor < Float.parseFloat(rangoValores[0]) && valor > Float.parseFloat(rangoValores[1])){
                               for(String idSensor:u.getSensoresIds()){
                                Notificacion n = new NotificacionSensor(p,idSensor,valor,s[9]); 
                                n.setEtiqueta("MODERADO"); 
                                u.getNotificaciones().add(n);}
                                }
                            }
                        }
                    }
                    break;               
                } 
            }
       });
    }
    public static void desactivarNotificaciones(String nombreArchivo,Usuario u){        
        File f = new File(nombreArchivo+".txt");        
        for(String s:nombreArchivo.split(" ")){
            if(u.getPropiedades().containsKey(s)){
                u.getPropiedades().remove(s);
                JOptionPane.showMessageDialog(null,"SE HA DESVINCULADO A LA PROPIEDAD "+s);
            }            
        }
        f.delete();        
        switch(JOptionPane.showInternalConfirmDialog(null, "DESEA DESENROLARSE A UN SENSOR?")){
            case -1:
                break;
            case 0:
                int eleccion = JOptionPane.showOptionDialog(null, "ELIJA EL SENSOR A DESENROLAR", "DESENROLAR SENSOR"
                        ,JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE
                        ,null, u.getSensoresIds().toArray(), u.getSensoresIds().toArray()[0]);
                JOptionPane.showMessageDialog(null,"SE HA DESVINCULADO AL SENSOR "+u.getSensoresIds().toArray()[eleccion]);                
                u.getSensoresIds().remove(eleccion);                
                break;
            case 1:
                break;
        }
        File archivo = new File("ListaUsuarios.txt");
        try{
        archivo.delete();
        archivo.createNewFile();
        FileWriter escribir = new FileWriter(archivo,true);
        PrintWriter line = new PrintWriter(escribir,true);
        for(Usuario user:IniciarSesion.listaUsuarios){
            line.println(user.toString());
                }
        line.close();
        escribir.close();
            } 
        catch (IOException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        JOptionPane.showMessageDialog(null, "SE HA ELIMINADO "+nombreArchivo);       
    }
    public static void programarNotificaciones(Usuario u,String propiedad){
        File archivo=null;
        if(u.getPropiedades().keySet().contains(propiedad)){
            archivo = new File("NOTIFICACIONES "+u.getNombreUsuario()+" "+propiedad+".txt"); 
            }
            try{                
                archivo.createNewFile();
                FileWriter escribir = new FileWriter(archivo,true);
                PrintWriter line = new PrintWriter(escribir); 
                for(Notificacion n:u.getNotificaciones()){
                    if(n.toString().contains(propiedad))
                    line.println(n.toString());               
                }  
                line.close();
                escribir.close();
                JOptionPane.showMessageDialog(null, "Se han creado las notificaciones.");
            }
            catch(IOException ex){
                System.err.println("NO SE ENCUANTRA EL ARCHIVO");                    
            } 
        }             
    public static boolean generarNotificaciones(Usuario u, String rangoFechas,String propiedad){
        String[] rango = rangoFechas.split("/");  //dd/m/año-dd/m/año
        File archivo = new File("NOTIFICACIONES"+" "+u.getNombreUsuario().toUpperCase()+" "+propiedad+" "+rango[0]+"-"+rango[1]+".txt");        
                       
            try{
                archivo.createNewFile();
                FileWriter escribir = new FileWriter(archivo,true);
                PrintWriter line = new PrintWriter(escribir);                      
                for(Notificacion n:u.getNotificaciones()){ 
                    //System.out.println(n.validarFecha(rangoFechas));
                    if(n.getEtiqueta().equals("PELIGRO") && n.validarFecha(rangoFechas) && n.toString().contains(propiedad)){                        
                       line.println(n.toString());
                        }
                    }     
                 for(Notificacion n:u.getNotificaciones()){
                     //System.out.println(n.validarFecha(rangoFechas));
                     if(n.getEtiqueta().equals("MODERADO") && n.validarFecha(rangoFechas) && n.toString().contains(propiedad)){
                       line.println(n.toString());
                    }
                }
                line.close();
                escribir.close();
                return true;
                    
                } 
            catch (IOException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }  
            return false;
        }
    public static void enrolarSensor(){
        //usuario:****-contraseña:****-propiedad:**-rangovalores-sensor1;sensor2.....
        File archivo = new File("ListaUsuarios.txt");     
            try{   
                archivo.delete();
                archivo.createNewFile();
                FileWriter escribir = new FileWriter(archivo);            
                PrintWriter line = new PrintWriter(escribir);                                
                    for(Usuario user:IniciarSesion.listaUsuarios){                        
                        line.println(user);                    
                        } 
                    line.close(); 
                    escribir.close();
                    JOptionPane.showMessageDialog(null,"SE HA ENROLADO AL SENSOR EXITOSAMENTE");
                    }
            catch (IOException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            }  
        }  
    public static void agregarPropiedad(Usuario u){
        String[] propiedades = {"co","humidity","ligth","lpg","motion","smoke","temp"};
        int eleccion = JOptionPane.showOptionDialog(null, "ESCOJA LA PROPIEDAD QUE DESEA AGREGAR", "REGISTRO USUARIO"
            ,JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE
            ,null, propiedades, propiedades[0]);
        String peligro = JOptionPane.showInputDialog("ESCRIBA EL VALOR DE PELIGRO");
        String moderado = JOptionPane.showInputDialog("ESCRIBA EL VALOR MODERADO");
        if(eleccion!=-1){
            u.getPropiedades().put(propiedades[eleccion],peligro+"/"+moderado);
        File archivo = new File("ListaUsuarios.txt");                    
        try {
            archivo.delete();
            archivo.createNewFile();
            FileWriter escribir = new FileWriter(archivo,true);
            PrintWriter line = new PrintWriter(escribir,true);
            for(Usuario user:IniciarSesion.listaUsuarios){
                line.println(user);
            }
            line.close();
            escribir.close();
            JOptionPane.showMessageDialog(null, "SE HA AGREGAD0 LA PROPIEDAD");            
            } 
        catch (IOException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        else{}
        }
    }
    

