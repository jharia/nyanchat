package testing;
import org.junit.*;
import clientSide.Controller;
import clientSide.NyanchatGUI;

public class TestControllerGUI {
	
	/**
	 * these tests all call the handleServerMessage in the controller and were validated visually
	 * by looking at changes in the GUI.
	 */
	
	@Test
	public void testOnlineUsers(){
		NyanchatGUI GUI = new NyanchatGUI("anvisha",null);
		Controller c1 = new Controller(GUI);
		c1.handleServerMessage("ONLINEUSERS anvisha will jesika nyan");
		sleepThread(4000);
		c1.handleServerMessage("ONLINEUSERS anvisha will");
		sleepThread(4000);
	}
	
	@Test
	public void testBasicChanges(){
		NyanchatGUI GUI = new NyanchatGUI("anvisha",null);
		Controller c1 = new Controller(GUI);
		c1.handleServerMessage("CSTART CID 99990 USER will");
		sleepThread(1000);
		assert(GUI.cidToTabs.containsKey(99990));
		c1.handleServerMessage("CHATSENDER will CID 99990 DATA abcd efgh");
		sleepThread(1000);
		c1.handleServerMessage("CHATSENDER will CID 1234 DATA abcd");
		sleepThread(1000);
		c1.handleServerMessage("CCHANGE CID 99990 anvisha");
		sleepThread(1000);
		c1.handleServerMessage("CCHANGE CID 0 anvisha will jesika nyan");
		sleepThread(10000);
		c1.handleServerMessage("CCHANGE CID 0 anvisha will");
		sleepThread(4000);
	}
	
	@Test
	public void testTypingStatus(){
		NyanchatGUI GUI = new NyanchatGUI("anvisha",null);
		Controller c1 = new Controller(GUI);
		c1.handleServerMessage("CCHANGE CID 5 anvisha will jesika nyan");
		sleepThread(4000);
		c1.handleServerMessage("TYPING will CID 5");
		sleepThread(4000);
		c1.handleServerMessage("CCHANGE CID 5 anvisha jesika");
		sleepThread(4000);
		c1.handleServerMessage("TYPED will CID 5");
		c1.handleServerMessage("TYPED jesika CID 5");
		sleepThread(4000);
		c1.handleServerMessage("CCHANGE CID 5 anvisha jesika will nyan");
		sleepThread(4000);
		c1.handleServerMessage("CLEARED jesika CID 5");
		sleepThread(4000);
		c1.handleServerMessage("CCHANGE CID 5 anvisha");
		sleepThread(4000);
		c1.handleServerMessage("TYPING Will CID 4"); //nothing should happen
		sleepThread(4000);
	}
	
	public void sleepThread(int time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
