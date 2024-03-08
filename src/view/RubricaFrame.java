package view;

import main.Controller;

import javax.swing.*;

/**
 * Classe astratta comune a tutti i frame del programma Rubrica.
 */
public abstract class RubricaFrame extends JFrame {

    private static final long serialVersionUID = 9063144154659261496L;

	/**
     * I frame vengono costruiti con titolo, larghezza e altezza.
     * Inoltre vengono impostati posizione centrale, non resizable e icona.
     * @param titolo
     * @param width
     * @param height
     */
    public RubricaFrame(String titolo, int width, int height) {
        super(titolo);
        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(false);
        setIconImage(Controller.getInstance().getImage("icon.png").getImage());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }
}
