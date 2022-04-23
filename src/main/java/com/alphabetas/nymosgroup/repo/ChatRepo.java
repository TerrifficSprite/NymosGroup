package com.alphabetas.nymosgroup.repo;

import com.alphabetas.nymosgroup.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepo extends JpaRepository<Chat, Long> {
   Chat findByCode(String code);
   Chat findByChatInvite(String chat_invite);
   void deleteById(Long id);
}
