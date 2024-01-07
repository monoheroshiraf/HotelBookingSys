package com.monohero.HotelBookingSys.Service;

import com.monohero.HotelBookingSys.Entity.Room;
import com.monohero.HotelBookingSys.Exceptions.ResourceNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface RoomService {

    Room addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice) throws SQLException, IOException;

    List<Room> getAll();

    List<String> getAllRoomTypes();

    List<Room> getAllRooms();

    byte[] getRoomPhotoByRoomID(Long id) throws SQLException, ResourceNotFoundException;
}
