package com.eebrian123tw.kable2580.selfhealth.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.eebrian123tw.kable2580.selfhealth.worker.ScreenOffWorker;
import com.eebrian123tw.kable2580.selfhealth.worker.UserPresentWorker;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class UserPresentReceiver extends BroadcastReceiver {

  private static final String SCREEN_TOGGLE_TAG = "SCREEN_TOGGLE_TAG";

  @Override
  public void onReceive(Context context, Intent intent) {
    // if user wakes up device
    if (Intent.ACTION_USER_PRESENT.equals(intent.getAction())) {
      Log.d(SCREEN_TOGGLE_TAG, "User present");
      WorkManager.getInstance()
          .enqueue(new OneTimeWorkRequest.Builder(UserPresentWorker.class).build());
    } else if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
      Log.d(SCREEN_TOGGLE_TAG, "Screen off");
      WorkManager.getInstance()
          .enqueue(new OneTimeWorkRequest.Builder(ScreenOffWorker.class).build());
    } else {
      Log.d(SCREEN_TOGGLE_TAG, "else");
    }
  }
}
