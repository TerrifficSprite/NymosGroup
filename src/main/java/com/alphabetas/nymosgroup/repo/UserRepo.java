package com.alphabetas.nymosgroup.repo;

import com.alphabetas.nymosgroup.model.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<ClientModel, Long> {
    ClientModel findByUsername(String username);
    ClientModel findByUsernameAndPassword(String username, String password);
}
