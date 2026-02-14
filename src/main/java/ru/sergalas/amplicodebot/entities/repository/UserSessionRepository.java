package ru.sergalas.amplicodebot.entities.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sergalas.amplicodebot.entities.entity.UserSession;

import java.util.Optional;

public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    Optional<UserSession> findByChatId(Long chatId);
}