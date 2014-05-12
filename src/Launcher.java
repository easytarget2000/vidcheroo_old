public class Launcher {
	private static final int iAmountOfPlayer = 16;
	private static VidcherooEngine engine = new VidcherooEngine(iAmountOfPlayer);
	
	public static void main(String[] args) {
		

		// GUI setup thread:
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
	         public void run() {
	            
	        	ControlWindow controlWindow = new ControlWindow();
	     		VideoWindow videoWindow = new VideoWindow(iAmountOfPlayer);
	     		controlWindow.setVisible(true);
	     		videoWindow.setVisible(true);
	     		
	     		controlWindow.setEngine(engine);
	     		
	     		engine.setControlWindow(controlWindow);
	     		engine.setVideoWindow(videoWindow);
	     			 
	         }
	      });
		
	}

}
