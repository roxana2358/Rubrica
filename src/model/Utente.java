package model;

import java.io.Serializable;

/**
 * Classe Utente con username e password.
 * Implementa Serializable per agevolare il salvataggio nel database.
 */
public class Utente implements Serializable {

	private static final long serialVersionUID = -2394236912073070649L;
	
	/**
     * Campi privati con le informazioni dell'utente.
     */
    private String username, password;

    /**
     * Costruttore di Utente.
     * @param name
     * @param password
     */
    public Utente(String name, String password) {
        this.username = name;
        this.password = password;
    }
    
    public Utente() {}

    public String getUsername() { return this.username; }
    public String getPassword() { return this.password; }

    public void setUsername(String username) { this.username = username; }
	public void setPassword(String password) { this.password = password; }

	/**
     * Override del metodo che restituisce l'istanza sotto forma di stringa.
     * @return
     */
    @Override
    public String toString() { return this.username+" "+this.password; }

    /**
     * Override del metodo equals per confrontare istanze di Utente.
     * @param o oggetto da confrontare
     * @return risultato del confronto
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Utente)) return false;

        Utente c = (Utente) o;
        return username.equals(c.username);
    }
}