package com.airbnb.service;

import java.io.File;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;


import com.airbnb.dto.BookingDto;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

@Component
public class PDFService {
    //private static final String filePath = "D:\\sts projects\\flight_reservation_app_dec_2023_1\\pdfdocs";


    public void generatePDF(String filepath, BookingDto dto) {

        Document document = new Document();

        try {

            PdfWriter.getInstance(document, new FileOutputStream(new File(filepath)));

            //open
            document.open();
            document.add(generateTable(dto));
            document.close();


        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();


        }
    }


    private PdfPTable generateTable(BookingDto dto) {
        PdfPTable table = new PdfPTable(2);
        PdfPCell cell;

        cell = new PdfPCell(new Phrase("Booking confirmation"));
        cell.setColspan(2);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Booking PDF"));
        cell.setColspan(2);
        table.addCell(cell);

        table.addCell("booking ID");
        table.addCell(String.valueOf(dto.getBookingId()));

        table.addCell("Guest Name");
        table.addCell(dto.getGuestName());

        table.addCell("Price per night");
        table.addCell(String.valueOf(dto.getPrice()));

        table.addCell("Total Price");
        table.addCell(String.valueOf(dto.getTotalPrice()));



        return table;




    }}


