package view;

/**
 * Classe astratta per i frame piccoli.
 */
public abstract class SmallFrame extends RubricaFrame {
	
    private static final long serialVersionUID = 8845441666424009345L;

	/**
     * I frame vengono costruiti con titolo.
     * @param titolo
     */
    public SmallFrame(String titolo) {
        super(titolo, 350, 350);
    }
}
