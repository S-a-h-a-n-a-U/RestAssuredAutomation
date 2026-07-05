package com.restassured.demo.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restassured.demo.models.Booking;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonReader {

    public static List<Booking> getBookingData() {

        ObjectMapper mapper = new ObjectMapper();

        try {

            return mapper.readValue(
                    new File("src/main/resources/bookingData.json"),
                    new TypeReference<List<Booking>>() {}
            );

        } catch (IOException e) {

            throw new RuntimeException("Unable to read bookingData.json", e);

        }
    }
}