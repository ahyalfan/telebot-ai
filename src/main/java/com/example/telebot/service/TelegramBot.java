package com.example.telebot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    private final AiService aiService;
    private final String botName;

    public TelegramBot(String botName, String botToken, AiService aiService) {
        super(botToken); // kirim token nya ke super class
        this.botName = botName;
        this.aiService = aiService;
    }
    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            Long chatId = message.getChatId();
            log.info("message {}",message);
            String text = message.getText(); // ambil message
            String result = aiService.getContent(text);
            var sendMessage = new SendMessage(chatId.toString(),result);
            try {
                execute(sendMessage); // ini dari library bottele
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void onRegister() { // kita registrasikan dengan superclass
        super.onRegister();
    }

    @Override
    public String getBotUsername() {
        return botName;
    }
}
