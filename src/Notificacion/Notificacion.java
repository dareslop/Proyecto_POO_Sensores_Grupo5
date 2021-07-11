package Notificacion;

import Sensor.Observacion;
import java.util.ArrayList;


public class Notificacion{
    protected String fechaNotificacion;
    protected String etiqueta;     
    protected float valor;
    public static ArrayList<Observacion> observaciones = new ArrayList<>();

    public Notificacion() {
    } 
    public Notificacion(float valor, String fechaNotificacion ) {        
        this.valor = valor;
        this.fechaNotificacion = fechaNotificacion;
    }
    public String getEtiqueta() {
        return etiqueta;
    }
    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }
   
    
}
