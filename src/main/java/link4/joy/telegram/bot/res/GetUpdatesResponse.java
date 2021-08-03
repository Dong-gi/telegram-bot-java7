package link4.joy.telegram.bot.res;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import link4.joy.telegram.bot.type.Update;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetUpdatesResponse extends BaseResponse {
    public Update[] result;
}
