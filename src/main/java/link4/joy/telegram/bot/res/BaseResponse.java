package link4.joy.telegram.bot.res;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponse {
    public boolean ok;
    public String description;
}
