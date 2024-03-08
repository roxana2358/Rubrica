package view;

import main.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Frame di visualizzazione dei contatti.
 * Contiene al suo interno la classe per il componente MyToolbarHome.
 */
public class ContactsFrame extends BigFrame {

    private static final long serialVersionUID = -7726156965803792110L;
    
	/**
     * Campi del frame.
     */
    private MyToolbarHome toolBar;
    private MyTable table;

    /**
     * Costruttore del frame.
     */
    public ContactsFrame() {
        super("Rubrica");

        // tootbar con le opzioni
        toolBar = new MyToolbarHome();
        add(toolBar, BorderLayout.NORTH);

        // tabella con i contatti
        table = new MyTable();
        add(table, BorderLayout.CENTER);
    }

    /**
     * Restituisce l'indice del contatto selezionato.
     * @return
     */
    public int getSelectedRow() { return table.getSelectedRow(); }

    /**
     * Metodo per assegnare il comportamento al panel.
     * @param listener
     */
    public void setToolbarListener(ButtonListener listener) { toolBar.setListener(listener);}

    /**
     * Classe privata del toolbar.
     */
    private class MyToolbarHome extends JToolBar implements ActionListener {

        private static final long serialVersionUID = -8384509918807652013L;
        
		/**
         * Campi del toolbar.
         */
        private JButton newB, editB, delB;
        private ButtonListener listener;

        /**
         * Costruttore del toolbar.
         */
        public MyToolbarHome() {
            setFloatable(false);
            setPreferredSize(new Dimension(getWidth(), 40));
            setLayout(new BorderLayout());

            newB = new JButton(processIcon(Controller.getInstance().getImage("new.png")));
            editB = new JButton(processIcon(Controller.getInstance().getImage("edit.png")));
            delB = new JButton(processIcon(Controller.getInstance().getImage("delete.png")));

            newB.addActionListener(this);
            editB.addActionListener(this);
            delB.addActionListener(this);

            newB.setFocusable(false);
            editB.setFocusable(false);
            delB.setFocusable(false);

            add(newB, BorderLayout.LINE_START);
            add(editB, BorderLayout.CENTER);
            add(delB, BorderLayout.LINE_END);
        }

        /**
         * Metodo che restituisce il resize dell'icona.
         * @param img icona da ridimensionare
         * @return
         */
        private ImageIcon processIcon(ImageIcon img) {
            return new ImageIcon(img.getImage().getScaledInstance(28,28, Image.SCALE_SMOOTH));
        }

        /**
         * Metodo per assegnare il comportamento al toolbar.
         * @param listener
         */
        public void setListener(ButtonListener listener) { this.listener = listener; }

        /**
         * Metodo chiamato quando viene usato il toolbar.
         * @param e l'evento da gestire
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton pushed = (JButton) e.getSource();
            if (listener!=null) {
                if (pushed.equals(newB)) {
                    listener.listenButton("new");
                } else if (pushed.equals(editB)) {
                    listener.listenButton("edit");
                } else if (pushed.equals(delB)) {
                    listener.listenButton("delete");
                }
            }
        }
    }
}
