package ru.sergalas.amplicodebot.bot.services;

import ru.sergalas.amplicodebot.bot.data.ExpertData;

import java.util.List;
import java.util.Locale;

public interface ExpertsService {

    public List<ExpertData> listExperts(Locale locale);
    public ExpertData getExpertById(String id, Locale locale);
}
