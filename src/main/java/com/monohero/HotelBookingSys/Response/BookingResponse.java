package com.monohero.HotelBookingSys.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {

    private Long id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String guestFullName;
    private String guestEmail;
    private int noOfAdults;
    private int noOfChildren;
    private int totalNoOfGuests;
    private String bookingConfirmationCode;
    private RoomResponse room;

    public BookingResponse(Long id, LocalDate checkInDate, LocalDate checkOutDate,
                           String bookingConfirmationCode) {
        this.id = id;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingConfirmationCode = bookingConfirmationCode;
    }


}
