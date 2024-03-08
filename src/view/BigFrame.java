package view;

/**
 * Classe astratta per i frame grandi.
 */
public abstract class BigFrame extends RubricaFrame {
	
    private static final long serialVersionUID = 8571536127407252241L;

	/**
     * I frame vengono costruiti con titolo.
     * @param titolo
     */
    public BigFrame(String titolo) {
        super(titolo, 500, 680);
    }
}
