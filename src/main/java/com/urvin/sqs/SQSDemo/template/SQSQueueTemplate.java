package com.urvin.sqs.SQSDemo.template;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.DeleteQueueRequest;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;

import java.util.Optional;

public class SQSQueueTemplate {


    AmazonSQSAsync amazonSQSAsync;
    private QueueMessagingTemplate queueMessagingTemplate;
    private String queueName;

    public SQSQueueTemplate(String queueName) {
        this.queueName = queueName;
        this.amazonSQSAsync = AmazonSQSAsyncClientBuilder.standard()
                                .withRegion(Regions.AP_NORTHEAST_1)
                                .build();

        // Create Queue
        init();
        this.queueMessagingTemplate = new QueueMessagingTemplate(amazonSQSAsync);
        this.queueMessagingTemplate.setDefaultDestinationName(queueName);
    }

    public void init() {
        // Create a queue
        System.out.println("Creating a new SQS queue called MyQueue.\n");
        CreateQueueRequest createQueueRequest = new CreateQueueRequest(queueName);
        String myQueueUrl = amazonSQSAsync.createQueue(createQueueRequest).getQueueUrl();
    }

    public void sendMessageToMessageProcessingQueue(MessageToProcess message) {
        System.out.println("Going to send message {} over SQS"+ message);

        this.queueMessagingTemplate.convertAndSend(queueName, message);
    }

    /**
     * This method fetch message from the queue and remove it.
     * @return
     */
    public MessageToProcess retrieveMessage() {
        MessageToProcess returnMessage = this.queueMessagingTemplate.receiveAndConvert(MessageToProcess.class);

        return Optional.ofNullable(returnMessage).orElse(null);
    }

    /**
     * This method delete the queue from AWS.
     */
    public void deleteQueue() {
        System.out.println("Deleting the test queue.\n");
        amazonSQSAsync.deleteQueue(new DeleteQueueRequest(amazonSQSAsync.getQueueUrl(queueName).getQueueUrl()));
    }

}
