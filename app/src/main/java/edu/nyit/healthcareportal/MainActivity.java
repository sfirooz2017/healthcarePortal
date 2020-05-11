package edu.nyit.healthcareportal;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import edu.nyit.healthcareportal.ui.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private NotificationHelper notificationHelper;

    private final String CHANNEL_ID = "personal notifications";
    private final int NOTIFICATION_ID = 001;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (GlobalData.getInstance().getAdmin())
            setContentView(R.layout.activity_admin_main);
        else
            setContentView(R.layout.activity_main);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavGraph graph = navController.getNavInflater().inflate(R.navigation.mobile_navigation);

        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        if (GlobalData.getInstance().getAdmin()) {
            graph.setStartDestination(R.id.nav_admin_home);
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_admin_home, R.id.nav_admin_medication, R.id.nav_admin_orders)
                    .setDrawerLayout(drawer)
                    .build();
        }
        else
        {
            graph.setStartDestination(R.id.nav_home);
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home, R.id.nav_medication, R.id.nav_orders)
                    .setDrawerLayout(drawer)
                    .build();

            for(int x = 0; x < GlobalData.getInstance().getUsers().get(0).getOrders().size();x++)
            {
                String[] tempDate = GlobalData.getInstance().getUsers().get(0).getOrders().get(x).getArrived().split("/");
                orderArrived(Integer.parseInt(tempDate[1]), Integer.parseInt(tempDate[0]));
            }
            orderArrived(5, 3);
        }

        navController.setGraph(graph);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



       // notificationHelper = new NotificationHelper(this);
        //displayNotification();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void displayNotification()
    {
        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_sms_notification)
                .setContentTitle("Package update")
                .setContentText("Your package has arrived!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        sendNotification("Order Update", "Order Arrived!");

      //  NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
      //  notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    public void sendNotification(String title, String message)
    {
        NotificationCompat.Builder nb = notificationHelper.getChannel1Notification(title, message);
        notificationHelper.getManager().notify(1, nb.build());

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void orderArrived(int m, int d)
    {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 3);
        c.set(Calendar.MONTH, 4);
        startAlarm(c);

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void startAlarm(Calendar c)
    {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);


    }




    private void createNotificationChannel()
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            CharSequence name = "Personal Notification";
            String description = "include all personal notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationChannel.setDescription(description);

            NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);

        }

    }



}
