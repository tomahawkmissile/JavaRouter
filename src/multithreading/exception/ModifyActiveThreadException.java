package multithreading.exception;

public class ModifyActiveThreadException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -859339209322298381L;

	public ModifyActiveThreadException() {
		super("Active threads cannot be modified.");
	}
	public ModifyActiveThreadException(String msg) {
		super(msg);
	}
}
