package Sensor;

import Porpiedades.PropiedadesEvaluadas;
import java.util.Objects;

public class Sensor {
    private String id;       
    private PropiedadesEvaluadas evaluaciones;    

    public Sensor(String id,PropiedadesEvaluadas evaluaciones) {
        this.id = id;       
        this.evaluaciones = evaluaciones;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public PropiedadesEvaluadas getEvaluaciones() {
        return evaluaciones;
    }
    public void setEvaluaciones(PropiedadesEvaluadas evaluaciones) {
        this.evaluaciones = evaluaciones;
    }  
    
    public void generarObservacion(){                    
                                 
        String[] propiedades = this.evaluaciones.toString().split(",");
        for(String s:propiedades){
            if(s.contains("temp") && !s.contains("fechaEvaluacion")){
                String[] str = s.split("-"); 
                Notificacion.Notificacion.observaciones.add(new Observacion("El sensor "+this.id+" realiza una observación en la fecha "
                    +this.evaluaciones.getFechaEvaluacion()+" para la propiedad "+str[0]+" cuyo resultado es "+str[1]+" grados."));                
            }                                                                     //2-8-14-17
            if(!s.contains("fechaEvaluacion")){
            String[] str = s.split("-");                  
                Notificacion.Notificacion.observaciones.add(new Observacion("El sensor "+this.id+" realiza una observación en la fecha "
                    +this.evaluaciones.getFechaEvaluacion()+" para la propiedad "+str[0]+" cuyo resultado cuyo resultado es "+str[1]+" ."));               
            }
        }
    }          
@Override
    public boolean equals(Object obj) { 
         if(obj == null){
        return false;
        }
        final Sensor other = (Sensor) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }       
        return true;
    }  
@Override
    public String toString() {
        return this.id;
    }
    
}  
