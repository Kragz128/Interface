package aaaa;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> napraviProzor());
    }

    private static void napraviProzor() {
        RasporedIspita raspored = new RasporedIspita();

        JFrame prozor = new JFrame("Evidencija ispitnih termina");
        prozor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        prozor.setSize(500, 450);
        prozor.setLayout(new BorderLayout(10, 10));

        // Polja za unos podataka o terminu
        JTextField predmet = new JTextField();
        JTextField sifra = new JTextField();
        JTextField datum = new JTextField();
        JTextField sala = new JTextField();
        JTextField brojStudenata = new JTextField();

        JPanel forma = new JPanel(new GridLayout(5, 2, 8, 8));
        forma.add(new JLabel("Naziv predmeta:"));
        forma.add(predmet);
        forma.add(new JLabel("Šifra termina:"));
        forma.add(sifra);
        forma.add(new JLabel("Datum:"));
        forma.add(datum);
        forma.add(new JLabel("Oznaka sale:"));
        forma.add(sala);
        forma.add(new JLabel("Broj prijavljenih studenata:"));
        forma.add(brojStudenata);

        // Polje za pretragu po šifri
        JTextField sifraPretrage = new JTextField(12);
        JPanel pretraga = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pretraga.add(new JLabel("Šifra za pretragu:"));
        pretraga.add(sifraPretrage);

        JPanel gornjiDeo = new JPanel(new BorderLayout());
        gornjiDeo.add(forma, BorderLayout.CENTER);
        gornjiDeo.add(pretraga, BorderLayout.SOUTH);

        JTextArea prikaz = new JTextArea();
        prikaz.setEditable(false);

        JButton dodajDugme = new JButton("Dodaj termin");
        JButton pretraziDugme = new JButton("Pretraži");
        JButton prikaziSveDugme = new JButton("Prikaži sve");

        JPanel dugmad = new JPanel(new GridLayout(1, 3, 5, 5));
        dugmad.add(dodajDugme);
        dugmad.add(pretraziDugme);
        dugmad.add(prikaziSveDugme);

        // Dodaje termin u raspored
        dodajDugme.addActionListener(e -> {
            try {
                int broj = Integer.parseInt(brojStudenata.getText());

                IspitniTermin termin = new IspitniTermin(
                        predmet.getText(),
                        sifra.getText(),
                        datum.getText(),
                        sala.getText(),
                        broj
                );

                raspored.dodajTermin(termin);
                prikaz.setText("Termin je dodat:\n" + termin);

            } catch (NumberFormatException greska) {
                JOptionPane.showMessageDialog(
                        prozor,
                        "Broj prijavljenih studenata mora biti ceo broj."
                );
            }
        });

        // Traži termin po šifri
        pretraziDugme.addActionListener(e -> {
            IspitniTermin termin =
                    raspored.nadjiTermin(sifraPretrage.getText());

            if (termin == null) {
                prikaz.setText("Termin nije pronađen.");
            } else {
                prikaz.setText("Pronađen termin:\n" + termin);
            }
        });

        // Prikazuje sve unete termine
        prikaziSveDugme.addActionListener(e -> {
            StringBuilder tekst = new StringBuilder();

            for (IspitniTermin termin : raspored.getTermini()) {
                tekst.append(termin).append("\n\n");
            }

            if (tekst.length() == 0) {
                prikaz.setText("Još nema unetih termina.");
            } else {
                prikaz.setText(tekst.toString());
            }
        });

        prozor.add(gornjiDeo, BorderLayout.NORTH);
        prozor.add(new JScrollPane(prikaz), BorderLayout.CENTER);
        prozor.add(dugmad, BorderLayout.SOUTH);

        prozor.setLocationRelativeTo(null);
        prozor.setVisible(true);
    }
}