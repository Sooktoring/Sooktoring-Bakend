package com.project.sooktoring.repository;

import com.project.sooktoring.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Modifying
    @Query("delete from ChatRoom cr where cr.id in :chatIds")
    void deleteAllById(List<Long> chatIds);
}
