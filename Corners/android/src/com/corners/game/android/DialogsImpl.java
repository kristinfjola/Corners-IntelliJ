/**
 * @author Kristin Fjola Tomasdottir
 * @date 	12.03.15
 * @goal 	Various native Android features
 */
package com.corners.game.android;

import screens.Play;

import com.corners.game.Dialogs;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;


public class DialogsImpl implements Dialogs {
       Handler uiThread;
       Context appContext;
       AndroidLauncher launcher;
       ProgressDialog progress;

      public DialogsImpl(Context appContext) {
               uiThread = new Handler();
               this.appContext = appContext;
       }

      @Override
      public void showDirections(final String alertBoxTitle, final String alertBoxMessage, final String alertBoxButtonText, final Play playScreen) {
		  // onClick
    	  final DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {		
    		  @Override
    		  public void onClick(DialogInterface dialog, int whichButton) {
    			  playScreen.resume();
			  }
		  };
		  
		  // onDismiss
		  final DialogInterface.OnDismissListener onDismissListener = new DialogInterface.OnDismissListener() {			
			  @Override
			  public void onDismiss(DialogInterface dialog) {
				  playScreen.resume();
			  }
		  };
    	  
		  // build dialog
		  LayoutInflater inflater = (LayoutInflater) appContext.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		  final View dialogLayout = inflater.inflate(R.layout.directions_layout, null);
    	  uiThread.post(new Runnable() {
                       public void run() {
                    	   AlertDialog dialog = new AlertDialog.Builder(appContext, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
                               					//.setIcon(R.drawable.temp)
                                               //.setTitle(alertBoxTitle)
                                               .setMessage(alertBoxMessage)
                                               .setNeutralButton(alertBoxButtonText, onClickListener)
                                               //.setView(dialogLayout)
                                               .create();
                    	   dialog.setOnDismissListener(onDismissListener);
                    	   dialog.show();
                       }
               });
       }

	@Override
	public void showToast(final CharSequence toastMessage, int toastDuration) {
		uiThread.post(new Runnable() {
            public void run() {
                    Toast.makeText(appContext, toastMessage, Toast.LENGTH_LONG)
                                    .show();
            }
		});
	}
	
	@Override
	public void showProgressBar() {
		LayoutInflater inflater = (LayoutInflater) appContext.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.directions_layout, null);
		 uiThread.post(new Runnable() {
             public void run() {
            	 //show(Context context, CharSequence title, CharSequence message, boolean indeterminate, boolean cancelable, DialogInterface.OnCancelListener cancelListener)
         		progress = new ProgressDialog(appContext);
            	progress.setMessage("Loading friends... ");
         		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
         		progress.setIndeterminate(true);
         		progress.show();
             }
		});
	}
	
	@Override
	public void hideProgressBar() {
		uiThread.post(new Runnable() {
            public void run() {
            	progress.dismiss();
            }
		});
	}
}
