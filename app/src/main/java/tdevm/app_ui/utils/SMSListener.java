package tdevm.app_ui.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Tridev on 21-11-2017.
 */

public class SMSListener extends BroadcastReceiver {

    public static final String TAG = SMSListener.class.getSimpleName();
    private static SMSReceived smsReceived;

    public static void setOnSMSReceivedListener(SMSReceived smsReceivedListener){
        smsReceived = smsReceivedListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG,"lo");
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
            SmsMessage[] msgs = null;
            String msg_from = null;
            String msgBody = null;
            if (bundle != null){
                //---retrieve the SMS message received---
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0; i<msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        msgBody = msgs[i].getMessageBody();
                    }
                }catch(Exception e){
                       Log.d("Exception caught",e.getMessage());
                }
                Log.d("Message Received",msg_from+' '+msgBody);
                if(smsReceived!=null){
                    smsReceived.onSMSReceived(msg_from,msgBody);
                }
               // Toast.makeText(context, "Intent Detected."+ msg_from+ msgBody, Toast.LENGTH_LONG).show();
            }
        }



//        final Bundle bundle = intent.getExtras();
//
//        try {
//
//            if (bundle != null) {
//
//                final Object[] pdusObj = (Object[]) bundle.get("pdus");
//
//                for (int i = 0; i < pdusObj.length; i++) {
//
//                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
//                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
//
//                    String senderNum = phoneNumber;
//                    String message = currentMessage.getDisplayMessageBody();
//
//                    Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);
//
//
//                    // Show Alert
//                    int duration = Toast.LENGTH_LONG;
//                    Toast toast = Toast.makeText(context,
//                            "senderNum: "+ senderNum + ", message: " + message, duration);
//                    toast.show();
//
//                } // end for loop
//            } // bundle is null
//
//        } catch (Exception e) {
//            Log.e("SmsReceiver", "Exception smsReceiver" +e);
//
//        }
    }

    public interface SMSReceived{
        void onSMSReceived(String sender,String body);
    }
}
