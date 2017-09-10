package com.brainor.textforward.textautoforward;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.telephony.SubscriptionManager;

import java.text.SimpleDateFormat;

/**
 * 欧伟科 on 2017/7/19.
 */

public class SmsReceiver extends BroadcastReceiver {
    private static final String SMS_RECEIVED = android.provider.Telephony.Sms.Intents.SMS_RECEIVED_ACTION;
    @Override
    public void onReceive(Context context, Intent intent) {
//        if (intent.getAction().equals(SMS_RECEIVED) ) {
        if (intent.getAction().equals(SMS_RECEIVED) && SubscriptionManager.from(context).getActiveSubscriptionInfo(intent.getIntExtra("subscription", -1)).getCarrierName().toString().matches("(中国电信|China Telecom)")) {
            SmsMessage[] 短信序列s = android.provider.Telephony.Sms.Intents.getMessagesFromIntent(intent);
            if (短信序列s.length > 0) {
                String 短信内容 = "";
                for (SmsMessage 短信序列 : 短信序列s) 短信内容 += 短信序列.getDisplayMessageBody();
//                Toast.makeText(context, 调试信息, Toast.LENGTH_LONG).show();
                短信内容 += "\n" + 短信序列s[0].getOriginatingAddress() + ":" +
                        短信序列s[0].getDisplayOriginatingAddress() + ":" +
                        (new SimpleDateFormat("MM-dd HH:mm:ss")).format(短信序列s[0].getTimestampMillis());
                new SendMail().execute(短信内容);
            }
        }
    }
}