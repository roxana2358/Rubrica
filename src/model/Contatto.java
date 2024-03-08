package model;

import java.io.Serializable;

/**
 * Classe Contatto con le informazioni del contatto.
 * Implementa Serializable per agevolare il salvataggio nel database.
 */
public class Contatto implements Serializable {

	private static final long serialVersionUID = -1405271171908798612L;
	
	/**
     * Campi privati con le informazioni del contatto.
     */
    private String nome, cognome, indirizzo, telefono;
    private int id, eta;

	/**
     * Costruttore di Contatto con argomenti.
     * @param nome
     * @param cognome
     * @param telefono
     * @param indirizzo
     * @param eta
     */
    public Contatto(String nome, String cognome, String telefono, String indirizzo, int eta) {
        this.nome = nome;
        this.cognome = cognome;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
        this.eta = eta;
    }
    
    /**
     * Costruttore di Contatto senza argomenti.
     */
    public Contatto() {}

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getCognome() { return cognome; }
    public String getTelefono() { return telefono; }
    public String getIndirizzo() { return indirizzo; }
    public int getEta() { return eta; }

    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCognome(String cognome) { this.cognome = cognome; }
    public void setIndirizzo(String indirizzo) { this.indirizzo = indirizzo; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setEta(int eta) { this.eta = eta; }

    /**
     * Override del metodo che restituisce l'istanza sotto forma di stringa.
     * @return
     */
    @Override
    public String toString(){ return nome+";"+cognome+";"+telefono+";"+indirizzo+";"+eta; }
}