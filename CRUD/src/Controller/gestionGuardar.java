/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Producto;
import View.ventana;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class gestionGuardar implements ActionListener {

    ventana v;
    String consultaInsertar = "INSERT INTO productos (producto, precio, cantidad) VALUES (?,?,?);";

    String tproducto;
    int tid, tcantidad;
    float tprecio;

    public gestionGuardar(ventana x) {
        this.v = x;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String producto = v.txtProducto.getText();
        String precio2 = v.txtPrecio.getText();
        String cantidad2 = v.txtCantidad.getText();

        if ("".equals(producto) || "".equals(precio2) || "".equals(cantidad2)) {
            JOptionPane.showMessageDialog(null,
                    "Ingrese todos los datos",
                    "Atencion",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            try {

                float precio = Float.parseFloat(precio2);
                int cantidad = Integer.parseInt(cantidad2);

                Producto p = new Producto(producto, precio, cantidad);
                try {
                    Connection con = Singleton.getInstance();
                    PreparedStatement ps = con.prepareStatement(consultaInsertar);
                    ps.setString(1, p.getProducto());
                    ps.setFloat(2, p.getPrecio());
                    ps.setInt(3, p.getCantidad());
                    ps.execute();

                    PreparedStatement ps2 = con.prepareStatement(v.cosulta);
                    ResultSet rs = ps2.executeQuery();

                    v.txtID.setText(null);
                    v.txtProducto.setText(null);
                    v.txtPrecio.setText(null);
                    v.txtCantidad.setText(null);

                    while (rs.next()) {
                        tid = rs.getInt("id");
                        tproducto = rs.getString("producto");
                        tprecio = rs.getFloat("precio");
                        tcantidad = rs.getInt("cantidad");
                    }
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            } catch (NumberFormatException n) {

            }
            Object[] filaDatos2 = {tid, tproducto, tprecio, tcantidad};
            v.tableModel.addRow(filaDatos2);

        }
    }

}
