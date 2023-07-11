package io.orkes.example.saga.workers;

import com.netflix.conductor.common.metadata.tasks.Task;
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
    @WorkerTask(value = "book_ride_saga", threadCount = 3, pollingInterval = 300)
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

    @WorkerTask(value = "assign_driver_saga", threadCount = 2, pollingInterval = 300)
    public Map<String, Object> checkForDriverAssignmentTask(CabAssignmentRequest cabAssignmentRequest) {
        int driverId = CabAssignmentService.assignDriver(cabAssignmentRequest);

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

    @WorkerTask(value = "make_payment_saga", threadCount = 2, pollingInterval = 300)
    public TaskResult checkForPaymentTask(PaymentRequest paymentRequest) {
        TaskResult result = new TaskResult();

        Payment payment = PaymentService.payForBooking(paymentRequest);
        Map<String, Object> output = new HashMap<>();

        if(payment.getStatus() == Payment.Status.SUCCESSFUL) {
            output.put("bookingId", payment.getBookingId());
            result.setStatus(TaskResult.Status.COMPLETED);
        } else {
            output.put("error", payment.getErrorMsg());
            result.setStatus(TaskResult.Status.FAILED);
        }

        result.setOutputData(output);

        return result;
    }

    @WorkerTask(value = "notify_driver_saga", threadCount = 2, pollingInterval = 300)
    public Map<String, Object> checkForDriverNotifications(DriverNotificationRequest driverNotificationRequest) {
        Map<String, Object> result = new HashMap<>();
        return result;
    }

    @WorkerTask(value = "notify_customer_saga", threadCount = 2, pollingInterval = 300)
    public Map<String, Object> checkForCustomerNotifications(Booking booking) {
        Map<String, Object> result = new HashMap<>();
        return result;
    }
}
