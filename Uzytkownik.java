/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tester;

/**
 *
 * @author jaros
 */
public abstract class Uzytkownik {
    protected String imie;

    public Uzytkownik(String imie) {
        this.imie = imie;
    }
    public String getImie() {
        return imie;
    }
}
