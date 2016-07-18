package io.yooz.notificationshade;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private List<ConversationMessage> conversationA = new ArrayList<>();
    private List<ConversationMessage> conversationB = new ArrayList<>();
    private List<ConversationMessage> conversationC = new ArrayList<>();

    private int mGroupId = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.singleNotification).setOnClickListener(this);
        findViewById(R.id.emptyBundleNotification).setOnClickListener(this);
        findViewById(R.id.bundleNotification).setOnClickListener(this);
        findViewById(R.id.styledNotification).setOnClickListener(this);
        findViewById(R.id.customViewNotification).setOnClickListener(this);

        setupConversationMessages();
    }

    private void sendNotification() {
        ConversationMessage message = new ConversationMessage(mGroupId++, mGroupId, "Hello " + mGroupId, "", true);
        NotificationCenter.showNotification(getApplicationContext(), message);
    }

    private void sendBundleNotification() {
        NotificationCenter.showSummaryNotification(getApplicationContext(), conversationB);
        for (ConversationMessage message : conversationB) {
            NotificationCenter.showNotification(getApplicationContext(), message);
        }
    }

    private void sendEmptyBundleNotification() {
        NotificationCenter.showSummaryNotification(getApplicationContext(), conversationC);
    }

    private void sendStyledNotification() {
        NotificationCenter.showStyledMessagingNotification(getApplicationContext(), conversationB);
    }

    private void sendCustomViewNotification() {
        NotificationCenter.showCustomViewNotification(getApplicationContext(), conversationC);
    }

    private void setupConversationMessages() {
        Random random = new Random();

        int groupId = 1111;
        conversationA.add(new ConversationMessage(groupId, random.nextInt(Integer.MAX_VALUE), "Hello!!", "", true));

        groupId = 2222;
        conversationB.add(new ConversationMessage(groupId, random.nextInt(Integer.MAX_VALUE), "Hello!!", "John", false));
        conversationB.add(new ConversationMessage(groupId, random.nextInt(Integer.MAX_VALUE), "Who is it?", "", true));
        conversationB.add(new ConversationMessage(groupId, random.nextInt(Integer.MAX_VALUE), "It's John Doe", "John", false));
        conversationB.add(new ConversationMessage(groupId, random.nextInt(Integer.MAX_VALUE), "Right..", "", true));

        groupId = 3333;
        conversationC.add(new ConversationMessage(groupId, random.nextInt(Integer.MAX_VALUE), "Do you do bargain?", "Jane", false));
        conversationC.add(new ConversationMessage(groupId, random.nextInt(Integer.MAX_VALUE), "Nope", "", true));
        conversationC.add(new ConversationMessage(groupId, random.nextInt(Integer.MAX_VALUE), "Never mind", "Jane", false));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.singleNotification:
                sendNotification();
                break;
            case R.id.bundleNotification:
                sendBundleNotification();
                break;
            case R.id.emptyBundleNotification:
                sendEmptyBundleNotification();
                break;
            case R.id.styledNotification:
                sendStyledNotification();
                break;
            case R.id.customViewNotification:
                sendCustomViewNotification();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
