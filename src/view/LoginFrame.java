package view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

/**
 * Frame di login.
 * Contiene al suo interno una classe per i componenti del frame: LoginPanel.
 */
public class LoginFrame extends RubricaFrame {

    private static final long serialVersionUID = -4323759461245695036L;
    
	/**
     * Unico panel del frame.
     */
    private LoginPanel loginPanel;

    /**
     * Costruttore del frame.
     * La sua chiusura provoca la terminazione del programma.
     */
    public LoginFrame() {
        super("Login", 400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginPanel = new LoginPanel();
        add(loginPanel, BorderLayout.CENTER);
    }

    /**
     * Metodo per assegnare il comportamento del panel.
     * @param listener
     */
    public void setListener(LoginButtonListener listener) { loginPanel.setListener(listener); }

    /**
     * Classe privata del panel.
     */
    private class LoginPanel extends Panel implements ActionListener {

        private static final long serialVersionUID = 397124901331321947L;
        
		/**
         * Campi privati del panel.
         */
        private JTextField usernameField;
        private JPasswordField passwordField;
        private JButton login, signUp;
        private JCheckBox checkBox;
        private LoginButtonListener listener;

        /**
         * Costruttore del Panel.
         */
        public LoginPanel() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            Color backgroundColor = new Color(66, 133,  244);
            Color buttonColor = new Color(18, 27, 196, 255);
            Color buttonText = Color.white;
            Font font = new Font(Font.SANS_SERIF, Font.BOLD , 14);

            // parte del panel con i campi
            JPanel fields = new JPanel();
            fields.setLayout(new GridLayout(6,1));
            fields.setBackground(backgroundColor);
            fields.setBorder(BorderFactory.createLineBorder(backgroundColor, 25));
            int alignment = SwingConstants.LEFT;
            Border outerBorder = BorderFactory.createEmptyBorder();
            Border innerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
            // username field
            JLabel usernameLabel = new JLabel("Username");
            usernameLabel.setHorizontalAlignment(alignment);
            usernameLabel.setFont(font);
            fields.add(usernameLabel);
            usernameField = new JTextField();
            usernameField.setHorizontalAlignment(alignment);
            usernameField.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
            fields.add(usernameField);
            // padding
            fields.add(Box.createRigidArea(new Dimension(2,2)));
            // password field
            JLabel passwordLabel = new JLabel("Password");
            passwordLabel.setHorizontalAlignment(alignment);
            passwordLabel.setFont(font);
            fields.add(passwordLabel);
            passwordField = new JPasswordField();
            passwordField.setHorizontalAlignment(alignment);
            passwordField.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
            fields.add(passwordField);
            // show password checkbox
            checkBox = new JCheckBox("Mostra password");
            checkBox.setHorizontalAlignment(alignment);
            checkBox.addActionListener(this);
            checkBox.setFocusable(false);
            checkBox.setBackground(backgroundColor);
            checkBox.setFont(new Font(Font.DIALOG, Font.ITALIC, 12));
            fields.add(checkBox);
            add(fields);

            // parte del panel con i button
            JPanel buttons = new JPanel();
            buttons.setLayout(new GridLayout(1,2,10, 0));
            buttons.setBackground(backgroundColor);
            buttons.setBorder(BorderFactory.createLineBorder(backgroundColor, 25));
            Border bl1 = BorderFactory.createRaisedBevelBorder();
            Border bl2 = BorderFactory.createLoweredBevelBorder();
            MouseAdapter ma = new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (e.getSource().equals(login)) login.setBorder(bl2);
                    else signUp.setBorder(bl2);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (e.getSource().equals(login)) login.setBorder(bl1);
                    else signUp.setBorder(bl1);
                }
            };
            // login button
            login = new JButton("Login");
            login.setFont(font);
            login.addActionListener(this);
            login.setBackground(buttonColor);
            login.setFocusable(false);
            login.setBorder(bl1);
            login.setForeground(buttonText);
            login.addMouseListener(ma);
            buttons.add(login);
            // registrati button
            signUp = new JButton("Crea nuovo");
            signUp.setFont(font);
            signUp.addActionListener(this);
            signUp.setBackground(buttonColor);
            signUp.setFocusable(false);
            signUp.setBorder(bl1);
            signUp.setForeground(buttonText);
            signUp.addMouseListener(ma);
            buttons.add(signUp);
            add(buttons);
        }

        /**
         * Metodo che permette di personalizzare la risposta del panel.
         * @param listener
         */
        public void setListener(LoginButtonListener listener) { this.listener = listener; }

        /**
         * Override del metodo che assegna il comportamento.
         * @param e l'evento che scatena l'azione
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            Object pushed = e.getSource();
            if (pushed.equals(checkBox)) {
                if (checkBox.isSelected()) passwordField.setEchoChar((char)0);
                else passwordField.setEchoChar('•');
            } else if (listener!=null) {
                if (pushed.equals(login)) {
                    listener.listenButton("login",
                            usernameField.getText(),
                            String.valueOf(passwordField.getPassword()));
                } else if (pushed.equals(signUp)) {
                    listener.listenButton("registrati",
                            usernameField.getText(),
                            String.valueOf(passwordField.getPassword()));
                }
            }
        }
    }
}
