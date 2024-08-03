package com.example.telebot.configuration;

import com.example.telebot.service.AiService;
import com.example.telebot.service.TelegramBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ClientConfiguration {
    private final AiService aiService;
    @Bean
    public TelegramBot getTelegramBot(
            @Value("${bot.name}") String botName,
            @Value("${bot.token}") String botToken
    ) {
        var telegramBot = new TelegramBot(botName,botToken,aiService);
        try {
            var telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(telegramBot);
        }catch (TelegramApiException e) {
            log.info("Error registering bot {}",e.getMessage());
            e.printStackTrace();
        }
        return telegramBot;
    }
}
