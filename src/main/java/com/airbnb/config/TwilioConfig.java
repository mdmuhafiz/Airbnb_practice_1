package com.airbnb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.twilio.Twilio;

@Configuration
public class TwilioConfig {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;

    @Bean
    public void initializeTwilio() {
        // Initialize the Twilio client with account SID and Auth Token
        Twilio.init(accountSid, authToken);
    }

    public String getTwilioPhoneNumber() {
        return twilioPhoneNumber;
    }
}
