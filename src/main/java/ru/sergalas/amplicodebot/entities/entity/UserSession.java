package ru.sergalas.amplicodebot.entities.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sergalas.amplicodebot.entities.enums.UserState;
import ru.sergalas.amplicodebot.entities.services.UserService;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_session")
public class UserSession {

    public static String LOCALE_EN = "en";
    public static String LOCALE_RU = "ru";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "locale")
    private String locale;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "selected_expert_id")
    private String selectedExpertId;

    @Column(name = "is_promo_activated")
    private Boolean isPromoActivated;

    @Enumerated(value = EnumType.STRING)
    UserState userState;

    public UserSession(Long chatId) {
        this.chatId = chatId;
        this.locale = "ru";
        this.userState = UserState.IDLE;
    }

}