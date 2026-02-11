package ru.sergalas.amplicodebot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_session")
public class UserSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "locale")
    private String locale;

    @Column(name = "first_name")
    private String firstName;

    public UserSession(Long chatId) {
        this.chatId = chatId;
        this.locale = "ru";
    }

}