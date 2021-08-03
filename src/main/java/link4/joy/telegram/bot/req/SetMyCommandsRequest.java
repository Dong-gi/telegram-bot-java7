package link4.joy.telegram.bot.req;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import link4.joy.telegram.bot.type.BotCommand;

@JsonInclude(Include.NON_NULL)
public class SetMyCommandsRequest {
    public List<BotCommand> commands = new ArrayList<>();
}
