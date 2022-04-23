package com.alphabetas.nymosgroup.repo;

import com.alphabetas.nymosgroup.model.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessagesRepo extends JpaRepository<Messages, Long> {
    List<Messages> findAllByChat(String chat);
}
