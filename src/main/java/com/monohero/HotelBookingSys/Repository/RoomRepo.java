package com.monohero.HotelBookingSys.Repository;

import com.monohero.HotelBookingSys.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepo extends JpaRepository<Room,Long> {

    @Query("SELECT DISTINCT room.roomType FROM Room room")
    List<String> findDistinctRoomTypes();
}
