package view;

import main.Controller;
import model.Contatto;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Frame di modifica dei contatti.
 * Contiene al suo interno due classi per le componenti interne del frame: EditContactPanel e MyToolbarEdit.
 */
public class EditContactFrame extends SmallFrame {

    private static final long serialVersionUID = 8148753072808585931L;
    
	/**
     * Campi del frame.
     */
    private MyToolbarEdit toolBar;
    private EditContactPanel ecp;

    /**
     * Costruttore del frame.
     * @param p
     */
    public EditContactFrame(Contatto p) {
        super("Modifica contatto");

        // toolbar con le opzioni
        toolBar = new MyToolbarEdit();
        add(toolBar, BorderLayout.NORTH);

        // panel con i dati
        ecp = new EditContactPanel(p);
        add(ecp, BorderLayout.CENTER);
    }

    /**
     * Metodo che assegna il comportamento al toolbar (salvataggio).
     * @param sbl
     */
    public void setSbl(SaveButtonListener sbl) { toolBar.setSbl(sbl); }

    /**
     * Metodo che assegna il comportamento al toolbar (chiusura).
     * @param bp
     */
    public void setBp(ButtonPusher bp) { toolBar.setBp(bp); }

    /**
     * Classe privata del toolbar.
     */
    private class MyToolbarEdit extends JToolBar implements ActionListener {

        private static final long serialVersionUID = -6768027454281761485L;
        
		/**
         * Campi privati del toolbar.
         */
        private JButton saveB, cancelB;
        private SaveButtonListener sbl;
        private ButtonPusher bp;

        /**
         * Costruttore del toolbar.
         */
        public MyToolbarEdit() {
            setFloatable(false);
            setPreferredSize(new Dimension(getWidth(), 40));
            setLayout(new BorderLayout());

            saveB = new JButton(processIcon(Controller.getInstance().getImage("save.png")));
            cancelB = new JButton(processIcon(Controller.getInstance().getImage("cancel.png")));

            saveB.addActionListener(this);
            cancelB.addActionListener(this);

            saveB.setFocusable(false);
            cancelB.setFocusable(false);

            add(saveB, BorderLayout.LINE_START);
            add(cancelB, BorderLayout.LINE_END);
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
         * Metodo per assegnare il comportamento al toolbar (salvataggio).
         * @param sbl
         */
        public void setSbl(SaveButtonListener sbl) { this.sbl = sbl; }

        /**
         * Metodo per assegnare il comportamento al toolbar (chiusura).
         * @param bp
         */
        public void setBp(ButtonPusher bp) { this.bp = bp; }

        /**
         * Metodo chiamato quando viene usato il toolbar.
         * @param e l'evento da gestire
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton pushed = (JButton) e.getSource();
            if (pushed.equals(saveB)) {
                sbl.listenButton(EditContactFrame.this.ecp.getFields());
            } else if (pushed.equals(cancelB)) {
                bp.pushButton();
            }
        }
    }

    /**
     * Classe privata del panel.
     */
    private class EditContactPanel extends JPanel {

        private static final long serialVersionUID = -2466279557696133698L;
        
		/**
         * Campi privati del panel.
         */
        private JTextField nome, cognome, telefono, indirizzo, eta;
        private boolean isNew = true;
        private Contatto contatto;

        /**
         * Costruttore del panel. Contiene un form.
         * @param p contatto da mostrare per la modifica
         */
        public EditContactPanel(Contatto p) {
            if (p!=null) {
                this.isNew = false;
                this.contatto = p;
            }
            Color backgroundColor = new Color(251, 188, 5, 255);
            Font font = new Font(Font.SANS_SERIF, Font.BOLD , 14);
            Border outerBorder = BorderFactory.createEmptyBorder();
            Border innerBorder = BorderFactory.createEmptyBorder(3, 3, 3, 3);
            int alignment = SwingConstants.LEFT;
            setLayout(new GridLayout(5,2, 0, 25));
            setBackground(backgroundColor);
            setBorder(BorderFactory.createLineBorder(backgroundColor, 25));

            // nome
            JLabel nameLabel = new JLabel("Nome:");
            nameLabel.setHorizontalAlignment(alignment);
            nameLabel.setFont(font);
            nome = new JTextField();
            nome.setHorizontalAlignment(alignment);
            nome.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
            if (!isNew) nome.setText(contatto.getNome());
            add(nameLabel);
            add(nome);

            // cognome
            JLabel surnameLabel = new JLabel("Cognome:");
            surnameLabel.setHorizontalAlignment(alignment);
            surnameLabel.setFont(font);
            cognome = new JTextField();
            cognome.setHorizontalAlignment(alignment);
            cognome.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
            if (!isNew) cognome.setText(contatto.getCognome());
            add(surnameLabel);
            add(cognome);

            // telefono
            JLabel telLabel = new JLabel("Telefono:");
            telLabel.setHorizontalAlignment(alignment);
            telLabel.setFont(font);
            telefono = new JTextField();
            telefono.setHorizontalAlignment(alignment);
            telefono.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
            if (!isNew) telefono.setText(contatto.getTelefono());
            add(telLabel);
            add(telefono);

            // indirizzo
            JLabel addressLabel = new JLabel("Indirizzo:");
            addressLabel.setHorizontalAlignment(alignment);
            addressLabel.setFont(font);
            indirizzo = new JTextField();
            indirizzo.setHorizontalAlignment(alignment);
            indirizzo.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
            if (!isNew) indirizzo.setText(contatto.getIndirizzo());
            add(addressLabel);
            add(indirizzo);

            // eta
            JLabel ageLabel = new JLabel("Età:");
            ageLabel.setHorizontalAlignment(alignment);
            ageLabel.setFont(font);
            eta = new JTextField();
            eta.setHorizontalAlignment(alignment);
            eta.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
            if (!isNew) eta.setText(contatto.getEta()+"");
            add(ageLabel);
            add(eta);
        }

        /**
         * Metodo che permette di ottenere i valori nel form.
         * @return
         */
        public Contatto getFields() {
            try {
                return new Contatto(nome.getText(), cognome.getText(), telefono.getText(), indirizzo.getText(), Integer.parseInt(eta.getText()));
            } catch (Exception e) {
                return new Contatto(nome.getText(), cognome.getText(), telefono.getText(), indirizzo.getText(), Integer.MIN_VALUE);
            }
        }
    }
}
