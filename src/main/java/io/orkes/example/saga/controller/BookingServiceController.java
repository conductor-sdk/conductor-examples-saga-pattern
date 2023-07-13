package io.orkes.example.saga.controller;

import io.orkes.example.saga.pojos.BookingRequest;
import io.orkes.example.saga.service.WorkflowService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestController
public class BookingServiceController {

    private final WorkflowService workflowService;

    @PostMapping(value = "/triggerRideBookingFlow", produces = "application/json")
    public ResponseEntity<Map<String, Object>> triggerRideBookingFlow(@RequestBody BookingRequest bookingRequest) {
        log.info("Starting ride booking flow for: {}", bookingRequest);
        return ResponseEntity.ok(workflowService.startRideBookingWorkflow(bookingRequest));
    }
}
