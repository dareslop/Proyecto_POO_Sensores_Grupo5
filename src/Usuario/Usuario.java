package Usuario;



import Notificacion.Notificacion;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

public class Usuario {
    private String nombreUsuario;
    private String contraseña;
    private HashMap<String,String> propiedades;   
    private ArrayList<String> sensoresIds = new ArrayList<>();
    private ArrayList<Notificacion> notificaciones = new ArrayList<>();   

    public Usuario(String nombreUsuario, String contraseña) {
        this.propiedades = new HashMap<>();
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
    }    
    public Usuario(String nombreUsuario, String contraseña,String propiedad,String rangoPeligro,String rangoModerado) {        
        this.propiedades = new HashMap<>();
        this.propiedades.put(propiedad, rangoPeligro+"/"+rangoModerado);
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña; 
    }   
    public Usuario(){        
    }        
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    public String getContraseña() {
        return contraseña;
    }
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    } 
    public HashMap<String,String> getPropiedades() {
        return this.propiedades;
    }
    public ArrayList<String> getSensoresIds() {
        return sensoresIds;
    }
    public ArrayList<Notificacion> getNotificaciones() {
        return notificaciones;
    }      
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        if (!Objects.equals(this.nombreUsuario, other.nombreUsuario)) {
            return false;
        }
        if (!Objects.equals(this.contraseña, other.contraseña)) {
            return false;
        }
        return true;
    }  
    @Override
    public String toString(){
        String propiedades = "";
        String sensores = "";
        String valores = "";
        for(Object p:this.propiedades.keySet()){
            propiedades +=p+";";
        }
        for(Object v:this.propiedades.values()){
            valores+=v+";";
        }
        for(String s:this.sensoresIds){           
            sensores+=s+";";  
        }        
        return "Usuario:"+this.getNombreUsuario()+"-"+"Contraseña:"
                        +this.getContraseña()+"-Propiedad:"+propiedades+"-"
                +valores+"-"+sensores;
    }
}
    
    
