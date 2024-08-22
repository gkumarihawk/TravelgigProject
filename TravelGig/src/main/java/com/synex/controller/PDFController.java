package com.synex.controller;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.activation.DataSource;
import jakarta.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


@RestController
public class PDFController {

    @Autowired
    private JavaMailSender emailSender;

    @GetMapping("/generate-and-send-pdf")
    public void generateAndSendPDF(String userEmail, String bookingDetails) {
        try {
            // Generate the PDF
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outputStream));
            Document document = new Document(pdfDocument);
            
            // Add booking information to the PDF
            addBookingInfoToPDF(document, bookingDetails);
            
            document.close();
            byte[] pdfData = outputStream.toByteArray();

            // Send the email with PDF attachment
            sendEmailWithAttachment(userEmail, "Booking Confirmation", "Please find the attached PDF document for your booking confirmation.", pdfData);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle error
        }
    }

    private void addBookingInfoToPDF(Document document, String bookingDetails) {
        // Adding booking information to the PDF document
        document.add(new Paragraph("Booking Details:"));
        document.add(new Paragraph(bookingDetails));
        // Maybe I need more booking related stuff
    }

    private void sendEmailWithAttachment(String to, String subject, String text, byte[] attachmentData) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            
            // Convert byte array to DataSource
            DataSource attachment = new ByteArrayDataSource(attachmentData, "application/pdf");
            helper.addAttachment("Booking_Confirmation.pdf", attachment); // Attach the PDF
            
            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            // Handle error
        }
    }
}
