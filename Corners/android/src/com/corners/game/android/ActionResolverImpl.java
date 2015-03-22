package com.corners.game.android;

import com.corners.game.ActionResolver;
import com.facebook.widget.LoginButton;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;


public class ActionResolverImpl implements ActionResolver {
       Handler uiThread;
       Context appContext;
       AndroidLauncher launcher;


      public ActionResolverImpl(Context appContext) {
               uiThread = new Handler();
               this.appContext = appContext;
       }


      public void showShortToast(final CharSequence toastMessage) {
               uiThread.post(new Runnable() {
                       public void run() {
                               Toast.makeText(appContext, toastMessage, Toast.LENGTH_SHORT)
                                               .show();
                       }
               });
       }



       public void showLongToast(final CharSequence toastMessage) {
               uiThread.post(new Runnable() {
                       public void run() {
                               Toast.makeText(appContext, toastMessage, Toast.LENGTH_LONG)
                                               .show();
                       }
               });
       }


      @Override
       public void showAlertBox(final String alertBoxTitle,
                       final String alertBoxMessage, final String alertBoxButtonText) {
               uiThread.post(new Runnable() {
                       public void run() {
                               new AlertDialog.Builder(appContext)
                                               .setTitle(alertBoxTitle)
                                               .setMessage(alertBoxMessage)
                                               .setNeutralButton(alertBoxButtonText,
                                                               new DialogInterface.OnClickListener() {
                                                                       public void onClick(DialogInterface dialog,
                                                                                       int whichButton) {
                                                                       }
                                                               }).create().show();
                       }
               });
       }

	@Override
	public void showToast(final CharSequence toastMessage, int toastDuration) {
		// TODO Auto-generated method stub
		uiThread.post(new Runnable() {
            public void run() {
                    Toast.makeText(appContext, toastMessage, Toast.LENGTH_LONG)
                                    .show();
            }
		});
	}
}
