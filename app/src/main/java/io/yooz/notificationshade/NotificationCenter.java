package io.yooz.notificationshade;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.RemoteViews;

import java.util.List;

public class NotificationCenter {

    private static final String NOTIFICATION_GROUP = "messaging";

    public static void showSummaryNotification(Context context, List<ConversationMessage> messages, boolean useStyle, String group) {
        Notification.Builder builder = new Notification.Builder(context)
                .setSmallIcon(Icon.createWithResource(context, R.mipmap.ic_launcher))
                .setLargeIcon(Icon.createWithResource(context, R.drawable.ic_account_circle_black_48dp))

                .setGroupSummary(true);

        if (useStyle) {
            builder.setStyle(getMessageBoxStyleFromMessages(messages));
        }

        if (group != null) {
            builder.setGroup(group);
        }

        Notification notification = builder.build();
        NotificationManagerCompat notificationManager =  NotificationManagerCompat.from(context);
        notificationManager.notify(getNotificationId(messages), notification);
    }

    public static void showNotification(Context context, ConversationMessage message, String group) {
        Notification.Builder builder = new Notification.Builder(context)
                .setSmallIcon(Icon.createWithResource(context, R.mipmap.ic_launcher))
                .setLargeIcon(Icon.createWithResource(context, R.drawable.ic_account_circle_black_48dp))
                .setContentTitle(message.message)
                .setSubText(message.from);

        if (group != null) {
            builder.setGroup(group);
        }

        Notification notification = builder.build();
        NotificationManagerCompat notificationManager =  NotificationManagerCompat.from(context);
        notificationManager.notify(getNotificationId(message), notification);
    }

    public static void showStyledMessagingNotification(Context context, List<ConversationMessage> messages, String group) {

        // intent for handling actions
        Notification.Action action = getRemoteInputAction(context);

        Notification.Builder builder = new Notification.Builder(context)
                .setSmallIcon(Icon.createWithResource(context, R.mipmap.ic_launcher))
                .setLargeIcon(Icon.createWithResource(context, R.drawable.ic_account_circle_black_48dp))
                .setStyle(getMessageBoxStyleFromMessages(messages))
                // Add quick reply action here
                .addAction(action);

        if (group != null) {
            builder.setGroup(group);
        }

        Notification notification = builder.build();
        NotificationManagerCompat notificationManager =  NotificationManagerCompat.from(context);
        notificationManager.notify(getNotificationId(messages), notification);
    }

    public static void showCustomViewNotification(Context context, List<ConversationMessage> messages) {

        Notification notification = new Notification.Builder(context)
                .setSmallIcon(Icon.createWithResource(context, R.mipmap.ic_launcher))
                .setLargeIcon(Icon.createWithResource(context, R.drawable.ic_account_circle_black_48dp))
                .setStyle(getMessageBoxStyleFromMessages(messages))
                //TODO use proper RemoteViews
                .setCustomContentView(new RemoteViews(context.getPackageName(), R.layout.activity_main))
                .setStyle(new Notification.DecoratedCustomViewStyle())
                .setGroup(String.valueOf(messages.get(0).conversationId))
                .build();

        NotificationManagerCompat notificationManager =  NotificationManagerCompat.from(context);
        notificationManager.notify(getNotificationId(messages), notification);
    }

    private static Notification.Action getRemoteInputAction(Context context) {
        RemoteInput remoteInput = new  RemoteInput.Builder(RemoteInputReceiver.KEY_QUICK_REPLY_TEXT)
                .setLabel("Quick Reply")
                .build();

        Notification.Action replyAction = new Notification.Action.Builder(Icon.createWithResource(context,
                R.drawable.ic_chat_bubble_white_24dp),
                "REPLY", getRemoteInputIntent(context))
                .addRemoteInput(remoteInput)
                .build();

        return replyAction;
    }

    private static Notification.Style getInboxStyleFromMessages(List<ConversationMessage> messages) {
        Notification.InboxStyle style = new Notification.InboxStyle()
                .setBigContentTitle(messages.size() + " new messages")
                .setSummaryText("johndoe@gmail.com");
        for (ConversationMessage msg : messages) {
            style.addLine(String.format("%s: %s", msg.selfMessage ? "Me" : msg.from, msg.message));
        }
        return style;
    }

    private static Notification.Style getMessageBoxStyleFromMessages(List<ConversationMessage> messages) {
        Notification.MessagingStyle style = new Notification.MessagingStyle("Me");
        style.setConversationTitle("Conversation title");
        long time = 1000;
        for (ConversationMessage msg : messages) {
            style.addMessage(msg.message,  ++time, msg.selfMessage ? null : msg.from);
        }
        return style;
    }

    private static int getNotificationId(ConversationMessage message) {
        return message.messageId;
    }

    private static int getNotificationId(List<ConversationMessage> messages) {
        if (messages.size() > 0) {
            return messages.get(0).conversationId;
        }
        return 5555;
    }

    private static PendingIntent getRemoteInputIntent(Context context) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setClass(context, RemoteInputReceiver.class);
        int requestCode = 9999;
        return PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}

