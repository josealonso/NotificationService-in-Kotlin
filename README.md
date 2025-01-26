# Notification Service written in Kotlin and using Spring Boot 3.x

This is a simple service to send notifications to users via email.

## Requirements

Build a notification service that subscribes to a message queue containing order events, retrieves additional data from external services (Users and Orders), and builds a notification email to be sent.

The notification service must subscribe to a message queue to receive order status events. This task is not required to use a real queue broker, just mock it

After receiving an event it should request additional data to build a notification data (mock the services that return fake data)

Orders service to request order data (for the task order details are not important, you can add a couple of fields, e.q. name, price) + it contains a related userId

Users service to request user data (name, email) by userId

Once the notification service has the user and order data, it should construct an object that will be passed to a predefined email template.

The final step is to send the email notification to the user using an email sending service. Please mock it for this task as well.

### Steps

1.- Notifications service subscribes to a queue.
2.- It receives an order status event.
3.- Request users and Orders service to get required data.
4.- Build a notification data object.
5.- Set the data to a template and send an email.

## Code Overview

- User entity
Fields: name, email, phoneNumber.

- Order entity
Fields: id, details.

- Order event
Fields: orderId, status, notificationType.

- Email data object
Fields: userData, orderData, orderStatus.

## Testing the basic Kafka endpoint

```bash
curl --location --request POST 'localhost:8080/api/v1/test' \
--header 'Content-Type: application/json' \
--data-raw '{
    "message": "SomeMessage"
}'
```

Response:

```
Message received: [SomeMessage]
Message received: [ExampleDto(someMessage=SomeMessage)]
Message received: [UserDto(id=26, name=SomeMessage)]
```
