package testing;

import org.junit.Test;

public class StartConversationTest {

	
	@Test
	/**
	 * Signs in many clients and creates many conversations. Then deletes them all at once
	 * to show that the server can handle many clients and conversations at a time.
	 */
	public void testStartConversation() {
	    TestClient will = new TestClient("will");
	    TestClient anvisha = new TestClient("anvisha");
	    TestClient jesika = new TestClient("jesika");
	    TestClient joe = new TestClient("joe");
		will.start();
		anvisha.start();
		jesika.start();
        joe.start();
		will.signIn();
		anvisha.signIn();
		jesika.signIn();
		joe.signIn();
		will.sendToServer("CREQ SENDER will RCPT anvisha");
		anvisha.sendToServer("CREQ SENDER anvisha RCPT jesika");
		jesika.sendToServer("CREQ SENDER jesika RCPT will");
		joe.sendToServer("CREQ SENDER joe RCPT will");
		joe.sendToServer("CREQ SENDER joe RCPT anvisha");
		joe.sendToServer("CREQ SENDER joe RCPT jesika");
	      for (int i = 0; i < 25; i++) {
	            TestClient cli = new TestClient("testCli"+i);
	            cli.start();
	            cli.signIn();
	            if (i > 0) {
	                cli.sendToServer("CREQ SENDER testCli"+i+" RCPT testCli"+(i-1));
	            }
	      }
	}

}
