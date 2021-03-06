/**
 * @author Kristin Fjola Tomasdottir
 * @date 	26.03.2015
 * @goal 	Handles and displays the notifications for the game
 */
package com.corners.game.android;

import java.util.Calendar;

import com.corners.game.Notifications;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class NotificationsImpl extends BroadcastReceiver implements Notifications{    
	int mId = 0;
	public Context context;
	
	public NotificationsImpl() {}
	
	public NotificationsImpl(AndroidLauncher androidLauncher) {
        this.context = androidLauncher;
    }
	
	@Override
	public void onReceive(Context context, Intent intent) {   
    	 PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
         PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
         wl.acquire();
         sendNotification(context);
         wl.release();
    }
	
	/**
	 * @param appContext
	 * sends a notification to the user's phone reminding him/her to play the game
	 */
	public void sendNotification(Context appContext){
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(appContext)
		        .setSmallIcon(R.drawable.logo)
		        .setContentTitle("Corners")
		        .setContentText("Aren't you missing Corners already?")
		        .setAutoCancel(true)
		        .setVibrate(new long[] { 10, 500});;
		        
		Intent resultIntent = new Intent(appContext, AndroidLauncher.class);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(appContext);
		stackBuilder.addParentStack(AndroidLauncher.class);
		stackBuilder.addNextIntent(resultIntent);
		
		PendingIntent resultPendingIntent =
		        stackBuilder.getPendingIntent(
		            0,
		            PendingIntent.FLAG_UPDATE_CURRENT
		        );
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager =
		    (NotificationManager) appContext.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(mId, mBuilder.build());
		Log.i("notifications", "notification sent");
	}

	@Override
	public void setNotifications() {
		Log.i("notifications", "setting notifications on");
		 AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
	     Intent i = new Intent(context, NotificationsImpl.class);
	     PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
	     
	     // repeat at around 14:00 every day
	     Calendar calendar = Calendar.getInstance();
	     calendar.setTimeInMillis(System.currentTimeMillis());
	     calendar.set(Calendar.HOUR_OF_DAY, 14);
	     calendar.set(Calendar.MINUTE, 0);
	     if(System.currentTimeMillis() > calendar.getTimeInMillis()){
	    	 calendar.add(Calendar.DATE, 1);
	     }

	     am.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
	}
	
	@Override
	public void cancelNotifications() {
		Log.i("notifications", "canceling notifications");
		Intent intent = new Intent(context, NotificationsImpl.class);
	    PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
	    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
	    alarmManager.cancel(sender);
	}
}
