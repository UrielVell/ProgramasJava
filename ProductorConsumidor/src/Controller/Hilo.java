/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import productorconsumidor.ProductorConsumidor;

/**
 *
 * @author uriel
 */
public class Hilo extends JFrame implements Runnable {

    int AnchoVentana = 1000;
    int AltoVentana = 1000;

    BufferedImage bi;
    //imagenes
    Image fondo, chef, cliente, plato, chefS;

    private boolean consumidor;
    private int id;
    private static int ultimoC = -1;
    //quiero 5 consumidores y 10 platillos 
    private static int platillo = 0, x1d = 0, y1d = 0, x2d = 0, y2d = 0, nC = 5, nP = 9;//nC=Numero Canibales nP=Numero Platillos
    private static Object lock = new Object();

    public Hilo(boolean consumidor, int h) {
        this.consumidor = consumidor;
        this.id = h;
    }

    public Hilo() {
        setSize(AnchoVentana, AltoVentana);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Ejercicio Consumidor Productor");
        setResizable(true);
        
        

        bi = new BufferedImage(AnchoVentana, AltoVentana, BufferedImage.TYPE_INT_RGB);

        Toolkit herramienta = Toolkit.getDefaultToolkit();

        fondo = herramienta.getImage(getClass().getResource("/img/Mesa.jpg"));
        chef = herramienta.getImage(getClass().getResource("/img/Chef.png"));
        cliente = herramienta.getImage(getClass().getResource("/img/Comiendo.png"));
        plato = herramienta.getImage(getClass().getResource("/img/Plato.png"));
        chefS = herramienta.getImage(getClass().getResource("/img/Dormir.png"));
    }

    @Override
    public void run() {
        while (true) {

            if (consumidor) {
                consumiendo();
            } else {
                cocinando();
            }
        }
    }

    private void cocinando() {

        synchronized (lock) {
            //repaint();
            if (platillo == 0) {//si la mesa está vacia
                if (ultimoC != -1) {//si hay consumidor en espera
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ProductorConsumidor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    platillo = nP;
                    x1d = 1000;
                    y1d = 220;
                    x2d = 1150;
                    y2d = 370;
                    System.out.println("Soy el chef, he puesto " + platillo + " platos en la mesa");

                    lock.notifyAll();//despertamos a todos para que los consumidores pasen
                    try {
                        lock.wait();
                    } catch (InterruptedException ex) {

                    }
                } else {
                    try {
                        //System.out.println("Hola "+ ultimoC);
                        lock.wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ProductorConsumidor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        }

    }

    private void consumiendo() {
        synchronized (lock) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
            }

            if (platillo != 0) {//si hay platillos en la mesa

                if (ultimoC != -1) {//si hay consumidor en espera

                    if (ultimoC == this.id) {
                        x1d = x1d - 95;
                        x2d = x2d - 95;//avance en perrito para que vaya al plato
                        System.out.println("Soy" + this.id + " regrese, me he comido el platillo " + (platillo));
                        platillo--;
                        System.out.println("                   Quedan " + platillo + " platos mas");
                        ultimoC = -1;
                        lock.notifyAll();
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException ex) {
                        }
                    } else {
                        try {
                            lock.wait();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ProductorConsumidor.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else {
                    x1d = x1d - 95;
                    x2d = x2d - 95;//avance en perrito para que vaya al plato
                    System.out.println("Soy " + this.id + " me he comido el platillo " + (platillo) + " no hay nadie en espera");
                    platillo--;
                    System.out.println("                   Quedan " + platillo + " platos mas");

                    lock.notifyAll();//Se despiertan todos los hilos por si quedan platos por consumir

                    try {
                        Thread.sleep(5000);
                        lock.wait();
                    } catch (InterruptedException ex) {
                    }
                }
            } else {//si no hay platillos en la mesa     //crear variable para saber ultimo consumidor crear if que no deje entrar a ningun consumidor a menos que sea el ultimo

                if (ultimoC != -1) {
                    //System.out.println("Soy " + this.id + " mesa vacia quiero uno");
                    lock.notifyAll();
                    try {
                        lock.wait();
                    } catch (InterruptedException ex) {
                    }
                } else if (ultimoC == -1) {
                    ultimoC = this.id;

                    System.out.println("Soy " + this.id + " encontre: " + platillo + " platillos, quiero uno" + ultimoC);
                    lock.notifyAll();//al no poder despertar un hilo en concreto se despiertan a todos

                }

            }
        }
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d;
        g.drawImage(bi, 0, 0, this);
        g2d = bi.createGraphics();

        g2d.drawImage(fondo,new AffineTransform(1f,0f,0f,1f,0,0), null);

        if (platillo == 0) {
            g2d.drawImage(chef, 800, 80, 950, 300, 0, 0, 237, 341, this);
            g2d.drawImage(cliente, 1000, 220, 1150, 370, 0, 0, 1600, 1600, this);
        }
        if (platillo != 0) {
            g2d.drawImage(chefS, 800, 80, 950, 300, 0, 0, 237, 341, this);
        }
        if (platillo > 0) {

            int x1 = 100, y1 = 280, x2 = 200, y2 = 380;

            for (int a = 0; a < platillo; a++) {
                g2d.drawImage(plato, x1, y1, x2, y2, 0, 0, 680, 680, this);
                x1 = x1 + 100;
                x2 = x2 + 100;
            }

            g2d.drawImage(cliente, x1d, y1d, x2d, y2d, 0, 0, 1600, 1600, this);  
        }

        repaint();
    }

}
