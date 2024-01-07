package com.monohero.HotelBookingSys.Service.Implementations;

import com.monohero.HotelBookingSys.Entity.Room;
import com.monohero.HotelBookingSys.Exceptions.ResourceNotFoundException;
import com.monohero.HotelBookingSys.Repository.RoomRepo;
import com.monohero.HotelBookingSys.Service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {


    @Autowired
    private final RoomRepo roomRepo;
    @Override
    public Room addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice) throws SQLException, IOException {
        Room room = new Room();
        room.setRoomType(roomType);
        room.setRoomPrice(roomPrice);
        if(!photo.isEmpty()){
            byte[] photoBytes = photo.getBytes();
            Blob photoBlob = new SerialBlob(photoBytes);
            room.setPhoto(photoBlob);
        }
        return roomRepo.save(room);
    }

    @Override
    public List<Room> getAll() {
        return roomRepo.findAll();
    }

    @Override
    public List<String> getAllRoomTypes() {
        return roomRepo.findDistinctRoomTypes();
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepo.findAll();
    }

    @Override
    public byte[] getRoomPhotoByRoomID(Long roomID) throws SQLException, ResourceNotFoundException {
        Optional<Room> room = roomRepo.findById(roomID);
        if (room.isEmpty()){
            throw new ResourceNotFoundException("Room Not Found!");
        }
        Blob photoBlob = room.get().getPhoto();
        if (photoBlob != null){
            return photoBlob.getBytes(1,(int) photoBlob.length());
        }
        return null;
    }
}
