package Notificacion;


public class NotificacionSensor extends Notificacion {
    private String propiedad;
    private String sensorId;

    public NotificacionSensor(String propiedad, String sensorId, float valor, String fechaNotificacion) {
        super( valor, fechaNotificacion);
        this.propiedad = propiedad;
        this.sensorId = sensorId;
    }

    @Override
    public String toString() {
        if(this.etiqueta.equalsIgnoreCase("PELIGRO")){
        return this.etiqueta+"El sensor "+this.sensorId+" evaluo la propiedad "+this.propiedad+" en la fecha"+
                this.fechaNotificacion+" tiene un valor "+this.valor+ " mayor al rango pemitido. PRESTAR ATENCION";
        }
        else{
        return this.etiqueta+"El sensor "+this.sensorId+" evaluo la propiedad "+this.propiedad+" en la fecha"+
                this.fechaNotificacion+" tiene un valor "+this.valor+ " dentro del rango pemitido.";
        }
    }
    
         
}
