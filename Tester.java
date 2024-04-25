/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.tester;

import javax.swing.SwingUtilities;

/**
 *
 * @author jaros
 */
public class Tester {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AplikacjaOkienkowa().setVisible(true);
        });
    }
}
