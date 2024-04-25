/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tester;

/**
 *
 * @author jaros
 */
public class Pytanie {
    private static int licznikPytań = 1;
    private int id;
    private String treśćPytania;
    private int punktyZaZadanie;
    private String[] odpowiedzi;
    private String poprawnaOdpowiedz;
    private String wybranaOdpowiedz;

    public Pytanie(String treśćPytania, int punktyZaZadanie, String[] odpowiedzi, String poprawnaOdpowiedz, String wybranaOdpowiedz) {
        this.id = licznikPytań++;
        this.treśćPytania = treśćPytania;
        this.punktyZaZadanie = punktyZaZadanie;
        this.odpowiedzi = odpowiedzi;
        this.poprawnaOdpowiedz = poprawnaOdpowiedz;
        this.wybranaOdpowiedz = "";
    }
    public int getId() {
        return id;
    }
    public String getQuestionText() {
        return treśćPytania;
    }
    public int getPunktyZaZadanie() {
        return punktyZaZadanie;
    }
    public String[] getOdpowiedzi() {
        return odpowiedzi;
    }
    public String getPoprawnaOdpowiedz() {
        return poprawnaOdpowiedz;
    }
    public String getWybranaOdpowiedz() {
        return wybranaOdpowiedz;
    }
     public void setWybranaOdpowiedz(String wybranaOdpowiedz) {
        this.wybranaOdpowiedz = wybranaOdpowiedz;
    }
}
