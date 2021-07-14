package Usuario;
import Usuario.Usuario;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class IniciarSesion {
    public static ArrayList<Usuario> listaUsuarios = new ArrayList<>();    
   
    public static void leerListaUsuarios(){
       Usuario u;
        try (BufferedReader lector = new BufferedReader(new FileReader("ListaUsuarios.txt"))){             
            String line = "";                        
            while((line = lector.readLine())!= null){                
                String [] sep = line.split("-");
                String[] user = sep[0].split(":");
                String[] pass = sep[1].split(":");
                String[]rangoValores = sep[2].split("/");
                u = new Usuario(user[1],pass[1],rangoValores[0],rangoValores[1]);
                listaUsuarios.add(u);
            }            
        } 
    catch (FileNotFoundException ex) {
            System.err.println("No existe el archivo: "+ex);
        } 
    catch (IOException ex) {
            System.err.println("Error lectura del archivo: "+ex);
        }  
    }   
    public static Usuario login(String nombre,String pass){               
        for(Usuario user:listaUsuarios){
            if (nombre.equals(user.getNombreUsuario())&& pass.equals(user.getContraseña())){
               JOptionPane.showMessageDialog(null,"INICIO DE SESION CORRECTO");
               return user;
                }
            else{                  
               JOptionPane.showMessageDialog(null,"Usuario no registrado"); 
               JOptionPane.showMessageDialog(null,"INICIO DE SESION FALLIDO");
               return null;
           }
        }
        return null;
    }
}   
