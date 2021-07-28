package link4.joy.telegram.bot;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import link4.joy.telegram.bot.consts.ParseMode;
import link4.joy.telegram.bot.req.*;
import link4.joy.telegram.bot.res.*;
import link4.joy.telegram.bot.type.*;

public class TelegramBotTest {

    private final String token = "insert-your-own-token";
    private final long chatId = 529202433L;

    @Test
    public void constructorTest() {
        new TelegramBot(token);
    }

    @Test
    public void getUpdatesTest1() throws Exception {
        new TelegramBot(token).getUpdates();
    }

    @Test
    public void getUpdatesTest2() throws Exception {
        new TelegramBot(token).getUpdates(550059108);
    }

    @Test
    public void sendMessageTest1() throws Exception {
        SendMessageRequest req = new SendMessageRequest();
        req.chat_id = chatId;
        req.parse_mode = ParseMode.HTML;
        req.text = "<b>Hello World!</b>";
        new TelegramBot(token).sendMessage(req);
    }

    @Test
    public void sendMessageTest2() throws Exception {
        SendMessageRequest req = new SendMessageRequest();
        req.chat_id = chatId;
        req.parse_mode = ParseMode.HTML;
        req.text = "<b>Hello World!</b>";
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.input_field_placeholder = "test";
        markup.one_time_keyboard = true;
        markup.keyboard = new KeyboardButton[1][2];
        markup.keyboard[0][0] = new KeyboardButton();
        markup.keyboard[0][0].text = "button [0][0]";
        markup.keyboard[0][1] = new KeyboardButton();
        markup.keyboard[0][1].text = "button [0][1]";
        req.reply_markup = markup;

        SendMessageResponse res = new TelegramBot(token).sendMessage(req);
        assertTrue(res.ok);
    }

    @Test
    public void sendPhotoTest() throws Exception {
        File photo = null;
        if (new File("photo.jpg").exists())
            photo = new File("photo.jpg");
        if (new File("src/test/resources/photo.jpg").exists())
            photo = new File("src/test/resources/photo.jpg");

        assertNotEquals(null, photo);
        SendPhotoRequest req = new SendPhotoRequest();
        req.chat_id = chatId;
        req.parse_mode = ParseMode.HTML;
        req.caption = "<b>Hello World!</b>";
        req.photo = photo;
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.input_field_placeholder = "test";
        markup.one_time_keyboard = true;
        markup.keyboard = new KeyboardButton[1][2];
        markup.keyboard[0][0] = new KeyboardButton();
        markup.keyboard[0][0].text = "button [0][0]";
        markup.keyboard[0][1] = new KeyboardButton();
        markup.keyboard[0][1].text = "button [0][1]";
        req.reply_markup = markup;
        new TelegramBot(token).sendPhoto(req);
    }
}
