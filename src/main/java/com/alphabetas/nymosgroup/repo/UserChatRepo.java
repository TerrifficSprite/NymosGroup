package com.alphabetas.nymosgroup.repo;

import com.alphabetas.nymosgroup.model.ClientChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserChatRepo extends JpaRepository<ClientChat, Long> {
    ClientChat findByClientAndChat(String user, String chat);
    List<ClientChat> findAllByClient(String user);
}
