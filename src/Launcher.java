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

public class Launcher {
	private static final int NUMBER_OF_INSTANCES = 16;
	private static VidcherooEngine engine = new VidcherooEngine(NUMBER_OF_INSTANCES);
	
	public static void main(String[] args) {
		
		// GUI setup thread:
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
	         public void run() {
	        	ControlWindow controlWindow = new ControlWindow();
	     		VideoWindow videoWindow = new VideoWindow(NUMBER_OF_INSTANCES);
	     		controlWindow.setVisible(true);
	     		videoWindow.setVisible(true);
	     		
	     		controlWindow.setEngine(engine);
	     		
	     		engine.setControlWindow(controlWindow);
	     		engine.setVideoWindow(videoWindow);
	         }
	      });
		
	}

}
