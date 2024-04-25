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

public class Nauczyciel extends Uzytkownik {
    private List<Student> studenci;
    private Test test;
    public Nauczyciel(String imie) {
        super(imie);
        studenci = new ArrayList<>();
    }
    public Test usunTest(Test test) {
                if(test != null){
                test = null;
                return test;
                }
                return null;
    }
    @Override
    public String getImie(){
        return imie;
    }
}
