##Benötigte Rechte
###Anwendung
```SNS:Publish
SNS:Subscribe
SNS:ConfirmSubscription 
SNS:Unsubscribe
```

###Spring Cloud AWS Autodiscovery
```autoscaling:DescribeAutoScalingInstances 
cloudformation:Describe*
```

##Topics: 
Publish: cam-test
Subscribe: arn:aws:sns:eu-central-1:831064628565:cam-echo-response

##Configuration
###Environment Variables
SERVER_PORT = 5000