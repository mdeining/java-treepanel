package samples;

public class Client {
	
	private Service service = new Service();
	
	private void call(){
		Object obj = Client.class;
		service.service((Class<?>)obj);
	}

	public static void main(String[] args) {
		Client client = new Client();
		client.call();
	}

}
