package multithreading;

public interface ThreadListener {

	void onStart();
	void onError();
	void onCleanup();
	void onStop();
	void onFinish();
	void onUpdate();
	void onRestart();
}
