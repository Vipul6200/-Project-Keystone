package com.zidio.keystone.controller;
import com.zidio.keystone.dto.ReportSummaryDTO;
import com.zidio.keystone.service.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/summary")
    public ReportSummaryDTO getSummary() {
        return reportService.getSummary();
    }
}
