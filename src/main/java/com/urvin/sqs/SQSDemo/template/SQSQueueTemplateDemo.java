package com.urvin.sqs.SQSDemo.template;

public class SQSQueueTemplateDemo {
    public static void main(String[] args) {
        SQSQueueTemplate sqsTemplate = new SQSQueueTemplate("MessageQueueTemplate");
        MessageToProcess messageToProcess = new MessageToProcess("This is test message for template",1);
        sqsTemplate.sendMessageToMessageProcessingQueue(messageToProcess);
        MessageToProcess returnMessage = sqsTemplate.retrieveMessage();

        System.out.println("Message is:"+returnMessage.getMessage());
        System.out.println("Priority:"+returnMessage.getPriority());

        //sqsTemplate.deleteQueue();
    }
}
