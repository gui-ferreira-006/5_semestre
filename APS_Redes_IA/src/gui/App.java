package gui;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;

public class App {

    public static void main (String[] args) {

        //Inicia a TelaServidor em modo escuro
        new Thread (() -> {
            FlatDarkLaf.setup();
            SwingUtilities.invokeLater(() -> {
                TelaServidor tela = new TelaServidor ();
                tela.setVisible (true);
                tela.iniciarServidor();
            });
        }).start();
    }
}