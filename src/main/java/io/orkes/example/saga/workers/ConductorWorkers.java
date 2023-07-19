package io.orkes.example.saga.workers;

import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.sdk.workflow.task.WorkerTask;
import io.orkes.example.saga.pojos.*;
import io.orkes.example.saga.service.BookingService;
import io.orkes.example.saga.service.CabAssignmentService;
import io.orkes.example.saga.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Component
@ComponentScan(basePackages = {"io.orkes"})
public class ConductorWorkers {
    /**
     * Note: Using this setting, up to 5 tasks will run in parallel, with tasks being polled every 200ms
     */
    @WorkerTask(value = "book_ride", threadCount = 3, pollingInterval = 300)
    public TaskResult checkForBookingRideTask(BookingRequest bookingRequest) {
        String bookingId = BookingService.createBooking(bookingRequest);

        TaskResult result = new TaskResult();
        Map<String, Object> output = new HashMap<>();

        if(bookingId != null) {
            output.put("bookingId", bookingId);
            result.setOutputData(output);
            result.setStatus(TaskResult.Status.COMPLETED);
        } else {
            output.put("bookingId", null);
            result.setStatus(TaskResult.Status.FAILED);
        }
        return result;
    }

    @WorkerTask(value = "assign_driver", threadCount = 2, pollingInterval = 300)
    public Map<String, Object> checkForDriverAssignmentTask(BookingIdRequestPayload cabAssignmentRequest) {
        int driverId = CabAssignmentService.assignDriver(cabAssignmentRequest.getBookingId());

        Map<String, Object> result = new HashMap<>();
        if(driverId != 0) {
            result.put("driverId", driverId);
            result.put("bookingId", cabAssignmentRequest.getBookingId());
        } else {
            result.put("bookingId", "error");
            result.put("driverId", "error");
        }
        return result;
    }

    @WorkerTask(value = "make_payment", threadCount = 2, pollingInterval = 300)
    public TaskResult checkForPaymentTask(PaymentRequest paymentRequest) {
        TaskResult result = new TaskResult();

        Payment payment = PaymentService.createPayment(paymentRequest);
        Map<String, Object> output = new HashMap<>();
        output.put("bookingId", payment.getBookingId());
        output.put("paymentId", payment.getPaymentId());
        output.put("paymentStatus", payment.getStatus().name());

        if(payment.getStatus() == Payment.Status.SUCCESSFUL) {
            result.setStatus(TaskResult.Status.COMPLETED);
        } else {
            output.put("error", payment.getErrorMsg());
            output.put("failed_event", "PAYMENT");
            result.setStatus(TaskResult.Status.FAILED);
        }

        result.setOutputData(output);

        return result;
    }

    @WorkerTask(value = "confirm_booking", threadCount = 2, pollingInterval = 300)
    public Map<String, Object> checkForBookingConfirmation(BookingIdRequestPayload bookingConfirmationReq) {
        Map<String, Object> result = new HashMap<>();
        Booking booking = BookingService.getBooking(bookingConfirmationReq.getBookingId());
        BookingService.confirmBooking(booking);
        return result;
    }

    @WorkerTask(value = "notify_driver", threadCount = 2, pollingInterval = 300)
    public Map<String, Object> checkForDriverNotifications(DriverNotificationRequest driverNotificationRequest) {
        Map<String, Object> result = new HashMap<>();
        return result;
    }

    @WorkerTask(value = "notify_customer", threadCount = 2, pollingInterval = 300)
    public Map<String, Object> checkForCustomerNotifications(Booking booking) {
        Map<String, Object> result = new HashMap<>();
        return result;
    }

    @WorkerTask(value = "cancel_payment", threadCount = 2, pollingInterval = 300)
    public Map<String, Object> checkForPaymentCancellations(BookingIdRequestPayload cancelPaymentRequest) {
        Map<String, Object> result = new HashMap<>();
        PaymentService.cancelPayment(cancelPaymentRequest.getBookingId());
        return result;
    }

    @WorkerTask(value = "cancel_driver_assignment", threadCount = 2, pollingInterval = 300)
    public Map<String, Object> checkForDriverAssignmentCancellations(BookingIdRequestPayload driverCancellationReq) {
        Map<String, Object> result = new HashMap<>();
        CabAssignmentService.cancelAssignment(driverCancellationReq.getBookingId());
        return result;
    }

    @WorkerTask(value = "cancel_booking", threadCount = 2, pollingInterval = 300)
    public Map<String, Object> checkForBookingCancellations(BookingIdRequestPayload cancelBookingRequest) {
        Map<String, Object> result = new HashMap<>();
        Booking booking = BookingService.getBooking(cancelBookingRequest.getBookingId());
        BookingService.cancelBooking(booking);
        return result;
    }

}
