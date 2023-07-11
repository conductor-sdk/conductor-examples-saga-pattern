# conductor-examples-saga-pattern
Sample Java Application demonstrating Saga microservice architecture pattern for a cab booking app

## Running this Example

To setup these workflows on Orkes Playground
1. Create booking workflow by uploading workflow definition - [cab_service_saga_booking_wf.json](src/main/resources/cab_service_saga_booking_wf.json)
2. Create cancellation workflow by uploading workflow definition - [cab_service_saga_cancellation_wf.json](src/main/resources/cab_service_saga_cancellation_wf.json)

To run this application and workers locally

1. Clone the project to your local
2. Update the application.properties with a `orkes.security.client.key` and `orkes.security.client.secret` that can connect to your conductor instance at `orkes.conductor.server.url`
    1. For connecting to playground - refer to this [article](https://orkes.io/content/how-to-videos/access-key-and-secret) to get a key and secret
    2. For connecting locally - follow the instructions [here (Install and Run Locally)](https://orkes.io/content/get-orkes-conductor)
3. From the root project folder, run `mvn spring-boot:run`

To create a booking request 

We can use two approaches:
1. Call the triggerRideBookingFlow API from within the Application
   ```
   curl --location 'http://localhost:8081/triggerRideBookingFlow' \
   --header 'Content-Type: application/json' \
   --data '{
   "pickUpLocation": "150 East 52nd Street, New York, NY 10045",
   "dropOffLocation": "120 West 81st Street, New York, NY 10012",
   "riderId": 1
   }'
   ```
2. Directly call the Orkes API for creating a workflow
   1. Generate a JWT token by following steps mentioned [here](https://orkes.io/content/access-control-and-security/applications#generating-token)
   2. Make an HTTPS request from postman/curl similar to below:
   ```
   curl --location 'https://play.orkes.io/api/workflow' \
   --header 'X-Authorization: <JWT Token>' \
   --data '{
       "name": "cab_service_saga_booking_wf",
       "version": 1,
       "input": {
           "pickUpLocation": "150 East 52nd Street, New York, NY 10045",
           "dropOffLocation": "120 West 81st Street, New York, NY 10012",
           "riderId": 1
       }
   }'
   ```