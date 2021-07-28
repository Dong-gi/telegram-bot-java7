package link4.joy.telegram.bot.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class ReplyKeyboardMarkup implements ReplyMarkup {
    public KeyboardButton[][] keyboard;
    public Boolean resize_keyboard = Boolean.TRUE;
    public Boolean one_time_keyboard = Boolean.TRUE;
    public String input_field_placeholder;
    public Boolean selective;
}
