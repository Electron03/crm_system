package com.example.recruit2.service;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.example.recruit2.models.Candidate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class ExcelReportService {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public byte[] generateMonthlyReport(List<Candidate> candidates) throws IOException {
        // Фильтруем кандидатов за последний месяц
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
        Map<String, Long> groupedByStatus = candidates.stream()
            .filter(c -> isLastMonth(c.getDateRec(), oneMonthAgo))
            .collect(Collectors.groupingBy(Candidate::getStatus, Collectors.counting()));

        // Создаем Excel-файл
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Отчет за месяц");

        // Заголовки
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Статус");
        headerRow.createCell(1).setCellValue("Количество кандидатов");

        // Заполняем данные
        int rowNum = 1;
        for (Map.Entry<String, Long> entry : groupedByStatus.entrySet()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(entry.getKey());
            row.createCell(1).setCellValue(entry.getValue());
        }

        // Записываем в поток
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream.toByteArray();
    }

    // Проверка: является ли дата в последнем месяце
    private boolean isLastMonth(String dateRec, LocalDate oneMonthAgo) {
        try {
            LocalDate date = LocalDate.parse(dateRec, FORMATTER);
            return !date.isBefore(oneMonthAgo);
        } catch (Exception e) {
            return false; // Если дата некорректна, пропускаем
        }
    }
}
