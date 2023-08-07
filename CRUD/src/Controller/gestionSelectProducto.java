/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.ventana;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JTable;

/**
 *
 * @author uriel
 */
public class gestionSelectProducto implements MouseListener{
    
    ventana v;
    
    
    public gestionSelectProducto(ventana x){
        this.v=x;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        }

    @Override
    public void mousePressed(MouseEvent e) {
        v.tabla = (JTable) e.getSource();
        Point point = e.getPoint();
        int row = v.tabla.rowAtPoint(point);
        int column =0;
            if (e.getClickCount()==1 && v.tabla.getSelectedRow() != -1){
                v.btnGuardar.setEnabled(false);
                int row2 = v.tabla.getSelectedRow();
                int idSe = (int) v.tabla.getValueAt(row2, column);
                column++;
                String productoSe = (String) v.tabla.getValueAt(row2, column);
                column++;
                float precioSe = (float) v.tabla.getValueAt(row2, column);
                column++;
                int cantidadSe = (int) v.tabla.getValueAt(row2, column);
                v.txtID.setText(Integer.toString(idSe));
                v.txtProducto.setText(productoSe);
                v.txtPrecio.setText(Float.toString(precioSe));
                v.txtCantidad.setText(Integer.toString(cantidadSe));
                v.btnEditar.setEnabled(true);
                v.btnEliminar.setEnabled(true);
            }
        }

    @Override
    public void mouseReleased(MouseEvent e) {
        }

    @Override
    public void mouseEntered(MouseEvent e) {
        }

    @Override
    public void mouseExited(MouseEvent e) {
        }
    
}
