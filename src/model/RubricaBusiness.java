package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import view.Observer;

public class RubricaBusiness implements Observable{

	/**
	 * Campo per la connessione al database.
	 */
	private Connection con;
	
	/**
	 * Campo per l'unica istanza della classe.
	 */
	private static RubricaBusiness rb;
	
	/**
     * Unico Observer.
     */
    private Observer observer;
	
	/**
	 * Campo per l'utente connesso.
	 */
	private Utente connesso;
	
	public Utente getConnesso() { return connesso; }

	public void setConnesso(Utente connesso) { this.connesso = connesso; }

	/**
	 * Costruttore privato della classe.
	 */
	private RubricaBusiness() {}
	
	/**
     * Metodo per recuperare l'unica istanza della classe.
     * @return unica istanza della classe
     */
	public static RubricaBusiness getInstance() {
		if (rb == null) rb = new RubricaBusiness();
		return rb;
	}

	/**
	 * Metodo che stabilisce la connessione al database per un utente preesistente.
	 * @return 
	 * @throws SQLException
	 */
	private Connection getConnection() throws SQLException {
		if (con==null) {
			String URL = "jdbc:mysql://localhost:3306/rubrica";
			String user = "roxana";
			String password = "password";
			con = DriverManager.getConnection(URL, user, password);
		}
		return con;
	}

	/**
     * Metodo che restituisce la lista di utenti.
     * @return lista di utenti presenti nel database
     */
    public List<Utente> getUtenti() { 
    	String sql = "SELECT * FROM utenti";
		
		List<Utente> utenti = new ArrayList<Utente>();
		
		try {
			PreparedStatement ps = getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
	
			while (rs.next()) {
				Utente u = new Utente();
				u.setUsername(rs.getString(1));
				u.setPassword(rs.getString(2));
				utenti.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return utenti;
    }
    
    /**
     * Metodo che permette di aggiungere un utente.
     * @param u utente da aggiungere
     */
    public void addUtente(Utente u) {
    	String sql = "INSERT INTO utenti(username, password) VALUES (?, ?)";
		
		try {
			PreparedStatement ps = getConnection().prepareStatement(sql);
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getPassword());
			
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    /**
	 * Metodo che restituisce i contatti dell'utente connesso.
	 * @return lista di contatti dell'utente connesso
	 */
	public List<Contatto> getContatti() {
		String sql = "SELECT id, nome, cognome, telefono, indirizzo, eta FROM contatti WHERE utente_ass=?";
		
		List<Contatto> contatti = new ArrayList<Contatto>();
		
		try {
			PreparedStatement ps = getConnection().prepareStatement(sql);
			ps.setString(1, connesso.getUsername());
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Contatto p = new Contatto();
				p.setId(rs.getInt(1));
				p.setNome(rs.getString(2));
				p.setCognome(rs.getString(3));
				p.setTelefono(rs.getString(4));
				p.setIndirizzo(rs.getString(5));
				p.setEta(rs.getInt(6));
				
				contatti.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return contatti;
	}
	
	/**
	 * Metodo che permette di aggiungere un contatto.
	 * @param c contatto da aggiungere
	 * @return id del contatto aggiunto
	 */
	public void addContatto(Contatto c) {
		String sql = "INSERT INTO contatti(nome, cognome, telefono, indirizzo, eta, utente_ass) VALUES (?, ?, ?, ?, ?, ?)";
		
		try {
			PreparedStatement ps = getConnection().prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, c.getNome());
			ps.setString(2, c.getCognome());
			ps.setString(3, c.getTelefono());
			ps.setString(4, c.getIndirizzo());
			ps.setInt(5, c.getEta());
			ps.setString(6, connesso.getUsername());
			
			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			
			notifyObserver();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Permette di rimuovere i dati di un contatto.
	 * @param c contatto da eliminare
	 */
	public void deleteContatto(Contatto c) {
		String sql = "DELETE FROM contatti WHERE id=?";
		
		try {
			PreparedStatement ps = getConnection().prepareStatement(sql);
			ps.setInt(1, c.getId());
			ps.executeUpdate();
			
			notifyObserver();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Permette di modificare i dati di un contatto.
	 * @param c contatto da modificare
	 */
	public void updateContatto(Contatto c) {
		String sql = "UPDATE contatti SET nome=?, cognome=?, telefono=?, indirizzo=? WHERE id=?";
		
		try {
			PreparedStatement ps = getConnection().prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, c.getNome());
			ps.setString(2, c.getCognome());
			ps.setString(3, c.getTelefono());
			ps.setString(4, c.getIndirizzo());
			ps.setInt(5, c.getId());
			
			ps.executeUpdate();
			
			notifyObserver();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
     * Override del metodo che assegna l'Osserver.
     * @param observer
     */
    @Override
    public void setObserver(Observer observer) { this.observer = observer; }

    /**
     * Override del metodo per notificare l'Osserver.
     */
    @Override
    public void notifyObserver() { observer.update(); }
}
