import javax.swing.JFrame;
import com.sun.jna.NativeLibrary;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;


public class VideoWindow extends JFrame {

	private static final long serialVersionUID = 8264453733374255866L;
	private static int iAmountOfPlayer;									// Has to be the same in the engine.
	private VideoCanvas[] arrayVideoCanvas;
	private String strVlcPath = "C:\\Program Files\\VLC";
	private String[] aStrMediaPath;
	private boolean isFullscreen = false;
	private static final int iWindowWidthSmall  = 800;
	private static final int iWindowHeightSmall = 600;
	private static final int iWindowWidthFull	= 1920;
	private static final int iWindowHeightFull	= 1080;

	/**
	 * Create the frame and add a few VideoCanvas.
	 */
	public VideoWindow(int amountOfPlayer) {
		VideoWindow.iAmountOfPlayer = amountOfPlayer;
		arrayVideoCanvas = new VideoCanvas[iAmountOfPlayer];
		aStrMediaPath = new String[iAmountOfPlayer];
		
		// JFrame configuration:
		setBounds(400,100,iWindowWidthSmall, iWindowHeightSmall);
		setTitle("Vidcheroo Video Player");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Initialise the VLC Player.
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), strVlcPath);
		
		// Initialise the four VideoCanvas and add them to this JFrame.
		for(int i=0; i < iAmountOfPlayer; i++) {
			arrayVideoCanvas[i] = new VideoCanvas();
			arrayVideoCanvas[i].setBounds(0, 0, iWindowWidthSmall, iWindowHeightSmall);
			arrayVideoCanvas[i].setVisible(false);
			add(arrayVideoCanvas[i]);
		}

	}
	
	/**
	 *  Hides and stops all VLC player but the current one.
	 */
	public void run(int activePlayer) {
//		System.out.println("VideoWindow run(" + activePlayer + ")");
	
		for(int i=0; i < iAmountOfPlayer; i++) {
			arrayVideoCanvas[i].stop();
			arrayVideoCanvas[i].setVisible(false);	
			
			if(i == activePlayer) {
				arrayVideoCanvas[i].setVisible(true);
				arrayVideoCanvas[i].play(aStrMediaPath[activePlayer]);
			}
			else {
						
			}
		}
	}
	
	/**
	 * Pauses the video show.
	 */
	public void stop() {
		for(int i=0; i < iAmountOfPlayer; i++) {
			arrayVideoCanvas[i].stop();
			arrayVideoCanvas[i].setVisible(false);
		}
	}
	
	/**
	 * Tell a certain player to play a media file.
	 * @param mediaPath:	URL to media file
	 * @param pos:			Determines which player will play this.
	 */
	public void setMediaPathAtPos(String mediaPath, int pos) {
		aStrMediaPath[pos] = mediaPath;
	}
	
	/**
	 * Configures the paths in which the VLC libs are.
	 */
	public void setVlcPath(String vlcPath) {
		this.strVlcPath = vlcPath;
	}
	
	/**
	 * Switches between fullscreen and windowed mode.
	 * Hide the frame, toggle the border, resize, show the frame.
	 */
	public void toggleFullscreen() {

		// Go to windowed mode.
		if(isFullscreen) {
			dispose();
			setUndecorated(false);
			setResizable(true);
			
			// Shrinkage:
			setBounds(200, 200, iWindowWidthSmall, iWindowHeightSmall);
			for(int i=0; i < iAmountOfPlayer; i++)
				arrayVideoCanvas[i].setBounds(0, 0, iWindowWidthSmall, iWindowHeightSmall);
			
			setVisible(true);
			isFullscreen = false;
		}
		// Go to fullscreen mode.
		else {
			dispose();
			setUndecorated(true);
			setResizable(false);
			
			// Growth:
			setBounds(0, 0, iWindowWidthFull, iWindowHeightFull);
			
			isFullscreen = true;
			setVisible(true);
		}
		
		for(int i=0; i < iAmountOfPlayer; i++) {
			arrayVideoCanvas[i].stop();
			arrayVideoCanvas[i].setBounds(0, 0, iWindowWidthFull, iWindowHeightFull);
		}
		
	}
	
}
