package multithreading;

import java.util.ArrayList;
import java.util.List;

import main.Registry;
import multithreading.exception.ModifyActiveThreadException;
import multithreading.exception.ThreadInstantiationException;

public abstract class DefaultThread extends Thread {
	
	private List<ThreadListener> listeners = new ArrayList<>();
	public void addListener(ThreadListener l) {
		listeners.add(l);
	}
	protected void notifyOnStart() {
		for(ThreadListener l:listeners) {
			l.onStart();
		}
	}
	protected void notifyOnError() {
		for(ThreadListener l:listeners) {
			l.onError();
		}
	}
	protected void notifyOnCleanup() {
		for(ThreadListener l:listeners) {
			l.onCleanup();
		}
	}
	protected void notifyOnStop() {
		for(ThreadListener l:listeners) {
			l.onStop();
		}
	}
	protected void notifyOnFinish() {
		for(ThreadListener l:listeners) {
			l.onFinish();
		}
	}
	protected void notifyOnUpdate() {
		for(ThreadListener l:listeners) {
			l.onUpdate();
		}
	}
	protected void notifyOnRestart() {
		for(ThreadListener l:listeners) {
			l.onRestart();
		}
	}
	
	volatile boolean alive=false;
	
	Thread t = null;
	
	final String name;
	
	protected DefaultThread(String name) throws ModifyActiveThreadException {
		if(alive) {
			throw new ModifyActiveThreadException();
		} else {
			this.name=name;
		}
	}
	
	public synchronized void initialize() throws ThreadInstantiationException {
		if(alive) {
			try {
				throw new ThreadInstantiationException("Thread has already been instantiated.");
			} catch (ThreadInstantiationException e) {
				e.printStackTrace();
				throw e;
			}
		}
		this.notifyOnStart();
	}
	protected void loop() {
		
		this.notifyOnUpdate();
	}
	protected void cleanup() {
		this.notifyOnCleanup();
	}
	
	volatile long millis;
	
	protected synchronized final void setDelay(long millis) {
		this.millis=millis;
	}
	@Override
	public final void start() {
		if(t==null) {
			this.notifyOnStart();
			alive=true;
			t = new Thread() {
				@Override
				public void run() {
					try {
						initialize();
						while(alive) {
							loop();
							try {
								Thread.sleep(millis);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						cleanup();
						notifyOnFinish();
					} catch (ThreadInstantiationException e1) {
						e1.printStackTrace();
					}
				}
			};
			t.setDaemon(true);
			t.start();
		}
	}
	public synchronized final boolean shutdown() {
		if((alive=false)==false) {
			this.notifyOnStop();
			return true;
		}
		return false;
	}
	public synchronized void restart() {
		Thread re = new Thread() {
			@Override
			public void run() {
				try {
					shutdown();
					t.join();
					cleanup();
					t=null;
					Thread.sleep(1);
					start();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		re.setDaemon(true);
		re.start();
		this.notifyOnRestart();
	}
	public synchronized final void registerThread(Registry r) {
		r.addRegister("threads."+name, t);
	}
}
