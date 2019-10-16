package com.yc.util;

import java.applet.Applet;
import java.applet.AudioClip;

import org.eclipse.jface.resource.ResourceManager;

/**
 * 音乐播放
 * @author 沈俊羽
 *
 */
public class PlaySound {

	/**
	 * 播放鼠标点击声音
	 */
	public static void mouseClick(){
		AudioClip clip=Applet.newAudioClip(ResourceManager.class.getClassLoader().getResource("sounds/MouseClick.wav"));
		clip.play();
	}
}
