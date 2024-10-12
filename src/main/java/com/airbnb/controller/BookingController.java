package com.airbnb.controller;

import com.airbnb.dto.BookingDto;
import com.airbnb.entity.Booking;
import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.BookingRepository;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.service.PDFService;
import com.airbnb.service.SmsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {

    private final PDFService pdfService;
    private BookingRepository bookingRepository;
    private PropertyRepository propertyRepository;
    private SmsService smsService;

    public BookingController(PDFService pdfService, BookingRepository bookingRepository, PropertyRepository propertyRepository, SmsService smsService) {
        this.pdfService = pdfService;


        this.bookingRepository = bookingRepository;
        this.propertyRepository = propertyRepository;
        this.smsService = smsService;
    }
    @PostMapping("/createbooking/{propertyId}")
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking,
                                                 @AuthenticationPrincipal PropertyUser user,
                                                 @PathVariable long propertyId){
        booking.setPropertyUser(user);
        Property property = propertyRepository.findById(propertyId).get();
        int propertyPrice = property.getNightlyPrice();
        int totalNight = booking.getTotalNights();
        int totalPrice = propertyPrice*totalNight;
        booking.setProperty(property);
        booking.setTotalPrice(totalPrice);
        Booking createdBooking = bookingRepository.save(booking);
        //create a PDF booking confirmation

        BookingDto dto = new BookingDto();
        dto.setBookingId(createdBooking.getId());
        dto.setGuestName(createdBooking.getGuestName());
        dto.setPrice(propertyPrice);
        dto.setTotalPrice(createdBooking.getTotalPrice());
        pdfService.generatePDF("E://pdf//"+"booking-confirmation-id"+createdBooking.getId()+".pdf",dto);
        smsService.sendSms("+916303524350","Your booking is confirmed,check your email for PDF Booking");
        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);

    }



}
