package com.piggyBank.repos;

import com.piggyBank.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface UsersRepository extends JpaRepository<Users, Integer> {

    Users findByEmail(String email);

    @Modifying
    @Query("update Users us set us.username = :username, us.password = :password where us.id = :id")
    void updateUser(@Param("id") Integer id, @Param("username") String username, @Param("password") String password);

//    , @Param("email") String email, @Param("status") Boolean status
//    Optional<Users> findById(Integer id);
//, us.email = :email, us.status = :status
}
