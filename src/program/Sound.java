package program;

import java.applet.Applet;
import java.applet.AudioClip;
import sun.audio.*;
import java.io.*;

public class Sound 
{
	//public static final AudioClip NAME = Applet.newAudioClip(Sound.class.getResource("resources/sfx/name.wav")); 
	   /*
	    * Sound.NAME.play();
	    * Sound.NAME.loop();
	    * Sound.NAME.stop();
	    */
	public static final AudioClip SFX = Applet.newAudioClip(Sound.class.getResource("resources/sfx/openthing.wav"));
	
	public static final AudioClip MUSIC = Applet.newAudioClip(Sound.class.getResource("resources/sfx/atheme_2.wav"));
}
