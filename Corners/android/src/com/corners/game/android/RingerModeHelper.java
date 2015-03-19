/**
 * @author 	Johanna Agnes Magnusdottir
 * @date 	15.03.2015
 * @goal 	Can check the phones ring mode, and acts as a listener for when the it is changed
 * 			(alerts the appropriate class to update).
 */

package com.corners.game.android;

import com.corners.game.MainActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

public class RingerModeHelper extends BroadcastReceiver {
	MainActivity main;
	AudioManager audioManager;
	
	public RingerModeHelper(MainActivity main, Context c) {
		this.main = main;
		this.audioManager = (AudioManager)c.getSystemService(Context.AUDIO_SERVICE);
		this.main.updatePhoneVolume(isNormalRingerMode());
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		main.updatePhoneVolume(isNormalRingerMode());
	}
	
	/**
	 * Checks the phones ringer mode
	 * @return false if the ringer mode is on silent/vibrate, else true
	 */
	public boolean isNormalRingerMode() {
		switch (audioManager.getRingerMode()) {
	    case AudioManager.RINGER_MODE_SILENT:
	        return false;
	    case AudioManager.RINGER_MODE_VIBRATE:
	    	return false;
	    case AudioManager.RINGER_MODE_NORMAL:
	    	return true;
		}
		return true;
	}
}
