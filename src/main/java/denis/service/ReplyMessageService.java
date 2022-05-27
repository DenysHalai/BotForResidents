package denis.service;

import denis.FirstTestBotDjek;
import denis.InlineButtons;
import denis.model.TextMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

public class ReplyMessageService {
    private final long chatId;
    private final FirstTestBotDjek bot;

    public ReplyMessageService(long chatId, FirstTestBotDjek bot) {
        this.chatId = chatId;
        this.bot = bot;
    }

    public Message replyMessage(String textMessage, ReplyKeyboard replyKeyboard) {
        try {
            return bot.execute(
                    SendMessage.builder()
                            .text(textMessage)
                            .chatId(String.valueOf(chatId))
                            .replyMarkup(replyKeyboard)
                            .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Message replyMessage(TextMessage textMessage, ReplyKeyboard replyKeyboard) {
        return replyMessage(textMessage.getValue(), replyKeyboard);
    }

    public Message replyMessage(TextMessage textMessage, boolean hideButtons) {
        return replyMessage(textMessage, new ReplyKeyboardRemove(hideButtons));
    }

    public Message replyMessage(String textMessage, boolean hideButtons) {
        return replyMessage(textMessage, new ReplyKeyboardRemove(hideButtons));
    }

    public Message replyMessage(TextMessage textMessage) {
        return replyMessage(textMessage, true);
    }

    public Message replyMessage(String textMessage) {
       return replyMessage(textMessage, true);
    }

    public void editMessageReplyMarkup(Integer messageId, InlineKeyboardMarkup replyKeyboard) {
        try {
            bot.execute(
                    EditMessageReplyMarkup
                            .builder()
                            .messageId(messageId)
                            .chatId(String.valueOf(chatId))
                            .replyMarkup(replyKeyboard)
                            .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteMessage(Integer messageId) {
        try {
            bot.execute(
                    DeleteMessage
                            .builder()
                            .messageId(messageId)
                            .chatId(String.valueOf(chatId))
                            .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void hideButtons() {
        Message message = replyMessage("...");
        deleteMessage(message.getMessageId());
    }
}
