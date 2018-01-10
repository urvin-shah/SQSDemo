package com.urvin.sqs.SQSDemo.simple;

import com.amazonaws.services.sqs.model.Message;

import java.util.List;
import java.util.Map;

public class SQSQueueDemo {
    public static void main(String[] args) {
        SQSQueue queue = new SQSQueue("MessageQueue");

        // Send message
        queue.sendMessage("This is sample message for MessageQueue");
        // Receive the message and display it
        List<Message> messages = queue.retrieveMessages();
        System.out.println("Total messages sent:"+messages.size());
        messages.forEach(message -> {
            System.out.println("  Message");
            System.out.println("    MessageId:     " + message.getMessageId());
            System.out.println("    ReceiptHandle: " + message.getReceiptHandle());
            System.out.println("    MD5OfBody:     " + message.getMD5OfBody());
            System.out.println("    Body:          " + message.getBody());
            for (Map.Entry<String, String> entry : message.getAttributes().entrySet()) {
                System.out.println("  Attribute");
                System.out.println("    Name:  " + entry.getKey());
                System.out.println("    Value: " + entry.getValue());
            }
        });

        // Delete single message
//        queue.deleteMessage(messages.get(0));

        //  Delete Queue
        //queue.deleteQueue();

    }
}
