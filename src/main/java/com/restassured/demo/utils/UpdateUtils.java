package com.restassured.demo.utils;

import com.restassured.demo.models.Booking;

import java.time.LocalDate;
import java.util.Random;

public class UpdateUtils {

    private static final Random random = new Random();

    private static int previousField = -1;

    public static UpdateResult updateBooking(Booking booking) {

        int randomField;

        do {

            randomField = random.nextInt(7);

        } while (randomField == previousField);

        previousField = randomField;

        switch (randomField) {

            case 0:
                return updateFirstname(booking);

            case 1:
                return updateLastname(booking);

            case 2:
                return updateTotalPrice(booking);

            case 3:
                return updateDepositPaid(booking);

            case 4:
                return updateCheckIn(booking);

            case 5:
                return updateCheckOut(booking);

            default:
                return updateAdditionalNeeds(booking);

        }

    }

    private static final String[] FIRST_NAMES = {
            "Rohit",
            "Aakash",
            "Vikram",
            "Nitin",
            "Arun",
            "Kunal",
            "Suresh",
            "Deepak",
            "Ajay",
            "Rajesh"
    };

    private static UpdateResult updateFirstname(Booking booking) {

        String oldValue = booking.getFirstname();

        String newValue;

        do {
            newValue = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        } while (newValue.equals(oldValue));

        booking.setFirstname(newValue);

        return new UpdateResult(
                "Firstname",
                oldValue,
                newValue);
    }

    private static final String[] LAST_NAMES = {
            "Sharma",
            "Patel",
            "Singh",
            "Reddy",
            "Verma",
            "Joshi",
            "Nair",
            "Das",
            "Gupta",
            "Kulkarni"
    };

    private static UpdateResult updateLastname(Booking booking) {

        String oldValue = booking.getLastname();

        String newValue;

        do {
            newValue = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
        } while (newValue.equals(oldValue));

        booking.setLastname(newValue);

        return new UpdateResult(
                "Lastname",
                oldValue,
                newValue);
    }

    private static UpdateResult updateTotalPrice(Booking booking) {

        int oldValue = booking.getTotalprice();

        int newValue = oldValue + 500;

        booking.setTotalprice(newValue);

        return new UpdateResult(
                "Total Price",
                oldValue,
                newValue);

    }

    private static UpdateResult updateDepositPaid(Booking booking) {

        boolean oldValue = booking.isDepositpaid();

        boolean newValue = !oldValue;

        booking.setDepositpaid(newValue);

        return new UpdateResult(
                "Deposit Paid",
                oldValue,
                newValue);

    }

    private static UpdateResult updateCheckIn(Booking booking) {

        String oldValue =
                booking.getBookingdates().getCheckin();

        String newValue =
                LocalDate.parse(oldValue)
                        .plusDays(1)
                        .toString();

        booking.getBookingdates().setCheckin(newValue);

        return new UpdateResult(
                "Check-In Date",
                oldValue,
                newValue);

    }

    private static UpdateResult updateCheckOut(Booking booking) {

        String oldValue =
                booking.getBookingdates().getCheckout();

        String newValue =
                LocalDate.parse(oldValue)
                        .plusDays(1)
                        .toString();

        booking.getBookingdates().setCheckout(newValue);

        return new UpdateResult(
                "Check-Out Date",
                oldValue,
                newValue);

    }

    private static final String[] NEEDS = {
            "Breakfast",
            "Lunch",
            "Dinner",
            "WiFi",
            "Airport Pickup",
            "Extra Bed",
            "Late Checkout",
            "Early Checkin",
            "Spa",
            "Gym Access"
    };

    private static UpdateResult updateAdditionalNeeds(Booking booking) {

        String oldValue = booking.getAdditionalneeds();

        String newValue;

        do {
            newValue = NEEDS[random.nextInt(NEEDS.length)];
        } while (newValue.equals(oldValue));

        booking.setAdditionalneeds(newValue);

        return new UpdateResult(
                "Additional Needs",
                oldValue,
                newValue);
    }

    public static class UpdateResult {

        private final String fieldName;

        private final Object oldValue;

        private final Object newValue;

        public UpdateResult(String fieldName,
                            Object oldValue,
                            Object newValue) {

            this.fieldName = fieldName;
            this.oldValue = oldValue;
            this.newValue = newValue;

        }

        public String getFieldName() {

            return fieldName;

        }

        public Object getOldValue() {

            return oldValue;

        }

        public Object getNewValue() {

            return newValue;

        }

    }

}