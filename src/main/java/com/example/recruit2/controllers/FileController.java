package com.example.recruit2.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.recruit2.DTO.OfferRequest;
import com.example.recruit2.models.Candidate;
import com.example.recruit2.repository.CandidateRepository;
import com.example.recruit2.service.ExcelReportService;
import com.example.recruit2.service.PdfGeneratorService;
import com.itextpdf.io.exceptions.IOException;

@RestController
public class FileController {

  private final PdfGeneratorService pdfGeneratorService;
private final ExcelReportService reportService;
private final CandidateRepository candidateRepository;
    public FileController(PdfGeneratorService pdfGeneratorService,ExcelReportService reportService,CandidateRepository candidateRepository) {
        this.pdfGeneratorService = pdfGeneratorService;
        this.reportService = reportService;
        this.candidateRepository=candidateRepository;
    }

    @PostMapping("/download")
    public ResponseEntity<byte[]> downloadPdf(@RequestBody OfferRequest request) throws Exception {
        byte[] pdfBytes = pdfGeneratorService.generateProtectedPdf(request);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=offer.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
     @GetMapping("/download")
    public ResponseEntity<byte[]> downloadReport() throws java.io.IOException {
        try {
            List<Candidate>candidates=candidateRepository.findAll();
            byte[] report = reportService.generateMonthlyReport(candidates);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=report.xlsx");

            return new ResponseEntity<>(report, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
