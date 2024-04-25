/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tester;

/**
 *
 * @author jaros
 */
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class AplikacjaOkienkowa extends JFrame{
    private static final int maksymalnaIlośćPunktów = 60;
    private static final String plikDoZapisywaniWyników = "wyniki.txt";
    
    private boolean czyWpisanoKryterium = false;
    private static Map<Integer, int[]> kryteriumOcen = new HashMap<>();
    
    private JButton przyciskDoRejestracji;
    private JButton przyciskDoRozwiązaniaTestu;
    private JTextArea obszarNaWynik;
    
    private Nauczyciel nauczyciel;
    private Student student;
    private Test test;
    
    private List<Student> listaStudentów;
    private List<Integer> listaPunktów;
    private JPanel panel;
    public AplikacjaOkienkowa() {
        główneOkienko();
        załadujPytaniaZPliku();
        listaStudentów = new ArrayList<>();
        listaPunktów = new ArrayList<>();
    }
    
    public void główneOkienko() {
        setTitle("Aplikacja do przeprowadzania testów z programowania obiektowego");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        przyciskDoRejestracji = new JButton("Zarejestruj się jako nauczyciel/opcje nauczyciela");
        przyciskDoRejestracji.addActionListener((ActionEvent e) -> {
            if(nauczyciel == null){
                zarejestrujNauczyciela();
            }else{
                String nazwaNauczyciela = JOptionPane.showInputDialog(null, "Wpisz nazwe nauczyciela");
                if (nazwaNauczyciela !=null && nazwaNauczyciela.equals(nauczyciel.imie)) {
                    nauczyciel = new Nauczyciel(nazwaNauczyciela);
                    pokażOpcjeNauczyciela();
                }else{
                    JOptionPane.showMessageDialog(null,"Spróbuj ponownie");
                }
            }
        });
        panel.add(przyciskDoRejestracji);

        przyciskDoRozwiązaniaTestu = new JButton("Rozwiąż test");
        przyciskDoRozwiązaniaTestu.addActionListener((ActionEvent e) -> {
            rozwiążTest();
        });
        panel.add(przyciskDoRozwiązaniaTestu);

        obszarNaWynik = new JTextArea(10, 30);
        obszarNaWynik.setEditable(false);
        
        setContentPane(panel);
        setVisible(true);
    }
    private void załadujPytaniaZPliku() {
        try {
            File plikZPytaniami = new File("dane.txt");
            BufferedReader odczytanieDanych = new BufferedReader(new FileReader(plikZPytaniami));
            String pobranaLinia;
            while ((pobranaLinia = odczytanieDanych.readLine()) != null) {
                przetworzenieWczytanejLinii(pobranaLinia);
            }
            odczytanieDanych.close();
            } catch (IOException e) {
            wyswietlBlad("Błąd z przetworzeniem pliku");
            }
    }
    private void przetworzenieWczytanejLinii(String pobranaLinia) {
        String[] dane = pobranaLinia.split(";");
        if (dane.length == 7) {
            String treśćPytania = dane[0];
            int punktyZaPytanie = Integer.parseInt(dane[1]);
            String[] pytania = new String[4];
            System.arraycopy(dane, 2, pytania, 0, 4);
            String poprawnaOdpowiedz = dane[6];
            String wybranaOdpowiedz = "";
            Pytanie pytanie = new Pytanie(treśćPytania, punktyZaPytanie, pytania, poprawnaOdpowiedz, wybranaOdpowiedz);
            BankPytan.getInstance().dodajPytanie(pytanie);
        }
    }
    private void zarejestrujNauczyciela() {
        if (nauczyciel != null) {
            wyswietlBlad("Nauczyciel jest już zarejestrowany.");
            return;
        }
        String nazwaNauczyciela = JOptionPane.showInputDialog(this, "Podaj nazwe nauczyciela:");
        if (nazwaNauczyciela != null && !nazwaNauczyciela.isEmpty()) {
            nauczyciel = new Nauczyciel(nazwaNauczyciela);
            wyswietlInformacje("Nauczyciel pomyślnie zarejestrowany.");
        }
    }
    private void pokażOpcjeNauczyciela(){
    JFrame okienkoZOpcjami = new JFrame("Opcje nauczyciela");
    okienkoZOpcjami.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel panelZOpcjami = new JPanel();
    panelZOpcjami.setLayout(new GridLayout(5, 1));

    JButton przyciskDoTworzeniaTestu = new JButton("Stwórz test");
    przyciskDoTworzeniaTestu.addActionListener((ActionEvent e) -> {
        stwórzTest();
    });
    panelZOpcjami.add(przyciskDoTworzeniaTestu);

    JButton przyciskDoModyfikacjiTestu = new JButton("Zmodyfikuj test");
    przyciskDoModyfikacjiTestu.addActionListener((ActionEvent e) -> {
        zmodyfikujTest();
    });
    panelZOpcjami.add(przyciskDoModyfikacjiTestu);

    JButton przyciskDoUsuwaniaTestu = new JButton("Usuń test");
    przyciskDoUsuwaniaTestu.addActionListener((ActionEvent e) -> {
        usuńTest();
    });
    panelZOpcjami.add(przyciskDoUsuwaniaTestu);

    JButton przyciskDoWyświetlaniaWyników = new JButton("Wyświetl wyniki");
    przyciskDoWyświetlaniaWyników.addActionListener((ActionEvent e) -> {
        wyświetlWyniki();
    });
    panelZOpcjami.add(przyciskDoWyświetlaniaWyników);

    JButton przyciskZPowrotemDoGłównegoOkienka = new JButton("Powrót");
    przyciskZPowrotemDoGłównegoOkienka.addActionListener((ActionEvent e) -> {
        okienkoZOpcjami.setVisible(false);
    });
    panelZOpcjami.add(przyciskZPowrotemDoGłównegoOkienka);
    okienkoZOpcjami.getContentPane().add(panelZOpcjami);
    okienkoZOpcjami.pack();
    okienkoZOpcjami.setVisible(true);
    }
    private void stwórzTest() {
        List<Pytanie> wybranePytania = wybierzPytania(maksymalnaIlośćPunktów);
        if (wybranePytania.isEmpty()) {
            wyswietlBlad("Brak pytań");
            return;
        }
        if(czyWpisanoKryterium==false){
        podanieKryteriumNaOceny();   
        test = new Test(wybranePytania);
        wyswietlInformacje("Utworzono test");
        czyWpisanoKryterium = true;
        }
    }
    private void podanieKryteriumNaOceny() {
    JTextField miejsceNaMinimalnePunkty = new JTextField(10);
    JTextField miejsceNaMaksmylanePunkty = new JTextField(10);
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(3, 2));
    panel.add(new JLabel("Wpisz kryterium dla ocen (5, 4, 3, 2):"));
    panel.add(new JLabel(""));
    panel.add(new JLabel("Min Punkty:"));
    panel.add(miejsceNaMinimalnePunkty);
    panel.add(new JLabel("Max Punkty:"));
    panel.add(miejsceNaMaksmylanePunkty);

    int wynikKryterium = JOptionPane.showConfirmDialog(null, panel, "Zakres punktów", JOptionPane.OK_CANCEL_OPTION);
    if (wynikKryterium == JOptionPane.OK_OPTION) {
        try {
            String tekstZMinimalnymiPunktami = miejsceNaMinimalnePunkty.getText().trim();
            String tekstZMaksymalnymiPunktami = miejsceNaMaksmylanePunkty.getText().trim();

            String[] tablicaNaMinimalnePunkty = tekstZMinimalnymiPunktami.split(",");
            String[] tablicaNaMaksymalnePunkty = tekstZMaksymalnymiPunktami.split(",");

            if (tablicaNaMinimalnePunkty.length == tablicaNaMaksymalnePunkty.length && tablicaNaMinimalnePunkty.length == 4) {
                int[] oceny = {5, 4, 3, 2};

                for (int i = 0; i < tablicaNaMinimalnePunkty.length; i++) {
                    int minimalnePunkty = Integer.parseInt(tablicaNaMinimalnePunkty[i].trim());
                    int maksymalnePunkty = Integer.parseInt(tablicaNaMaksymalnePunkty[i].trim());
                    int wybranaOcena = oceny[i];

                    kryteriumOcen.put(wybranaOcena, new int[]{minimalnePunkty, maksymalnePunkty});
                }
            } else {
                wyswietlBlad("Nieprawidłowa liczba zakresów punktacji");
            }
        } catch (NumberFormatException e) {
            wyswietlBlad("Zły format danych");
        }
    }
    miejsceNaMinimalnePunkty.setVisible(true);
    miejsceNaMaksmylanePunkty.setVisible(true);
}
private List<Pytanie> wybierzPytania(int maksymalnaIloscPunktow) {
    List<Pytanie> wszystkiePytania = BankPytan.getInstance().getWszystkiePytania();
    List<Pytanie> wybranePytania = new ArrayList<>();
    if (wszystkiePytania.size() < 20 || maksymalnaIloscPunktow < 60) { 
        return wybranePytania;
    }
    Collections.shuffle(wszystkiePytania);
    int łącznaIlośćPunktów = 0;
    int index = 0;
    while (wybranePytania.size() < 20 && łącznaIlośćPunktów < maksymalnaIloscPunktow && index < wszystkiePytania.size()) {
        Pytanie pytanie = wszystkiePytania.get(index);
        if (łącznaIlośćPunktów + pytanie.getPunktyZaZadanie() <= maksymalnaIloscPunktow) {
            wybranePytania.add(pytanie);
            łącznaIlośćPunktów += pytanie.getPunktyZaZadanie();
        }
        index++;
    }
    if (wybranePytania.size() < 20 && łącznaIlośćPunktów < maksymalnaIloscPunktow) {
        int pozostałePunkty = maksymalnaIloscPunktow - łącznaIlośćPunktów;
        int pozostałePytania = 20 - wybranePytania.size();
        List<Pytanie> listaPozostałychPytań = new ArrayList<>();
        for (int i = index; i < wszystkiePytania.size(); i++) {
            Pytanie pytanie = wszystkiePytania.get(i);
            if (pytanie.getPunktyZaZadanie() <= pozostałePunkty) {
                listaPozostałychPytań.add(pytanie);
            }
        }
        Collections.shuffle(listaPozostałychPytań);
        int pozostałePytaniaDoDodania = Math.min(pozostałePytania, listaPozostałychPytań.size());
        wybranePytania.addAll(listaPozostałychPytań.subList(0, pozostałePytaniaDoDodania));
    }
    return wybranePytania;
}
    private void zmodyfikujTest() {
        if (test == null) {
            wyswietlBlad("Nie znaleziono testu.");
            return;
        }
        List<Pytanie> wybranePytania = wybierzPytania(maksymalnaIlośćPunktów);
        if (wybranePytania.isEmpty()) {
            wyswietlBlad("Brak pytań.");
            return;
        }
        test.setListaPytań(wybranePytania);
        wyswietlInformacje("Test zmodyfikowany pomyślnie.");
    }
    private void usuńTest() {
        if (test != null) {
            test = nauczyciel.usunTest(test);
            wyswietlBlad("Test usunięty pomyślnie.");
        } else {
            wyswietlBlad("Brak testu do usunięcia.");
        }
    }
    private void wyświetlWyniki() {
        if (listaStudentów.isEmpty()) {
            wyswietlInformacje("Brak studentów.");
        } else {
        JFrame oknoNaWyniki = new JFrame("Wyniki");
        oknoNaWyniki.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panelNaWyniki = new JPanel();
        panelNaWyniki.setLayout(new GridLayout(4, 2));
        JScrollPane scrollPane = new JScrollPane(obszarNaWynik);
        panelNaWyniki.add(scrollPane);
        StringBuilder wyniki = new StringBuilder();
        JButton przyciskNaSortowaniePunktami = new JButton("Wyniki posortowane punktami");
        JButton przyciskNaSortowanieImieniem = new JButton("Wyniki posortowane imieniem");
        JButton powrotButton = new JButton("Powrót");
            przyciskNaSortowanieImieniem.addActionListener((ActionEvent e) -> {
                Object[] opcje = { "Rosnąco", "Malejąco" };
                int wybór = JOptionPane.showOptionDialog(oknoNaWyniki, "Wybierz kolejność sortowania:", "Sortowanie",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcje, opcje[0]);
                Map<Student, Integer> MapazStudentamiIPunktami = new HashMap<>();
                for (int i = 0; i < listaStudentów.size(); i++) {
        Student student = listaStudentów.get(i);
        int punkty = listaPunktów.get(i);
        MapazStudentamiIPunktami.put(student, punkty);
    }
    List<Student> posortowaniStudenci = new ArrayList<>(listaStudentów);
            if (wybór == 0) {
                Collections.sort(posortowaniStudenci, Comparator.comparing(Student::getImie));
            } else if (wybór == 1) {
                Collections.sort(posortowaniStudenci, Comparator.comparing(Student::getImie, Collections.reverseOrder()));
            }
            wyniki.setLength(0);
            wyniki.append("Wyniki posortowane imieniem\n");
            for (Student student : posortowaniStudenci) {
                int punktyStudenta = MapazStudentamiIPunktami.get(student);
                int ocena = wezOcene(punktyStudenta);
                wyniki.append(student.getImie()).append(" - ").append(punktyStudenta).append(" punktów").append(" Ocena: ").append(ocena).append("\n");
            }
            obszarNaWynik.setText(wyniki.toString());
        });
            przyciskNaSortowaniePunktami.addActionListener((ActionEvent e) -> {
    Object[] opcje = {"Rosnąco", "Malejąco"};
    int wybór = JOptionPane.showOptionDialog(oknoNaWyniki, "Wybierz kolejność sortowania:", "Sortowanie",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcje, opcje[0]);

    List<Student> posortowaniStudenci = new ArrayList<>(listaStudentów);
    if (wybór == 0) {
        Collections.sort(posortowaniStudenci, (s1, s2) -> {
            int index1 = listaStudentów.indexOf(s1);
            int index2 = listaStudentów.indexOf(s2);
            int punkty1 = listaPunktów.get(index1);
            int punkty2 = listaPunktów.get(index2);
            return Integer.compare(punkty1, punkty2);
        });
    } else if (wybór == 1) {
        Collections.sort(posortowaniStudenci, (s1, s2) -> {
            int index1 = listaStudentów.indexOf(s1);
            int index2 = listaStudentów.indexOf(s2);
            int punkty1 = listaPunktów.get(index1);
            int punkty2 = listaPunktów.get(index2);
            return Integer.compare(punkty2, punkty1);
        });
    }

    StringBuilder wynik = new StringBuilder();
    wynik.setLength(0);
    wynik.append("Wyniki posortowane punktami\n");
    for (Student student : posortowaniStudenci) {
        int punktyStudenta = listaPunktów.get(listaStudentów.indexOf(student));
        int ocena = wezOcene(punktyStudenta);
        wynik.append(student.getImie()).append(" - ").append(punktyStudenta).append(" punktów").append(" Ocena: ").append(ocena).append("\n");
    }
    obszarNaWynik.setText(wynik.toString());
});

            powrotButton.addActionListener((ActionEvent e) -> {
                oknoNaWyniki.setVisible(false);
                });
        panelNaWyniki.add(przyciskNaSortowaniePunktami);
        panelNaWyniki.add(przyciskNaSortowanieImieniem);
        panelNaWyniki.add(powrotButton);
        oknoNaWyniki.getContentPane().add(panelNaWyniki);
        oknoNaWyniki.pack();
        oknoNaWyniki.setVisible(true);
        }
    }
    private int wezOcene(int punkty) {
    int ocena = 1;
    for (Map.Entry<Integer, int[]> entry : kryteriumOcen.entrySet()) {
        int zasięgOceny = entry.getKey();
        int[] zasięgPunktów = entry.getValue();
        int minimalnePunkty = zasięgPunktów[0];
        int maksymalnePunkty = zasięgPunktów[1];
        if (punkty >= minimalnePunkty && punkty <= maksymalnePunkty) {
            ocena = zasięgOceny;
            break;
        }
    }
    return ocena;
    }
    private void rozwiążTest() {
        if(nauczyciel == null){
            wyswietlBlad("Brak nauczyciela, który utworzy test");
            return;
        }
        if (test == null) {
            wyswietlBlad("Brak dostępnego testu");
            student = null;
            return;
        }
        String nazwaStudenta = JOptionPane.showInputDialog(this, "Podaj imie studenta");
        for(int i=0; i<listaStudentów.size(); i++){
            if(nazwaStudenta.equals(listaStudentów.get(i).getImie())){
                wyswietlInformacje("Nazwa już zajęta!");
                return;
            }
        }
        if (nazwaStudenta != null && !nazwaStudenta.isEmpty()) {
            student = new Student(nazwaStudenta);

            JPanel panelZPytaniami = new JPanel();
            panelZPytaniami.setLayout(new BoxLayout(panelZPytaniami, BoxLayout.Y_AXIS));

            JLabel labelNaImie = new JLabel("Student: " + nazwaStudenta);
            labelNaImie.setFont(new Font("Arial", Font.BOLD, 14));
            panelZPytaniami.add(labelNaImie);
            panelZPytaniami.add(Box.createRigidArea(new Dimension(0, 10)));

            JLabel labelNaTest = new JLabel("Test:");
            labelNaTest.setFont(new Font("Arial", Font.BOLD, 14));
            panelZPytaniami.add(labelNaTest);
            panelZPytaniami.add(Box.createRigidArea(new Dimension(0, 10)));

            List<Pytanie> pytania = test.getListaPytań();

            for (int i = 0; i < pytania.size(); i++) {
                Pytanie pytanie = pytania.get(i);

                JPanel labelNaPytaniaWPionie = new JPanel();
                labelNaPytaniaWPionie.setLayout(new BoxLayout(labelNaPytaniaWPionie, BoxLayout.Y_AXIS));

                JLabel labelNaPytanie = new JLabel("Pytanie " + (i + 1) + ":");
                labelNaPytanie.setFont(new Font("Arial", Font.BOLD, 12));
                labelNaPytaniaWPionie.add(labelNaPytanie);

                JLabel labelNaTreśćPytania = new JLabel(pytanie.getQuestionText());
                labelNaTreśćPytania.setFont(new Font("Arial", Font.PLAIN, 12));
                labelNaPytaniaWPionie.add(labelNaTreśćPytania);
                labelNaPytaniaWPionie.add(Box.createRigidArea(new Dimension(0, 5)));

                ButtonGroup przyciskiNaOdpowiedzi = new ButtonGroup();
                    for (String answer : pytanie.getOdpowiedzi()) {
                        JRadioButton przyciskNaOdpowiedz = new JRadioButton(answer);
                        przyciskNaOdpowiedz.setFont(new Font("Arial", Font.PLAIN, 12));
                        przyciskiNaOdpowiedzi.add(przyciskNaOdpowiedz);
                        labelNaPytaniaWPionie.add(przyciskNaOdpowiedz);

                        przyciskNaOdpowiedz.addActionListener((ActionEvent e) -> {
                            pytanie.setWybranaOdpowiedz(przyciskNaOdpowiedz.getText());
                        });

                        String wybranaOdpowiedz = pytanie.getWybranaOdpowiedz();
                        if (wybranaOdpowiedz.equals(answer)){
                            przyciskNaOdpowiedz.setSelected(true);
                        } else {
                            przyciskNaOdpowiedz.setSelected(false);
                        }
                    }
                labelNaPytaniaWPionie.add(Box.createRigidArea(new Dimension(0, 10)));
                panelZPytaniami.add(labelNaPytaniaWPionie);
            }
            JButton przyciskDoZapisania = new JButton("Zapisz");
            JButton przyciskDoPowrotu = new JButton("Powrót do menu");
            przyciskDoPowrotu.setEnabled(false);
            przyciskDoZapisania.addActionListener((ActionEvent e) -> {
                int wynik = obliczPunktyZaTest(test);
                zapiszWynikiStudentów(wynik, test.getListaNiepoprawnychOdpowiedzi());
                int ocena = wezOcene(wynik);
                wyswietlInformacje("Test rozwiązany. Wynik: " + wynik + " punktów. Ocena: " + ocena);
                listaStudentów.add(student);
                listaPunktów.add(wynik);
                panelZPytaniami.add(przyciskDoPowrotu);
                panelZPytaniami.revalidate();
                panelZPytaniami.repaint();
                przyciskDoZapisania.setEnabled(false);
                przyciskDoPowrotu.setEnabled(true);
            });
        
            przyciskDoPowrotu.addActionListener((ActionEvent e) -> {
                for (Pytanie pytanie : pytania) {
                pytanie.setWybranaOdpowiedz("");
                }
                główneOkienko();
            });
           panelZPytaniami.add(przyciskDoZapisania);
           panelZPytaniami.add(przyciskDoPowrotu);
           panelZPytaniami.revalidate();
           panelZPytaniami.repaint();
        
           JScrollPane scrollPane = new JScrollPane(panelZPytaniami);
           scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
           scrollPane.setPreferredSize(new Dimension(400, 300));
           setContentPane(scrollPane);
           revalidate();
           repaint();
        }        
    }
    private int obliczPunktyZaTest(Test test) {
        int wynik = 0;
        List<String> niepoprawneOdpowiedzi = new ArrayList<>();

        for (Pytanie pytanie : test.getListaPytań()) {
            String chosenAnswer = pytanie.getWybranaOdpowiedz();
            String correctAnswer = pytanie.getPoprawnaOdpowiedz();
            if (chosenAnswer.equals(correctAnswer)) {
                wynik += pytanie.getPunktyZaZadanie();
            } else {
                niepoprawneOdpowiedzi.add(pytanie.getQuestionText());
            }
        }

        test.setListaNiepoprawnychOdpowiedzi(niepoprawneOdpowiedzi);
        return wynik;
    }
    private void zapiszWynikiStudentów(int wynik, List<Pytanie> niepoprawneOdpowiedzi) {
        try {
            File plikDoZapisywaniaWynikow = new File(plikDoZapisywaniWyników);
            BufferedWriter zmiennaDoZapisu = new BufferedWriter(new FileWriter(plikDoZapisywaniaWynikow, true));
            zmiennaDoZapisu.write(wynik + ";");
            zmiennaDoZapisu.write(formatowanieNiepoprawnychOdpowiedzi(niepoprawneOdpowiedzi));
            zmiennaDoZapisu.newLine();
            zmiennaDoZapisu.close();
        } catch (IOException e) {
            wyswietlBlad("Błąd z zapisem danych.");
        }
    }
    private String formatowanieNiepoprawnychOdpowiedzi(List<Pytanie> niepoprawneOdpowiedzi) {
        StringBuilder formatowaneOdpowiedzi = new StringBuilder();
        for (Pytanie pytanie : niepoprawneOdpowiedzi) {
            formatowaneOdpowiedzi.append(pytanie.getId()).append(",");
        }
        return formatowaneOdpowiedzi.toString();
    }
    private void wyswietlBlad(String wiadomość) {
        JOptionPane.showMessageDialog(this, wiadomość, "Błąd", JOptionPane.ERROR_MESSAGE);
    }
    private void wyswietlInformacje(String wiadomosc) {
        JOptionPane.showMessageDialog(this, wiadomosc, "Information", JOptionPane.INFORMATION_MESSAGE);
    }
}