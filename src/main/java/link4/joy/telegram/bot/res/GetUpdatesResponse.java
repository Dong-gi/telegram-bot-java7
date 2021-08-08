package link4.joy.telegram.bot.res;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import link4.joy.telegram.bot.type.Update;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetUpdatesResponse extends BaseResponse {
    @JsonProperty("result")
    public Update[] result;
}
