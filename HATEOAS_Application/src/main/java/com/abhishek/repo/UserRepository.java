package com.abhishek.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhishek.entity.User_Tab;

public interface UserRepository extends JpaRepository<User_Tab, Long> {
	User_Tab findByName(String name);
	User_Tab findByEmail(String email);

}
