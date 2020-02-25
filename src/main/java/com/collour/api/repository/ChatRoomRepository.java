package com.collour.api.repository;

import com.collour.api.domain.ChatRoom;
import com.collour.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    List<ChatRoom> findByUserListContainsOrderByNoDesc(User user);
}
