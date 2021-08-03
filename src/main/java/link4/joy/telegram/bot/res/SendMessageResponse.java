package link4.joy.telegram.bot.res;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import link4.joy.telegram.bot.type.Message;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SendMessageResponse extends BaseResponse {
    public Message result;
}
