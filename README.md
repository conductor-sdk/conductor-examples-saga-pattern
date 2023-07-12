# conductor-examples-saga-pattern
Sample Java Application demonstrating Saga microservice architecture pattern for a cab booking app

## Running this Example

### Workflow Setup on Orkes Playground
1. Create booking workflow by uploading workflow definition - [cab_service_saga_booking_wf.json](src/main/resources/cab_service_saga_booking_wf.json)
2. Create cancellation workflow by uploading workflow definition - [cab_service_saga_cancellation_wf.json](src/main/resources/cab_service_saga_cancellation_wf.json)

### Running the application and workers locally

1. Clone the project to your local
2. Update the application.properties with a `orkes.security.client.key` and `orkes.security.client.secret` that can connect to your conductor instance at `orkes.conductor.server.url`
    1. For connecting to playground - refer to this [article](https://orkes.io/content/how-to-videos/access-key-and-secret) to get a key and secret
    2. For connecting locally - follow the instructions [here (Install and Run Locally)](https://orkes.io/content/get-orkes-conductor)
3. From the root project folder, run `mvn spring-boot:run`

### Booking Creation

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
   
A successful booking creation workflow run will look like this:

<img width="541" alt="Screenshot 2023-07-11 at 10 11 13 PM" src="https://github.com/conductor-sdk/conductor-examples-saga-pattern/assets/127052609/b0f0a18c-2dd7-4e16-b0ed-67ba0c017492">

   
#### Triggering the cancellation workflow to simulate rollback of distributed transactions

* Create a booking for rider 3 who doesn't have a payment method seeded.
   ```
   curl --location 'https://play.orkes.io/api/workflow' \
   --header 'X-Authorization: <JWT Token>' \
   --data '{
       "name": "cab_service_saga_booking_wf",
       "version": 1,
       "input": {
           "pickUpLocation": "150 East 52nd Street, New York, NY 10045",
           "dropOffLocation": "120 West 81st Street, New York, NY 10012",
           "riderId": 3
       }
   }'
   ```

* This will cause the workflow to fail and trigger the cancellation workflow.
