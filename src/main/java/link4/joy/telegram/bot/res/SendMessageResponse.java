package link4.joy.telegram.bot.res;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import link4.joy.telegram.bot.type.Message;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SendMessageResponse extends BaseResponse {
    @JsonProperty("result")
    public Message result;
}
