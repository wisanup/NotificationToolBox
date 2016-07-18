package io.yooz.notificationshade;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.RemoteInput;


public class RemoteInputReceiver extends BroadcastReceiver {

    public static final String KEY_QUICK_REPLY_TEXT = "quick_reply";

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle results = RemoteInput.getResultsFromIntent(intent);
        if (results != null) {
            CharSequence quickReplyResult = results.getCharSequence(KEY_QUICK_REPLY_TEXT);
        }
    }
}
