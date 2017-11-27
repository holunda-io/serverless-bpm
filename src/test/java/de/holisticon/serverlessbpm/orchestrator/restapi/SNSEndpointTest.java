package de.holisticon.serverlessbpm.orchestrator.restapi;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SNSEndpointTest {

    @Mock
    private NotificationHandler notificationHandler;

    @Mock
    private SubscriptionConfirmationHandler subscriptionConfirmationHandler;

    private SNSHandlerManager snsHandlerManager;

    private SNSEndpoint endpoint;

    @Before
    public void setupMocks() {
        snsHandlerManager = new SNSHandlerManager(notificationHandler, subscriptionConfirmationHandler);
        endpoint = new SNSEndpoint(snsHandlerManager);
    }

    @Test
    public void handleSubscriptionConfirmation() {
        endpoint.receive("SubscriptionConfirmation",
                "{\n" +
                "  \"Type\" : \"SubscriptionConfirmation\",\n" +
                "  \"MessageId\" : \"165545c9-2a5c-472c-8df2-7ff2be2b3b1b\",\n" +
                "  \"Token\" : \"2336412f37fb687f5d51e6e241d09c805a5a57b30d712f794cc5f6a988666d92768dd60a747ba6f3beb71854e285d6ad02428b09ceece29417f1f02d609c582afbacc99c583a916b9981dd2728f4ae6fdb82efd087cc3b7849e05798d2d2785c03b0879594eeac82c01f235d0e717736\",\n" +
                "  \"TopicArn\" : \"arn:aws:sns:us-west-2:123456789012:MyTopic\",\n" +
                "  \"Message\" : \"You have chosen to subscribe to the topic arn:aws:sns:us-west-2:123456789012:MyTopic.\\nTo confirm the subscription, visit the SubscribeURL included in this message.\",\n" +
                "  \"SubscribeURL\" : \"https://sns.us-west-2.amazonaws.com/?Action=ConfirmSubscription&TopicArn=arn:aws:sns:us-west-2:123456789012:MyTopic&Token=2336412f37fb687f5d51e6e241d09c805a5a57b30d712f794cc5f6a988666d92768dd60a747ba6f3beb71854e285d6ad02428b09ceece29417f1f02d609c582afbacc99c583a916b9981dd2728f4ae6fdb82efd087cc3b7849e05798d2d2785c03b0879594eeac82c01f235d0e717736\",\n" +
                "  \"Timestamp\" : \"2012-04-26T20:45:04.751Z\",\n" +
                "  \"SignatureVersion\" : \"1\",\n" +
                "  \"Signature\" : \"EXAMPLEpH+DcEwjAPg8O9mY8dReBSwksfg2S7WKQcikcNKWLQjwu6A4VbeS0QHVCkhRS7fUQvi2egU3N858fiTDN6bkkOxYDVrY0Ad8L10Hs3zH81mtnPk5uvvolIC1CXGu43obcgFxeL3khZl8IKvO61GWB6jI9b5+gLPoBc1Q=\",\n" +
                "  \"SigningCertURL\" : \"https://sns.us-west-2.amazonaws.com/SimpleNotificationService-f3ecfb7224c7233fe7bb5f59f96de52f.pem\"\n" +
                "  }");
    }

    @Test
    public void handleNotification() {
        endpoint.receive("Notification", "{\n" +
                "  \"Type\" : \"Notification\",\n" +
                "  \"MessageId\" : \"22b80b92-fdea-4c2c-8f9d-bdfb0c7bf324\",\n" +
                "  \"TopicArn\" : \"arn:aws:sns:us-west-2:123456789012:MyTopic\",\n" +
                "  \"Subject\" : \"ce8ed5a9-fcdf-40d9-a61c-4024e23f2101\",\n" +
                "  \"Message\" : \"EchoResponse\",\n" +
                "  \"Timestamp\" : \"2012-05-02T00:54:06.655Z\",\n" +
                "  \"SignatureVersion\" : \"1\",\n" +
                "  \"Signature\" : \"EXAMPLEw6JRNwm1LFQL4ICB0bnXrdB8ClRMTQFGBqwLpGbM78tJ4etTwC5zU7O3tS6tGpey3ejedNdOJ+1fkIp9F2/LmNVKb5aFlYq+9rk9ZiPph5YlLmWsDcyC5T+Sy9/umic5S0UQc2PEtgdpVBahwNOdMW4JPwk0kAJJztnc=\",\n" +
                "  \"SigningCertURL\" : \"https://sns.us-west-2.amazonaws.com/SimpleNotificationService-f3ecfb7224c7233fe7bb5f59f96de52f.pem\",\n" +
                "  \"UnsubscribeURL\" : \"https://sns.us-west-2.amazonaws.com/?Action=Unsubscribe&SubscriptionArn=arn:aws:sns:us-west-2:123456789012:MyTopic:c9135db0-26c4-47ec-8998-413945fb5a96\"\n" +
                "  }");
    }
}