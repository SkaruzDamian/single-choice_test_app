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

public class Test {
    private List<Pytanie> listaPytań;
    private List<String> listaNiepoprawnychOdpowiedzi;

    public Test(List<Pytanie> listaPytań) {
        this.listaPytań = listaPytań;
    }
    public List<Pytanie> getListaPytań() {
        return listaPytań;
    }
    public void setListaPytań(List<Pytanie> listaPytań) {
        this.listaPytań = listaPytań;
    }
    public List<Pytanie> getListaNiepoprawnychOdpowiedzi() {
        List<Pytanie> listaNiepoprawnychOdpowiedzi = new ArrayList<>();
        for (Pytanie pytanie : listaPytań) {
            
            if (!pytanie.getPoprawnaOdpowiedz().equals(pytanie.getWybranaOdpowiedz())) {
                listaNiepoprawnychOdpowiedzi.add(pytanie);
            }
        }
        return listaNiepoprawnychOdpowiedzi;
    }
     public void setListaNiepoprawnychOdpowiedzi(List<String> listaNiepoprawnychOdpowiedzi) {
        this.listaNiepoprawnychOdpowiedzi = listaNiepoprawnychOdpowiedzi;
    }
}
