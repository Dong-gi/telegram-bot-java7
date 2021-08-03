package link4.joy.telegram.bot;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import link4.joy.telegram.bot.consts.ParseMode;
import link4.joy.telegram.bot.req.*;
import link4.joy.telegram.bot.res.*;
import link4.joy.telegram.bot.type.*;

public class TelegramBotTest {

    private final String token = ""; // insert-your-own-token
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

    @Test
    public void commandTest() throws Exception {
        TelegramBot bot = new TelegramBot(token);
        bot.setDefault(new TelegramBotCommand() {
            @Override
            public <X extends BaseResponse> BaseResponse process(TelegramBot bot, Update update) throws IOException {
                SetMyCommandsRequest req = new SetMyCommandsRequest();
                for (TelegramBotCommands command : TelegramBotCommands.values()) {
                    bot.addCommand(command.key, command);
                    BotCommand c = new BotCommand();
                    c.command = command.command;
                    c.description = command.description();
                    req.commands.add(c);
                }
                bot.setMyCommands(req);
                return null;
            }

            @Override
            public String command() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String description() {
                // TODO Auto-generated method stub
                return null;
            }
        });
        for (int i = 0; i < 5; ++i) {
            Thread.sleep(3000);
            bot.processLastUpdate();
        }
    }
}

enum TelegramBotCommands implements TelegramBotCommand {
    ECHO_V1 {
        @Override
        public String description() {
            return "Echo message v1";
        }
    },
    ECHO_V2 {
        @Override
        public String description() {
            return "Echo message v2";
        }

        @Override
        public SendMessageResponse process(TelegramBot bot, Update update) throws IOException {
            SendMessageRequest req = new SendMessageRequest();
            req.chat_id = update.message.chat.id;
            req.text = key + " >> " + update.message.text;
            return bot.sendMessage(req);
        }
    };

    public final String command;
    public final String key;

    {
        command = name().toLowerCase();
        key = '/' + command;
    }

    @Override
    public String command() {
        return command;
    }

    @Override
    public String description() {
        return command;
    }

    @Override
    public SendMessageResponse process(TelegramBot bot, Update update) throws IOException {
        SendMessageRequest req = new SendMessageRequest();
        req.chat_id = update.message.chat.id;
        req.text = "There's no handler implementation for the message;" + update.message.text;
        return bot.sendMessage(req);
    }

}