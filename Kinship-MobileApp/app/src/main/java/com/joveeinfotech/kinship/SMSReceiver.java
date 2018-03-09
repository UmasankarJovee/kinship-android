package com.joveeinfotech.kinship;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

import com.joveeinfotech.kinship.notification.FcmMessagingService;

import kotlin.TypeCastException;

public class SMSReceiver extends BroadcastReceiver {

	LocationManager lm;
	LocationListener locationListener;
	String senderTel;
	Context context;
	EmptyActivity h;
	FcmMessagingService fcm;

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub

		h = new EmptyActivity();
		fcm = new FcmMessagingService();
		Bundle bundle = arg1.getExtras();
		SmsMessage[] msgs = null;
		String str = "";
		if (bundle != null) {
			senderTel = "";
			Object[] pdus = (Object[]) bundle.get("pdus");
			msgs = new SmsMessage[pdus.length];
			for (int i = 0; i < msgs.length; i++) {
				msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
				if (i == 0) {
					senderTel = msgs[i].getOriginatingAddress();
				}
				str += msgs[i].getMessageBody().toString();
			}
			if (str.startsWith("Kinship")) {

				h.sendNotification();
				//fcm.onMessageReceived(null);

				//this.abortBroadcast();
			}
		}
    }
}