package testing;

import org.junit.Test;

public class SignInTest {

	@Test
	/**
	 * Signs in many users and disconnects them all at once to tests server's ability to 
	 * handle many clients as well as deleting them at once.
	 */
	public void signInTest() {
		TestClient will = new TestClient("will");
		TestClient anvisha = new TestClient("anvisha");
		TestClient jesika = new TestClient("jesika");
		TestClient jesika2 = new TestClient("jesika");
		will.start();
		anvisha.start();
		jesika.start();
		jesika2.start();
		
		will.signIn();
		anvisha.signIn();
		jesika.signIn();
		jesika2.signIn();
		for (int i = 0; i < 100; i++) {
			TestClient cli = new TestClient("testCli"+i);
			cli.start();
			cli.signIn();
		}
	}
	
}
