package main;

import model.*;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * Classe controller che gestisce l'app Rubrica.
 */
public class Controller {

    /**
     * Unica istanza del controller.
     */
    private static Controller controller;

    /**
     * Frame di login.
     */
    private LoginFrame loginFrame;

    /**
     * Frame con la tabella dei contatti
     */
    private ContactsFrame contactsFrame;

    /**
     * Frame di modifica dei contatti.
     */
    private EditContactFrame editFrame;

    /**
     * Costruttore privato del controller.
     */
    private Controller() { }

    /**
     * Metodo che da accesso alla cartella con le icone.
     * @param fileName nome del file richiesto
     * @return
     */
    public ImageIcon getImage(String fileName) {
        return new ImageIcon(Toolkit.getDefaultToolkit().getImage(Controller.this.getClass().getClassLoader().getResource("assets/"+fileName)));
    }

    /**
     * Metodo che restituisce l'unico riferimento al controller.
     * @return unica istanza del controller
     */
    public static Controller getInstance(){
        if (controller == null) controller = new Controller();
        return controller;
    }

    /**
     * Metodo per avviare il programma Rubrica.
     */
    public void start(){
        this.loginFrame = new LoginFrame();
        loginFrame.setListener(new LoginButtonListener() {
            @Override
            public void listenButton(String instruction, String utente, String password) {
                if (utente.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(loginFrame,
                            "Devi inserire username E password per accedere!",
                            "Errore", JOptionPane.ERROR_MESSAGE);
                } else switch (instruction) {
                    case "login" -> login(utente, password);
                    case "registrati" -> registrati(utente, password);
                }
            }
        });
        loginFrame.setVisible(true);
    }

    /**
     * Metodo che permette di caricare i contatti di un utente preesistente.
     * @param utente
     * @param password
     */
    public void login(String utente, String password) {
        List<Utente> utenti = RubricaBusiness.getInstance().getUtenti();    // recupero lista di utenti
        Utente loginU = new Utente(utente, password);
        int i = utenti.indexOf(loginU);          // controllo se l'utente è presente
        if (i!=-1) {                             // utente esiste
        	RubricaBusiness.getInstance().setConnesso(utenti.get(i));
            if (RubricaBusiness.getInstance().getConnesso().getPassword().equals(password)) {   // password corretta
                loginFrame.dispose();                           								// nascondi finestra login
                showContacts();                                 								// mostra rubrica
            } else {                                            								// password errata
                JOptionPane.showMessageDialog(loginFrame,
                        "La password inserita non è corretta!",
                        "Errore!", JOptionPane.ERROR_MESSAGE);
            }
        } else {                                // utente non esiste
            JOptionPane.showMessageDialog(loginFrame,
                    "Utente non trovato: prova a creare un nuovo profilo.",
                    "Attenzione!", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Metodo che permette di registrare un nuovo utente.
     * @param utente
     * @param password
     */
    public void registrati(String utente, String password) {
        List<Utente> utenti = RubricaBusiness.getInstance().getUtenti();   // recupero lista di utenti
        Utente loginU = new Utente(utente, password);
        int i = utenti.indexOf(loginU);         // controllo se l'utente è presente
        if (i!=-1) {                            // username presente nel db
            JOptionPane.showMessageDialog(loginFrame,
                    "Utente presente nel database: prova a fare il login.",
                    "Attenzione!", JOptionPane.WARNING_MESSAGE);
        } else {
            RubricaBusiness.getInstance().addUtente(loginU);
            login(utente, password);
        }
    }

    /**
     * Metodo che permette di passare al frame con i contatti.
     */
    public void showContacts() {
        contactsFrame = new ContactsFrame();
        contactsFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (JOptionPane.showOptionDialog(contactsFrame, "Uscire dall'applicazione?",
                        "Logout", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, new String[]{"Sì", "No"}, JOptionPane.NO_OPTION)==JOptionPane.YES_OPTION) {
                    contactsFrame.dispose();
                    if (editFrame!=null && editFrame.isShowing()) editFrame.dispose();
                    start();
                }
            }
        });
        contactsFrame.setToolbarListener(new ButtonListener() {
            @Override
            public void listenButton(String instruction) {
                switch (instruction) {
                    case "new" -> addContact();
                    case "edit" -> {
                        if (contactsFrame.getSelectedRow()==-1)
                            JOptionPane.showMessageDialog(contactsFrame,
                                    "Non è stato selezionato un contatto!",
                                    "Attenzione!", JOptionPane.WARNING_MESSAGE);
                        else editContact();
                    }
                    case "delete" -> {
                        if (contactsFrame.getSelectedRow()==-1) {
                            JOptionPane.showMessageDialog(contactsFrame,
                                    "Non è stato selezionato un contatto!",
                                    "Attenzione!", JOptionPane.WARNING_MESSAGE);
                        } else {
                            Contatto p = RubricaBusiness.getInstance().getContatti().get(contactsFrame.getSelectedRow());
                            if (JOptionPane.showOptionDialog(contactsFrame,
                                    "Eliminare "+p.getNome().toUpperCase()+" "+p.getCognome().toUpperCase()+"?",
                                    "Confermi?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[]{"Sì", "No"}, JOptionPane.NO_OPTION) == JOptionPane.YES_OPTION)
                                removeContact();
                        }
                    }
                }
            }
        });
        contactsFrame.setVisible(true);
    }

    /**
     * Metodo che apre un frame di modifica dei contatti.
     * @param p
     */
    private void openEdit(Contatto p) {
        editFrame = new EditContactFrame(p);
        editFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (JOptionPane.showOptionDialog(editFrame, "Vuoi annullare le modifiche?",
                        "Confermi?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, new String[]{"Sì", "No"}, JOptionPane.NO_OPTION)==JOptionPane.YES_OPTION)
                    editFrame.dispose();
            }
        });
        editFrame.setSbl(new SaveButtonListener() {
            @Override
            public void listenButton(Contatto newContatto) {
                if (newContatto.getCognome().isEmpty() || newContatto.getNome().isEmpty() ||
                		newContatto.getTelefono().isEmpty() || newContatto.getIndirizzo().isEmpty() ||
                		newContatto.getEta()==Integer.MIN_VALUE )
                    JOptionPane.showMessageDialog(editFrame,
                            "Devi compilare tutti i campi correttamente!",
                            "Errore!", JOptionPane.ERROR_MESSAGE);
                else {
                    if (p==null) RubricaBusiness.getInstance().addContatto(newContatto);
                    else {
                    	newContatto.setId(p.getId());
                    	RubricaBusiness.getInstance().updateContatto(newContatto);
                    }
                    editFrame.dispose();
                }
            }
        });
        editFrame.setBp(new ButtonPusher() {
            @Override
            public void pushButton() {
                if (JOptionPane.showOptionDialog(editFrame, "Vuoi annullare le modifiche?",
                        "Confermi?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, new String[]{"Sì", "No"}, JOptionPane.NO_OPTION)==JOptionPane.YES_OPTION)
                    editFrame.dispose();
            }
        });
        editFrame.setVisible(true);
    }

    /**
     * Metodo che permette di aggiungere un nuovo contatto.
     */
    public void addContact() {
        openEdit(null);
    }

    /**
     * Metodo che permette la modifica di un contatto.
     */
    public void editContact() {
        openEdit(RubricaBusiness.getInstance().getContatti().get(contactsFrame.getSelectedRow()));
    }

    /**
     * Metodo che permette di eliminare un contatto.
     */
    public void removeContact() {
    	RubricaBusiness.getInstance().deleteContatto(
    			RubricaBusiness.getInstance().getContatti().get(contactsFrame.getSelectedRow()));
    }
}