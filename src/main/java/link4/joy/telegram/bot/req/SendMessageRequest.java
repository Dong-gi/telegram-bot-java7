package link4.joy.telegram.bot.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import link4.joy.telegram.bot.consts.ParseMode;
import link4.joy.telegram.bot.type.ReplyMarkup;

@JsonInclude(Include.NON_NULL)
public class SendMessageRequest {
    public long chat_id;
    public String text;
    public ParseMode parse_mode;
    public Long replay_to_message_id;
    public Boolean allow_sending_without_reply;
    public ReplyMarkup reply_markup;
}
