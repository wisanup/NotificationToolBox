package io.yooz.notificationshade;

public class ConversationMessage {

    public int conversationId;
    public int messageId;
    public String message;
    public String from;
    public boolean selfMessage;

    public ConversationMessage(int conversationId, int messageId, String message, String from, boolean selfMessage) {
        this.conversationId = conversationId;
        this.messageId = messageId;
        this.message = message;
        this.from = from;
        this.selfMessage = selfMessage;
    }
}
