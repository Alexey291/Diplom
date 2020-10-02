package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public class SettingsResponse {
    @JsonProperty("MULTIUSER_MODE")
    private boolean multiuserMode;
    @JsonProperty("POST_PREMODERATION")
    private boolean postPremoderation;
    @JsonProperty("STATISTICS_IS_PUBLIC")
    private boolean statisticsPublic;

    public void setStatisticsPublic(boolean statisticsPublic) {
        this.statisticsPublic = statisticsPublic;
    }

    public boolean isMultiuserMode() {
        return multiuserMode;
    }

    public void setMultiuserMode(boolean multiuserMode) {
        this.multiuserMode = multiuserMode;
    }

    public boolean isPostPremoderation() {
        return postPremoderation;
    }

    public void setPostPremoderation(boolean postPremoderation) {
        this.postPremoderation = postPremoderation;
    }

    public boolean isStatisticsPublic() {
        return statisticsPublic;
    }



}
