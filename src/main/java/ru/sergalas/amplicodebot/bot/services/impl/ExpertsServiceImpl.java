package ru.sergalas.amplicodebot.bot.services.impl;

import org.springframework.stereotype.Service;
import ru.sergalas.amplicodebot.bot.data.ExpertData;
import ru.sergalas.amplicodebot.bot.services.ExpertsService;
import ru.sergalas.amplicodebot.bot.services.LocalizationService;
import ru.sergalas.amplicodebot.entities.entity.UserSession;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class ExpertsServiceImpl implements ExpertsService {

    private final LocalizationService localizationService;
    private static final List<String> EXPERT_IDS = Arrays.asList(
            "alexandr.shustanov",
            "pavel.kislov",
            "mikhail.polivaha",
            "fedor.sazonov",
            "ilya.sazonov",
            "rustam.kuramshin",
            "alexandr.kuchuk"
    );
    private final Map<String,ExpertData> expertByLocal = new ConcurrentHashMap<>();

    public ExpertsServiceImpl(LocalizationService localizationService) {
        this.localizationService = localizationService;
        initialExperts();
    }

    @Override
    public List<ExpertData> listExperts(Locale locale) {
        return EXPERT_IDS.stream().map(id ->getExpertById(id,locale)).toList();
    }

    @Override
    public ExpertData getExpertById(String id, Locale locale) {
        String cacheKey = "%s_%s".formatted(locale.toLanguageTag(),id);
        return expertByLocal.computeIfAbsent(cacheKey,expert -> createExpertMessage(id, locale));
    }


    private void initialExperts() {
        for( Locale locale : Arrays.asList(Locale.of(UserSession.LOCALE_RU), Locale.of(UserSession.LOCALE_EN))) {
            EXPERT_IDS.forEach(id -> {
                createExpertMessage(id,locale);
            });
        }
    }

    private ExpertData createExpertMessage(String expertId, Locale locale) {
        try{
            String fullNameKey = "expert.%s.fullName".formatted(expertId);
            String bioKey = "expert.%s.bio".formatted(expertId);
            String contactKey = "expert.%s.contacts".formatted(expertId);
            String personalPromptKey = "expert.%s.personalPrompt".formatted(expertId);
            String fullName = localizationService.getLocalizedMessage(fullNameKey,locale);
            String bio = localizationService.getLocalizedMessage(bioKey,locale);
            String contact = localizationService.getLocalizedMessage(contactKey,locale);
            String personalPrompt = localizationService.getLocalizedMessage(personalPromptKey,locale);
            String cashKey = "%s_%s".formatted(locale, expertId);
            ExpertData expertData = new ExpertData(expertId,fullName,bio,contact,personalPrompt);
            expertByLocal.put(cashKey,expertData);
            return expertData;
        } catch (Exception e){
            throw new RuntimeException(
                "Failed to load expert " + expertId + " for locale " + locale,
                e);
        }

    }

}
