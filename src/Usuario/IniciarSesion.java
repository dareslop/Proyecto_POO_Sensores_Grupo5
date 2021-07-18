package Usuario;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class IniciarSesion {
    public static ArrayList<Usuario> listaUsuarios = new ArrayList<>();    
   
    public static boolean leerListaUsuarios(){
       Usuario u=null;
        try (BufferedReader lector = new BufferedReader(new FileReader("ListaUsuarios.txt"))){             
            String line = "";            
            while((line = lector.readLine()) != null){                
                String [] sep = line.split("-");                
                String[] user = sep[0].split(":");
                String[] pass = sep[1].split(":");
                String[] valores = sep[3].split(";");//[valor1,valor2....]
                String[] p = sep[2].split(":");
                String[] propiedades = p[1].split(";");
                String[] sensores=null;
                try{
                sensores = sep[4].split(";");}                
                catch(ArrayIndexOutOfBoundsException ex){
                    //JOptionPane.showMessageDialog(null, "NO TIENE SENSORES ENROLADOS");
                }
                u = new Usuario(user[1],pass[1]);
                for(int i=0;i<propiedades.length;i++){
                    String[] rangoValores = valores[i].split("/");
                    u.getPropiedades().put(propiedades[i],rangoValores[0]+"/"+rangoValores[1]);                    
                    }                    
                        try{
                        for(String sensor:sensores){                                                        
                            u.getSensoresIds().add(sensor);
                                }
                            }                        
                        catch(NullPointerException ex){
                            //JOptionPane.showMessageDialog(null, "NO HAY SENSORES");
                            }
                        listaUsuarios.add(u);
                        return true;
                        }                    
                    }          
    catch (FileNotFoundException ex) {
            System.err.println("No existe el archivo: "+ex);      
        } 
    catch (IOException ex) {
            System.err.println("Error lectura del archivo: "+ex);            
        }
        return false;
    } 
    public static Usuario login(String nombre,String pass){   
        Usuario u = new Usuario(nombre,pass);        
        if(pass.equals("")){
           JOptionPane.showMessageDialog(null,"INGRESE UNA CONTRASEÑA VALIDA"); 
            JOptionPane.showMessageDialog(null,"INICIO DE SESION FALLIDO"); 
            return null;
        }
        boolean existe = false;
        for(Usuario user:listaUsuarios){               
            if (user.equals(u)){
               existe = true;
               JOptionPane.showMessageDialog(null,"INICIO DE SESION CORRECTO");
               return user;
                }
            }
        if(!existe){
            JOptionPane.showMessageDialog(null,"Usuario no registrado"); 
            JOptionPane.showMessageDialog(null,"INICIO DE SESION FALLIDO");
            return null;
            }
    return null;   
        }    
}
   

    
  



   
   
