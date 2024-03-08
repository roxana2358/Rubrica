package view;

import model.Contatto;

/**
 * Interfaccia che permette di salvare un nuovo contatto nella rubrica.
 */
public interface SaveButtonListener {
    void listenButton(Contatto newPersona);
}
