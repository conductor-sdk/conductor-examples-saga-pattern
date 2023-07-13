package io.orkes.example.saga.service;

import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import io.orkes.conductor.client.WorkflowClient;
import io.orkes.example.saga.pojos.BookingRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class WorkflowService {

    private final WorkflowClient workflowClient;
    private final Environment environment;
    private final String TASK_DOMAIN_PROPERTY = "conductor.worker.all.domain";

    public Map<String, Object> startRideBookingWorkflow(BookingRequest bookingRequest) {
        UUID uuid = UUID.randomUUID();
        String bookingRequestId = uuid.toString();
        bookingRequest.setBookingRequestId(bookingRequestId);

        StartWorkflowRequest request = new StartWorkflowRequest();
        request.setName("cab_service_saga_booking_wf");
        request.setVersion(1);
        request.setCorrelationId(bookingRequestId);

        String domain = environment.getProperty(TASK_DOMAIN_PROPERTY, String.class, "");

        if (!domain.isEmpty()) {
            Map<String, String> taskToDomain = new HashMap<>();
            taskToDomain.put("*", domain);
            request.setTaskToDomain(taskToDomain);
        }

        Map<String, Object> inputData = new HashMap<>();
        inputData.put("riderId", bookingRequest.getRiderId());
        inputData.put("dropOffLocation", bookingRequest.getDropOffLocation());
        inputData.put("pickUpLocation", bookingRequest.getPickUpLocation());
        request.setInput(inputData);

        String workflowId = "";
        try {
            workflowId = workflowClient.startWorkflow(request);
            log.info("Workflow id: {}", workflowId);
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            return Map.of("error", "Booking creation failure", "detail", ex.toString());
        }

        return Map.of("workflowId", workflowId);
    }
}
