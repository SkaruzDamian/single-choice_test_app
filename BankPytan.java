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

public class BankPytan {
    private static BankPytan instance;
    private List<Pytanie> wszystkiePytania;
    private BankPytan() {
        wszystkiePytania = new ArrayList<>();
    }
    
    public static BankPytan getInstance() {
        if (instance == null) {
            instance = new BankPytan();
        }
        return instance;
    }
    public void dodajPytanie(Pytanie pytanie) {
        wszystkiePytania.add(pytanie);
    }
    public List<Pytanie> getWszystkiePytania() {
        return wszystkiePytania;
    }
}
