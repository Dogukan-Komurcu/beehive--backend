package com.example.beehiveproject.repository;


import com.example.beehiveproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


//Use this repository layer for accessing database.
//We dont have to define basic queries here.
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    //This method is a derived query in Spring Data JPA.
    //Automatically generates the SQL query based on the method name.
    //SELECT * FROM user WHERE email = ?
    Optional<User> findByEmail(String email);

    // Arama (isim veya e-posta)
    List<User> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email);
    // Role göre filtreleme
    List<User> findByRole_Name(String roleName);
    // Duruma göre filtreleme
    List<User> findByStatus(User.Status status);
}
