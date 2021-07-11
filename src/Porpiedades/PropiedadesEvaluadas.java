package Porpiedades;

public class PropiedadesEvaluadas {
  private final String fechaEvaluacion;  
  private final double co;
  private final  double humidity;
  private final boolean ligth;
  private final  double lpg;
  private final  boolean motion;
  private final  double smoke;
  private final double temp;
  

  public PropiedadesEvaluadas(String fechaEvaluacion,float co, float humidity, boolean ligth, float lpg, boolean motion, float smoke, float temp) {
    this.fechaEvaluacion = fechaEvaluacion;
    this.co = co;
    this.humidity = humidity;
    this.ligth = ligth;
    this.lpg = lpg;
    this.motion = motion;
    this.smoke = smoke;
    this.temp = temp;
    }
    public double getCo() {
        return co;
    }
    public double getHumidity() {
        return humidity;
    }
    public boolean isLigth() {
        return ligth;
    }
    public double getLpg() {
        return lpg;
    }
    public boolean isMotion() {
        return motion;
    }
    public double getSmoke() {
        return smoke;
    }
    public double getTemp() {
        return temp;
    }  
    public String getFechaEvaluacion() {
        return fechaEvaluacion;
    }
    
@Override
  public String toString() {
      //propiedad-valor,
    return  "fechaEvaluacion-"+fechaEvaluacion+",co-" + co + ",humidity-" + humidity + ",ligth-" + ligth + ",lpg-" + lpg + ",motion-" + motion + ",smoke-" + smoke + ",temp-" + temp;
    }
    
  
}
