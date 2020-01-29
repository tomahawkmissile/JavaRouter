package multithreading.exception;

public final class ThreadInstantiationException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8944072150021321123L;

	public ThreadInstantiationException() {
		super("Thread has already been instantiated!");
	}
	public ThreadInstantiationException(String msg) {
		super(msg);
	}
}
