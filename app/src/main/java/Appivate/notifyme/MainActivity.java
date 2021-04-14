package Appivate.notifyme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
/*
 This is a demo project produced by James Palfrey on behalf of Astra Appivate 2021.
 It is used to provide a quick means by which developers can understand key technologies on the platform
 and should not be used as a sole source of learning.
 Developer docs on notifications can be found here https://developer.android.com/training/notify-user/build-notification
 */

public class MainActivity extends AppCompatActivity {
     LinearLayout basicBtn, inAppBtn, actionBtn, criticalBtn;
     EditText title, message;
     int notiID = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUI();
        //create notification channel
        createNotificationChannel();
        //make activity hide title & change color of system bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(getResources().getColor(R.color.rafblue));
        getSupportActionBar().hide();

    }
@SuppressLint("ClickableViewAccessibility")
    private void setUI() {
        //set up layout
        basicBtn = findViewById(R.id.basic);
        actionBtn = findViewById(R.id.withAction);
        criticalBtn = findViewById(R.id.critical);
        title = findViewById(R.id.title);
        message = findViewById(R.id.message);
        //set up button on touches
        basicBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    basicBtn.setBackgroundResource(R.drawable.btn_rounded_pressed);
                    //check text fields are verified.
                    if (verifyTextFields(title.getText().toString(), message.getText().toString())){
                    //send basic notification
                        if (message.getText().length() > 40){
                            //send notification with drop down box
                            sendBasicNotificationWithDropDown();
                        }else {
                            sendBasicNotification();
                        }
                    }else {
                        Toast.makeText(MainActivity.this, "You must enter some text to send a notification", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    basicBtn.setBackgroundResource(R.drawable.btn_rounded);
                }
                return false;
            }
        });

        criticalBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    criticalBtn.setBackgroundResource(R.drawable.btn_rounded_pressed);
                     sendCriticalNotification();
                }else if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    criticalBtn.setBackgroundResource(R.drawable.btn_rounded);
                }
                return false;
            }
        });
    actionBtn.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                actionBtn.setBackgroundResource(R.drawable.btn_rounded_pressed);
                sendNotificationWithAction();
            }else if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                actionBtn.setBackgroundResource(R.drawable.btn_rounded);
            }
            return false;
        }
    });
    }

    private boolean sendBasicNotification() {
        boolean result = false;
        //Every notification should take your user to a part of your app - otherwise..whats the point? Therefore we create a pendingIntent
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        //channel ID required for API 26 +
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle(title.getText().toString())
                .setContentText(message.getText().toString())
                //you can change priority based on how urgent the information is
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                // This automatically removes the notification when the user taps it.
                .setAutoCancel(true);

        //now show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // notificationID is unique Int for each notification
        notiID++;
        notificationManager.notify(notiID, builder.build());
        return result;
    }
    /*@Summary: Uses NotificationCompat to send a simple notification for messages where the content is over 40 characters (1 line)
      @Param title: EditText
      @Param message: EditText
      @Param R.drawable.ic_stat_name:  Notification icon, transparent bg.
      @Post: Delivers a text based notification
     */
    private boolean sendBasicNotificationWithDropDown() {
        boolean result = false;
        //channel ID required for API 26 +
        //Every notification should take your user to a part of your app - otherwise..whats the point? Therefore we create a pendingIntent
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        //channel ID required for API 26 +
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle(title.getText().toString())
                .setContentText(message.getText().toString())
                //you can change priority based on how urgent the information is
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                // NEW : this is the line you need to place to enable drop down text menu
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message.getText().toString()))
                // This automatically removes the notification when the user taps it.
                .setAutoCancel(true);

        //now show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // notificationID is unique Int for each notification
        notiID++;
        notificationManager.notify(notiID, builder.build());
        return result;
    }

    /*@Summary: Uses NotificationCompat to send a notification with actions using the .addAction() method.
         @Post: Delivers a text based notification
        */
    private  boolean sendNotificationWithAction() {
        boolean result = false;
        //channel ID required for API 26 +
        //Every notification should take your user to a part of your app - otherwise..whats the point? Therefore we create a pendingIntent
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        //channel ID required for API 26 +
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle("Op Wideawake is in effect")
                .setContentText("All building custodians are to report areas clear.")
                // you can change priority based on how urgent the information is
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                //This creates it as a critical notification.
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                // set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                //now add the action
                .addAction(R.drawable.ic_stat_name,"Report Find",pendingIntent)
                .addAction(R.drawable.ic_stat_name,"Report Clear", pendingIntent)
                // This automatically removes the notification when the user taps it.
                .setAutoCancel(true);

        //now show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // notificationID is unique Int for each notification
        notiID++;

        notificationManager.notify(notiID, builder.build());
        return result;
    }
    /*@Summary: Uses NotificationCompat to send a custom notification for messages where the content is of important nature
      @Param notificationLayout: identifies to the user critical information via RemoteView
      @Param notificationLayoutExpanded: expanded information type.
      @Param R.drawable.ic_stat_name:  Notification icon, transparent bg.
      @Post: Delivers a custom notification
     */
    private  boolean sendCriticalNotification() {
        boolean result = false;
        //channel ID required for API 26 +
        //Every notification should take your user to a part of your app - otherwise..whats the point? Therefore we create a pendingIntent
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        //set the custom layouts
        RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.notification_small);
        //set text views
        notificationLayout.setTextViewText(R.id.notification_title, "IED Found");
        notificationLayout.setTextViewText(R.id.notification_secondary, "Please exit via North Gate");
        RemoteViews notificationLayoutExpanded = new RemoteViews(getPackageName(), R.layout.notification_layout);
        notificationLayoutExpanded.setTextViewText(R.id.notification_title, "IED Found");
        notificationLayoutExpanded.setTextViewText(R.id.notification_secondary, "Please exit via North Gate");
        //channel ID required for API 26 +
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.ic_stat_name)
                // you can change priority based on how urgent the information is
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                //set custom view for expanded
                .setCustomBigContentView(notificationLayoutExpanded)
                //set custom view for unexpanded.
                .setCustomContentView(notificationLayout)
                //This creates it as a critical notification.
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                // set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setFullScreenIntent(pendingIntent,true)
                // This automatically removes the notification when the user taps it.
                .setAutoCancel(true);

        //now show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // notificationID is unique Int for each notification
        notiID++;

        notificationManager.notify(notiID, builder.build());
        return result;
    }
    /*
    NotificationChannel class is new and only supported on API 26+
    @Summary: builds a notification channel for you to push notifications to
    @Param id:  Notification channel ID must match that of your NotificationCompat.Builder.
     */
    private void createNotificationChannel(){
        //Create notification Channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "testChannel";
            String description = "A channel to test notifications on";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            // this notification channel ID must match that of your notification
            NotificationChannel channel = new NotificationChannel("1", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public boolean verifyTextFields (String title, String message){
        boolean result = false;
        if (!title.equals("")&&!message.equals("")){
            // has a message
            if (title.length()< 60 && message.length() < 300){
                // is under the required length
                result = true;
            }
        }

        return result;
    }
}