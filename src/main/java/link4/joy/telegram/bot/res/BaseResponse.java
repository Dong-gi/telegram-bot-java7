package link4.joy.telegram.bot.res;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponse {
    public static final BaseResponse NO_HANDLER = new BaseResponse(true, "NO_HANDLER");
    public static final BaseResponse NOTHING_TO_DO = new BaseResponse(true, "NOTHING TO DO");

    public boolean ok;
    public String description;

    public BaseResponse() {
    }

    public BaseResponse(boolean ok, String description) {
        this.ok = ok;
        this.description = description;
    }

    public boolean isNoHandler() {
        return this == NO_HANDLER;
    }

    public boolean isNothingToDo() {
        return this == NOTHING_TO_DO;
    }
}
