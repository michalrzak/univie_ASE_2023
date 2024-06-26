package com.ase.analyticReport.presentation;

import com.ase.analyticReport.business.AnalyticReportService;

import java.lang.invoke.MethodHandles;

import com.ase.analyticReport.business.data.AnalyticReportEvent;
import com.ase.analyticReport.business.data.AnalyticReportFeedback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/analyticReport/")
public class AnalyticReportController {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());
    private final AnalyticReportService analyticReportService;

    @Autowired
    public AnalyticReportController(AnalyticReportService reportService) {
        this.analyticReportService = reportService;
    }

    @GetMapping(value = "feedback/{eventID}")
    public ResponseEntity<AnalyticReportFeedback> getAnalyticReportFeedback(
            @PathVariable String eventID) {
        LOGGER.info("GET api/v1/analyticReport/feedback/{}", eventID);
        return analyticReportService.getAnalyticReportFeedback(eventID);
    }
    @GetMapping(value = "event/{eventID}")
    public ResponseEntity<AnalyticReportEvent> getAnalyticReportEvent(
            @PathVariable String eventID) {
        LOGGER.info("GET api/v1/analyticReport/event/{}", eventID);
        return analyticReportService.getAnalyticReportEvent(eventID);
    }

    @GetMapping(value ="healthcheck")
    public ResponseEntity<Void> healthcheck() {
        return ResponseEntity.ok().build();
    }
}
