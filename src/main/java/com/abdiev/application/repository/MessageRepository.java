package com.abdiev.application.repository;


import com.abdiev.application.entity.PrimeMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<PrimeMessage,Long> {
}
