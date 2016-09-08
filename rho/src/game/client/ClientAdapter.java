package game.client;

public interface ClientAdapter {	

	void end();

	void send(String protocoll, String data);

	void update(String[] json);

	void setup(String[] message);
	
}
