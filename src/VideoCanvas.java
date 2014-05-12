import java.awt.Canvas;

import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;


public class VideoCanvas extends Canvas{
	
	private static final long serialVersionUID = 6257285474024452417L;
	private MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory("--no-video-title-show", "--quiet");
    private CanvasVideoSurface videoSurface = mediaPlayerFactory.newVideoSurface(this);
    private EmbeddedMediaPlayer mediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer();
	
    /**
     * Initialise the canvas with a media player on top.
     */
	public VideoCanvas() {
	    mediaPlayer.setVideoSurface(videoSurface);
	    mediaPlayer.setVolume(0);
	    mediaPlayer.setRepeat(true);
	    mediaPlayer.enableMarquee(false);
	}
	
	/**
	 * @param mediaFile: Play this file in this media player.
	 */
	public void play(String mediaFile) {
		mediaPlayer.playMedia(mediaFile);
	}
	
	/**
	 * Pause this media player.
	 */
	public void pause() {
		mediaPlayer.pause();
	}
	
	/**
	 * Stop this media player.
	 */
	public void stop() {
		mediaPlayer.stop();
	}
	
}
