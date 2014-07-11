/*
 * Copyright (C) 2014 Easy Target
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class VidcherooEngine {
	private ControlWindow controlWindow;
	private VideoWindow videoWindow;
	private FileLoader fileLoader = new FileLoader();
	private int numberOfInstances;						// Has to be the same in the VideoWindow.
	private int currentInstance = 0;					// 0 up to iAmountOfPlayer-1
	private double tempo = 120.0;						// Beats (1/4 notes) per minute
	private int tempoTime;								// Wait value in ms
	private double beatFrac = 1;						// 1:="1/4", 2:="1/8", 4:="1/16"
	private boolean isPlaying = false;					// Play or pause
	private boolean isFullscreen = false;
	
	public VidcherooEngine(int numberOfInstances) {
		this.numberOfInstances = numberOfInstances; 
		refreshBeatTime();
	}
	
	public void setControlWindow(ControlWindow cw) {
		controlWindow = cw;
	}
	
	public void setVideoWindow(VideoWindow vw) {
		videoWindow = vw;
	}
	
	public void setTempo(double tempo) {
		this.tempo = tempo;
		refreshBeatTime();
	}
	
	public double getTempo() {
		return tempo;
	}
	
	// Should only get .5, 1, 2 or 4.
	public void setBeatFraction(double frac) {
		beatFrac = frac;
		refreshBeatTime();
	}
	
	// 60s / BPM * beat fraction
	private void refreshBeatTime() {
		tempoTime = (int) (60 / (beatFrac * tempo) * 1000);
		System.out.println("New switch time: " + tempoTime);
	}
	
	public void loadFiles(String mediaPath) {
		fileLoader.loadFileList(mediaPath);
	}
	
	public void setVlcPath(String vlcPath) {
		videoWindow.setVlcPath(vlcPath);
	}
	
	public void play() {
		currentInstance = 0;
		
		if (fileLoader.getFileListLength() > 2) {		// Only play something if more than two files have been found.
			isPlaying = true;
			
	        Thread t = new Thread() {
	           public void run() {
	        	   System.out.println("Starting new EnginePlay thread.");
	        	   controlWindow.setStatusMessage("Playing...");
	        	   
	        	   // Get the first four videos and give them to the VideoWindow.
	        	   for(currentInstance=0; currentInstance < numberOfInstances; currentInstance++) {
	        		   videoWindow.setMediaPathOfInstance( fileLoader.getNextVideoPath(), currentInstance );
	        	   }
	              
	        	   while (isPlaying) {
	        		   // Go through the player.
	        		   ++currentInstance;
	        		   if (currentInstance > numberOfInstances - 1) currentInstance=0;

	        		   // Let the currently visible player run and wait rhythmically.
	        		   videoWindow.run(currentInstance);
	        		   try {
	        			   sleep(tempoTime);
	        		   } 
	        		   catch (InterruptedException ex) {}
	        		   
	        		   // Fill the last instance in the stack with a new file.
	        		   if (currentInstance > 0) {
		        		   videoWindow.setMediaPathOfInstance(fileLoader.getNextVideoPath(), currentInstance - 1);
	        		   } else {
		        		   videoWindow.setMediaPathOfInstance(fileLoader.getNextVideoPath(), numberOfInstances - 1);
	        		   }
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
		isFullscreen = !isFullscreen;
		videoWindow.setFullscreen(isFullscreen);
	}
	
	public void setStatusMessage(String message) {
		controlWindow.setStatusMessage(message);
	}
}
