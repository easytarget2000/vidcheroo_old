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

import java.awt.Canvas;

import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;

public class VideoCanvas extends Canvas{
	private static final long serialVersionUID = 201407111955L;
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
