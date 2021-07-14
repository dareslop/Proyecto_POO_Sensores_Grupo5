package Sensor;

import Porpiedades.PropiedadesEvaluadas;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import Menu.Menu;

public class Observacion {  
    private String observacion;

    public Observacion(String observacion) {
        this.observacion = observacion;
    }  
    public String getObservacion() {
        return this.observacion;
    }
    

    public static void leerObservaciones(){     
    Sensor s1;
    PropiedadesEvaluadas p1;
    try (BufferedReader lector = new BufferedReader(new FileReader("iot_telemetry_data_new.csv"))){             
            String line = "";      
            line = lector.readLine();            
            while((line = lector.readLine())!= null){                
                String [] sep = line.split(",");                
                String[] fecha=sep[9].split(" ");
                p1 = new PropiedadesEvaluadas(fecha[0]+"-"+fecha[1],Float.parseFloat(sep[2].substring(1, sep[2].length()-1)),
                        Float.parseFloat(sep[3].substring(1, sep[3].length()-1)),Boolean.valueOf(sep[4]),
                        Float.parseFloat(sep[5].substring(1, sep[5].length()-1)),
                Boolean.valueOf(sep[6]), Float.parseFloat(sep[7].substring(1, sep[7].length()-1)),
                        Float.parseFloat(sep[8].substring(1, sep[8].length()-1)));   
                s1=new Sensor(sep[1],p1);        
                Menu.sensores.add(s1);                          
                    }
                }                          
         
    catch (FileNotFoundException ex) {
            System.err.println("No existe el archivo: "+ex);
        } 
    catch (IOException ex) {
            System.err.println("Error lectura del archivo: "+ex);
        }  
    catch(NumberFormatException ex){
        System.err.println("error no tengo idea");
        }
    }

    @Override
    public String toString() {
        return observacion ;
    }
    
}
