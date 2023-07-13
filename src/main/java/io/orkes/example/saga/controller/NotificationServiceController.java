package io.orkes.example.saga.controller;

//import io.orkes.example.banking.pojos.DepositDetail;
//import io.orkes.example.banking.pojos.FraudCheckResult;
//import io.orkes.example.banking.service.FraudCheckService;
//import io.orkes.example.banking.service.WorkflowService;
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
public class NotificationServiceController {

//    private final FraudCheckService fraudCheckService;
//    private final WorkflowService workflowService;
//
//    @PostMapping(value = "/checkForFraud", produces = "application/json")
//    public ResponseEntity<FraudCheckResult> checkForFraud(@RequestBody DepositDetail depositDetail) {
//        log.info("Checking for fraud: {}", depositDetail);
//        return ResponseEntity.ok(fraudCheckService.checkForFraud(depositDetail));
//    }
//
//
//    // docs-marker-start-1
//    @PostMapping(value = "/triggerDepositFlow", produces = "application/json")
//    public ResponseEntity<Map<String, Object>> triggerDepositFlow(@RequestBody DepositDetail depositDetail) {
//        log.info("Starting deposit flow for: {}", depositDetail);
//        return ResponseEntity.ok(workflowService.startDepositWorkflow(depositDetail));
//    }

    // docs-marker-end-1

}
