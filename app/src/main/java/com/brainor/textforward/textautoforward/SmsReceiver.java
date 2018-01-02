package com.brainor.textforward.textautoforward;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.telephony.SubscriptionManager;

import java.text.SimpleDateFormat;
import java.util.Objects;

/**
 * 欧伟科 on 2017/7/19.
 */

public class SmsReceiver extends BroadcastReceiver {
    private static final String SMS_RECEIVED = android.provider.Telephony.Sms.Intents.SMS_RECEIVED_ACTION;
    @Override
    public void onReceive(Context context, Intent intent) {
//        if (intent.getAction().equals(SMS_RECEIVED) ) {
        if (Objects.equals(intent.getAction(), SMS_RECEIVED) && SubscriptionManager.from(context).getActiveSubscriptionInfo(intent.getIntExtra("subscription", -1)).getCarrierName().equals("中国电信")) {
            SmsMessage[] 短信序列s = android.provider.Telephony.Sms.Intents.getMessagesFromIntent(intent);
            if (短信序列s.length > 0) {
                StringBuilder 短信内容 = new StringBuilder();
                for (SmsMessage 短信序列 : 短信序列s) 短信内容.append(短信序列.getDisplayMessageBody());
//                Toast.makeText(context, 调试信息, Toast.LENGTH_LONG).show();
                短信内容.append("\n").append(短信序列s[0].getOriginatingAddress()).append(":").append(短信序列s[0].getDisplayOriginatingAddress()).append(":").append((new SimpleDateFormat("MM-dd HH:mm:ss")).format(短信序列s[0].getTimestampMillis()));
                new SendMail().execute(短信内容.toString());
            }
        }
    }
}