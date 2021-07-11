package Usuario;


import Porpiedades.PropiedadesEvaluadas;
import Sensor.Sensor;
import Notificacion.Notificacion;
import Notificacion.NotificacionPropiedad;
import Notificacion.NotificacionSensor;
import java.util.ArrayList;
import java.util.Objects;

public class Usuario {
    private String nombreUsuario;
    private String contraseña;   
    private String propiedadEvaluar = "co";
    private String rangoPeligro;
    private String rangoModerado;
    private ArrayList<String> sensoresIds = new ArrayList<>();
    private ArrayList<Notificacion> notificaciones = new ArrayList<>();   
    

    public Usuario(String nombreUsuario, String contraseña,String rangoPeligro,String rangoModerado) {        
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;  
        this.rangoPeligro = rangoPeligro;
        this.rangoModerado = rangoModerado;
    }    
    
    public Usuario(){}
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
    public String getPropiedadEvaluar() {
        return propiedadEvaluar;
    }
    public ArrayList<String> getSensoresIds() {
        return sensoresIds;
    }
    public ArrayList<Notificacion> getNotificaciones() {
        return notificaciones;
    }   
    public String getRangoPeligro() {
        return rangoPeligro;
    }
    public void setRangoPeligro(String rangoPeligro) {
        this.rangoPeligro = rangoPeligro;
    }
    public String getRangoModerado() {
        return rangoModerado;
    }
    public void setRangoModerado(String rangoModerado) {
        this.rangoModerado = rangoModerado;
    }
    
    
    @Override
    public boolean equals(Object obj) {        
        if(obj == null){
        return false;
        }
        final Usuario other = (Usuario) obj;
        if (!Objects.equals(this.nombreUsuario, other.nombreUsuario) && !Objects.equals(this.contraseña, other.contraseña)) {
            return false;
        }       
        return true;
    }   

    @Override
    public String toString() {
        return nombreUsuario;
    }
    
}
