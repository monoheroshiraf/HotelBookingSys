package com.monohero.HotelBookingSys.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookedRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "check_in")
    private LocalDate checkInDate;

    @Column(name = "check_out")
    private LocalDate checkOutDate;

    @Column(name = "guest_full_name")
    private String guestFullName;

    @Column(name = "guest_email")
    private String guestEmail;

    @Column(name = "adults")
    private int noOfAdults;

    @Column(name = "children")
    private int noOfChildren;

    @Column(name = "total_guests")
    private int totalNoOfGuests;

    @Column(name = "confirmation_code")
    private String bookingConfirmationCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    public void calcTotalNoOfGuests(){
        this.totalNoOfGuests = this.noOfChildren + this.noOfAdults;
    }

    public void setNoOfAdults(int noOfAdults) {
        this.noOfAdults = noOfAdults;
        calcTotalNoOfGuests();
    }

    public void setNoOfChildren(int noOfChildren) {
        this.noOfChildren = noOfChildren;
        calcTotalNoOfGuests();
    }



}
