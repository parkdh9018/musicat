package com.musicat.data.repository;

import com.musicat.data.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User, Long> {


}
