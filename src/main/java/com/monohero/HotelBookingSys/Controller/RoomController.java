package com.monohero.HotelBookingSys.Controller;

import com.monohero.HotelBookingSys.Entity.BookedRoom;
import com.monohero.HotelBookingSys.Entity.Room;
import com.monohero.HotelBookingSys.Exceptions.PhotoRetrievalException;
import com.monohero.HotelBookingSys.Response.BookingResponse;
import com.monohero.HotelBookingSys.Response.RoomResponse;
import com.monohero.HotelBookingSys.Service.BookingService;
import com.monohero.HotelBookingSys.Service.RoomService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hotel")
@CrossOrigin(origins = "http://localhost:5173")
public class RoomController {

    @Autowired
    private final RoomService roomService;
    @Autowired
    private final BookingService bookingService;


    @PostMapping("/newroom")
    public ResponseEntity<RoomResponse> addNewRoom(
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("roomType") String roomType,
            @RequestParam("roomPrice") BigDecimal roomPrice) throws SQLException, IOException {

        Room savedRoom = roomService.addNewRoom(photo,roomType,roomPrice);
        RoomResponse response = new RoomResponse(savedRoom.getId(),
                savedRoom.getRoomType(), savedRoom.getRoomPrice());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/room-types")
    public List<String> getAllRoomTypes(){
        return  roomService.getAllRoomTypes();
    }


    public ResponseEntity<List<RoomResponse>> getAllRooms() throws SQLException {
        List<Room> rooms = roomService.getAllRooms();
        List<RoomResponse> roomResponses = new ArrayList<>();

        for (Room room : rooms){
            byte[] photoBytes = roomService.getRoomPhotoByRoomID(room.getId());
            if (photoBytes != null && photoBytes.length > 0){
                String base64Photo = Base64.encodeBase64String(photoBytes);
                RoomResponse roomResponse = getRoomResponse(room);
                roomResponse.setPhoto(base64Photo);
                roomResponses.add(roomResponse);
            }
        }
        return ResponseEntity.ok(roomResponses);
    }

    private RoomResponse getRoomResponse(Room room) {
        List<BookedRoom> bookings = getAllBookingsByRoomID(room.getId());
        List<BookingResponse> bookingResponses = bookings
                .stream()
                .map(booking -> new BookingResponse(
                        booking.getId(),
                        booking.getCheckInDate(),
                        booking.getCheckOutDate(),
                        booking.getBookingConfirmationCode()
                )).toList();
        byte[] photoByte = null;
        Blob photoBlob = room.getPhoto();
        if (photoBlob != null){
            try {
                photoByte = photoBlob.getBytes(1,(int)photoBlob.length());
            }catch (SQLException e){
                throw new PhotoRetrievalException("Error retrieving photo!");
            }
        }
        return new RoomResponse(room.getId(),
                room.getRoomType(),
                room.getRoomPrice(),
                room.isBooked(),
                photoByte,
                bookingResponses);

    }

    private List<BookedRoom> getAllBookingsByRoomID(Long roomID) {
        return bookingService.getAllBookingsByRoomID(roomID);
    }
}
