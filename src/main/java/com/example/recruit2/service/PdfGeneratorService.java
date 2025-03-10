package com.example.recruit2.service;

import com.example.recruit2.DTO.OfferRequest;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Service
public class PdfGeneratorService {

    public byte[] generateProtectedPdf(OfferRequest request) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(byteArrayOutputStream, 
            new WriterProperties().setStandardEncryption(
                null, "admin123".getBytes(),
                EncryptionConstants.ALLOW_PRINTING | EncryptionConstants.ALLOW_COPY,
                EncryptionConstants.ENCRYPTION_AES_128
            )
        );

        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument, PageSize.A4);

        // Добавляем логотип
        try (InputStream is = new ClassPathResource("static/logo.png").getInputStream()) {
            ImageData imageData = ImageDataFactory.create(is.readAllBytes());
            Image logo = new Image(imageData).setWidth(120).setHeight(50);
            document.add(logo);
        }
        
        // Разделитель
        document.add(new LineSeparator(new SolidLine()));

        // Жирный шрифт
        PdfFont font = PdfFontFactory.createFont("C:\\Windows\\Fonts\\arial.ttf", PdfEncodings.IDENTITY_H, 
        PdfFontFactory.EmbeddingStrategy.FORCE_EMBEDDED);

        // Заголовок
        document.add(new Paragraph(new Text("ОФФЕР\n").setFont(font).setFontSize(14)));

        // Данные оффера (вставляем данные пользователя)
        document.add(new Paragraph(new Text("ФИО: " + request.getFullName()+"\n").setFont(font).setFontSize(14)));
        document.add(new Paragraph(new Text("Должность: " + request.getPosition()+"\n").setFont(font).setFontSize(14)));
        document.add(new Paragraph(new Text("Дата выхода: " + request.getStartDate()+"\n").setFont(font).setFontSize(14)));
        document.add(new Paragraph(new Text("Оклад: " + request.getSalary()+"\n").setFont(font).setFontSize(14)));
        document.add(new Paragraph(new Text("Испытательный срок: " + request.getProbationPeriod()+"\n").setFont(font).setFontSize(14)));
        document.add(new Paragraph(new Text("Функциональные обязанности: " + request.getResponsibilities()+"\n").setFont(font).setFontSize(14)));
        document.add(new Paragraph(new Text("Бонусы и условия работы: " + request.getBenefits()+"\n").setFont(font).setFontSize(14)));
       
        document.close();
        return byteArrayOutputStream.toByteArray();
    }
}
