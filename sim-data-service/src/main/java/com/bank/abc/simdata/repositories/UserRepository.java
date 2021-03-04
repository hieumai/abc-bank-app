package com.bank.abc.simdata.repositories;

import com.bank.abc.simdata.models.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.phoneNumber = :phoneNumber")
    User findOneByPhoneNumber(@Param("phoneNumber") String username);
}
