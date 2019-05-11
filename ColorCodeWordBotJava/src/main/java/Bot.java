import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;

public class Bot extends TelegramLongPollingBot {

    public void onUpdateReceived(Update update) {
        String receivedWord = update.getMessage().getText();

        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());

        Word word = new Word("de","en",receivedWord);
        String[] result = new String[2];
        try {
            result = word.getResponse();
        } catch (IOException e) {
            sendMessage.setText("hmm the word is invalid");
        }

        sendMessage.setText(result[0]+"\n"+result[1]+"\n"+result[2]);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    public void onUpdatesReceived(List<Update> updates) {

    }

    public String getBotUsername() {
        return "ColorCodeWord";
    }


    @Override
    public String getBotToken() {
        return "token";
    }
}
