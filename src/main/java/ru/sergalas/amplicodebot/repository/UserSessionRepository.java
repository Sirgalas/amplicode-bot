package ru.sergalas.amplicodebot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sergalas.amplicodebot.entity.UserSession;

import java.util.Optional;

public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    Optional<UserSession> findByChatId(Long chatId);
}