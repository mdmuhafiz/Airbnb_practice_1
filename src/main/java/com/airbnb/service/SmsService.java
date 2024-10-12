package com.airbnb.service;

import com.airbnb.config.TwilioConfig;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    @Autowired
    private TwilioConfig twilioConfig;

    public String sendSms(String to, String body) {
        try {
            // Create a message using Twilio API
            Message message = Message.creator(
                    new PhoneNumber(to),               // The recipient phone number
                    new PhoneNumber(twilioConfig.getTwilioPhoneNumber()), // Twilio phone number
                    body                               // SMS body content
            ).create();

            return "SMS sent successfully with SID: " + message.getSid();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred while sending SMS: " + e.getMessage();
        }
    }
}
