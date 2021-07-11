package Notificacion;

public class NotificacionPropiedad extends Notificacion {
    private String nombrePropiedad;    

    public NotificacionPropiedad(String nombrePropiedad, float valor, String fechaNotificacion) {
        super( valor, fechaNotificacion);
        this.nombrePropiedad = nombrePropiedad;
    }

    @Override
    public String toString() {
        if(this.etiqueta.equalsIgnoreCase("PELIGRO")){
        return this.etiqueta+" la propiedad "+this.nombrePropiedad+ " evaluada en la fecha "+this.fechaNotificacion+
                " tiene un valor "+this.valor+ " mayor al rango permitido. PRESTAR ATENCION.";
        }
        else{
        return this.etiqueta+" la propiedad "+this.nombrePropiedad+ " evaluada en la fecha "+this.fechaNotificacion+
                " tiene un valor "+this.valor+ " dentro del rango permitido.";
        }
    }  
}
