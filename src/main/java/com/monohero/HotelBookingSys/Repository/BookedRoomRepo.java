package com.monohero.HotelBookingSys.Repository;

import com.monohero.HotelBookingSys.Entity.BookedRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookedRoomRepo extends JpaRepository<BookedRoom,Long> {
}
