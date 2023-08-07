/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package productorconsumidor;

import Controller.Hilo;

/**
 *
 * @author uriel
 */
public class ProductorConsumidor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Hilo().setVisible(true);
        int nC=5;
        
        int numHilos = nC + 1;
        
        Thread[] hilos = new Thread[numHilos];

        for(int h=0; h < hilos.length ; h++){
            Runnable runnable = null;
            
            if(h != 0){//todos los demas hilos entran en esta condicion, siendo 9 los restantes
                runnable = new Hilo(true,h);//indicamos aqui que es un consumidor
            }
            else{//Cuando el primer hilo sea procesado/lanzado se le indicara que es consumidor, asi aumentan o se aseguran las posibilidades de que entre primero
                runnable = new Hilo(false,h);//indica entonces que es un productor
            }
            
            hilos[h] = new Thread(runnable);
            hilos[h].start(); //se inician todos los hilos  
        }        
        for(int h=0; h<hilos.length; h++){
            try{
                hilos[h].join();
            }catch(Exception ex){}
        }
    }
    
}
