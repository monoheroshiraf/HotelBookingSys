package com.monohero.HotelBookingSys.Repository;

import com.monohero.HotelBookingSys.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepo extends JpaRepository<Room,Long> {
}
