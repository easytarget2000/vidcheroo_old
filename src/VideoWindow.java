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

import javax.swing.JFrame;
import com.sun.jna.NativeLibrary;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

public class VideoWindow extends JFrame {
	private static final long serialVersionUID = 201407111955L;
	private static int numberOfInstances;
	private VideoCanvas[] allVideoCanvas;
	private String[] mediaPaths;

	/**
	 * Create the frame and add a few VideoCanvas.
	 */
	public VideoWindow(int amountOfPlayer) {
		VideoWindow.numberOfInstances = amountOfPlayer;
		allVideoCanvas = new VideoCanvas[numberOfInstances];
		mediaPaths = new String[numberOfInstances];
		
		// Initialise the VLC Player.
		// TODO: Read this value from settings.
		setVlcPath("C:\\Program Files\\VLC");
		
		// Initialise the four VideoCanvas and add them to this JFrame.
		for (int i = 0; i < numberOfInstances; i++) {
			allVideoCanvas[i] = new VideoCanvas();
			allVideoCanvas[i].setVisible(false);
			add(allVideoCanvas[i]);
		}
		
		// JFrame configuration:
		setFullscreen(false);
		setTitle("Vidcheroo Video Player");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 *  Hides and stops all VLC player but the current one.
	 */
	public void run(int activePlayer) {	
		for(int i=0; i < numberOfInstances; i++) {
			//allVideoCanvas[i].stop();
			//allVideoCanvas[i].setVisible(false);	
			if(i == activePlayer) {
				allVideoCanvas[i].setVisible(true);
				allVideoCanvas[i].play(mediaPaths[activePlayer]);
			}
			else {
				allVideoCanvas[i].stop();
				allVideoCanvas[i].setVisible(false);	
			}
		}
	}
	
	/**
	 * Pauses the video show.
	 */
	public void stop() {
		for(int i=0; i < numberOfInstances; i++) {
			allVideoCanvas[i].stop();
			allVideoCanvas[i].setVisible(false);
		}
	}
	
	/**
	 * 
	 * @param isFullscreen
	 */
	public void setFullscreen(boolean isFullscreen) {
		// Hide.
		dispose();
		
		if (isFullscreen) {		// Go to fullscreen mode.
			// Remove decorations and do not allow resizing from now on.
			setUndecorated(true);
			setResizable(false);
			
			// Growth:
			// TODO: Read these values from current screen settings.
			int windowWidthFull = 1920;
			int windowHeightFull = 1080;
			setBounds(0, 0, windowWidthFull, windowHeightFull);
			for (int i = 0; i < numberOfInstances; i++) {
				//allVideoCanvas[i].stop();
				allVideoCanvas[i].setBounds(0, 0, windowWidthFull, windowHeightFull);
			}
		} else {				// Go to windowed mode.
			// Show decorations and be resizable.
			setUndecorated(false);
			setResizable(true);
			
			// Shrinkage:
			// TODO: Read these values from stored settings.
			int windowWidthSmall = 1366;
			int windowHeightSmall = 768;
			setBounds(200, 200, windowWidthSmall, windowHeightSmall);
			for(int i=0; i < numberOfInstances; i++)
				allVideoCanvas[i].setBounds(0, 0, windowWidthSmall, windowHeightSmall);
		}
		
		// Come back.
		setVisible(true);
	}
	
	/**
	 * Tell a certain player to play a media file.
	 * @param mediaPath:	URL to media file
	 * @param pos:			Determines which player will play this.
	 */
	public void setMediaPathOfInstance(String mediaPath, int instance) {
		mediaPaths[instance] = mediaPath;
	}
	
	/**
	 * Configures the paths in which the VLC libs are.
	 */
	public void setVlcPath(String vlcPath) {
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), vlcPath);
	}
}
