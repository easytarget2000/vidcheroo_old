public class VidcherooEngine {
	private ControlWindow controlWindow;
	private VideoWindow videoWindow;
	private FileLoader fileLoader = new FileLoader();
	private static int iAmountOfPlayer;					// Has to be the same in the VideoWindow.
	private int iCurrentPlayer = 0;						// 0 up to iAmountOfPlayer-1
	private double dTempo = 120.0;						// Beats (1/4 notes) per minute
	private int iTempoSwitch;							// Wait value in ms
	private double dBeatFrac = 1;						// 1:="1/4", 2:="1/8", 4:="1/16"
	private boolean isPlaying = false;					// Play or pause
	
	public VidcherooEngine(int amountOfPlayer) {
		VidcherooEngine.iAmountOfPlayer = amountOfPlayer; 
		calculateSwitchTime();
	}
	
	public void setControlWindow(ControlWindow cw) {
		controlWindow = cw;
	}
	
	public void setVideoWindow(VideoWindow vw) {
		videoWindow = vw;
	}
	
	public void setTempo(double bpm) {
		dTempo = bpm;
		calculateSwitchTime();
	}
	
	public double getTempo() {
		return dTempo;
	}
	
	// Should only get .5, 1, 2 or 4.
	public void setBeatFraction(double frac) {
		dBeatFrac = frac;
		calculateSwitchTime();
	}
	
	// 60s / BPM * Frac
	private void calculateSwitchTime() {
		iTempoSwitch = (int) (60 / (dBeatFrac*dTempo) * 1000);
		System.out.println("New switch time: " + iTempoSwitch);
	}
	
	public void loadFiles(String mediaPath) {
		fileLoader.loadFileList(mediaPath);
	}
	
	public void setVlcPath(String vlcPath) {
		videoWindow.setVlcPath(vlcPath);
	}
	
	public void play() {
		iCurrentPlayer = 0;
		
		if(fileLoader.getFileListLength() > 2) {		// Only play something if more than two files have been found.
			isPlaying = true;
			
	        Thread t = new Thread() {
	           public void run() {
	        	   System.out.println("Starting new EnginePlay thread.");
	        	   int iNextPlayer = 0;
	        	   controlWindow.setStatusMessage("Playing...");
	        	   
	        	   // Get the first four videos and give them to the VideoWindow.
	        	   for(iCurrentPlayer=0; iCurrentPlayer < iAmountOfPlayer; iCurrentPlayer++) {
	        		   videoWindow.setMediaPathAtPos( fileLoader.getNextVideoPath(), iCurrentPlayer );
	        	   }
	              
	        	   while(isPlaying) {
	        		   // Go through the player.
	        		   ++iCurrentPlayer;
	        		   if(iCurrentPlayer > iAmountOfPlayer - 1)
	        			   iCurrentPlayer=0;

	        		   // Let the currently visible player run and wait rhythmically.
	        		   videoWindow.run(iCurrentPlayer);
	        		   try {
	        			   sleep(iTempoSwitch);
	        		   } 
	        		   catch (InterruptedException ex) {}
	        		   
	        		   // Fill the last player with a new file.
	        		   if(iCurrentPlayer > 0) {
	        			   iNextPlayer = iCurrentPlayer - 1;
	        		   }
	        		   else {
	        			   iNextPlayer = iAmountOfPlayer - 1;
	        		   }
	        		   videoWindow.setMediaPathAtPos( fileLoader.getNextVideoPath(), iNextPlayer );
	              }
	        	   
	           }
	        };
	        t.start();  // call back run()
		}
		else {
			controlWindow.setStatusMessage("Missing path to media files!");
		}
	}
	
	public void pause() {
		System.out.println("Pausing video.");
		isPlaying = false;
		videoWindow.stop();
	}
	
	public void toggleFullscreen() {
		videoWindow.toggleFullscreen();
	}
	
	public void setStatusMessage(String message) {
		controlWindow.setStatusMessage(message);
	}


		
}
