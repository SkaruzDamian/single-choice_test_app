/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tester;

/**
 *
 * @author jaros
 */
import java.util.ArrayList;
import java.util.List;

public class Student extends Uzytkownik{
    private int punktyStudenta;
    private List<Pytanie> niepoprawneOdpowiedzi;

    public Student(String imie) {
        super(imie);
        punktyStudenta = 0;
        niepoprawneOdpowiedzi = new ArrayList<>();
    }

    public int getPunktyStudenta() {
        return punktyStudenta;
    }
    @Override
    public String getImie(){
        return imie;
    }
    public List<Pytanie> getNiepoprawneOdpowiedzi() {
        return niepoprawneOdpowiedzi;
    }
    
}