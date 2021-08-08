package link4.joy.telegram.bot.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class BotCommand {
    /**
     * 1-32 characters. Can contain only lowercase English letters, digits and underscores
     */
    @JsonProperty("command")
    public String command;
    /**
     * 3-256 characters
     */
    @JsonProperty("description")
    public String description;
}
