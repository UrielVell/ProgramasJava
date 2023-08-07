/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.ventana;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author uriel
 */
public class gestionLimpiar implements ActionListener{
    
    ventana v;
    
    public gestionLimpiar(ventana x){
        this.v=x;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        v.txtID.setText("");
        v.txtProducto.setText("");
        v.txtCantidad.setText("");
        v.txtPrecio.setText("");
        v.txtProducto.setRequestFocusEnabled(true);
    }
    
    
    
}
