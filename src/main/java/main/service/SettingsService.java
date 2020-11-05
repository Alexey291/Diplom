package main.service;

import main.entity.GlobalSettings;
import main.entity.GlobalSettingsRepository;
import main.api.response.SettingsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {
    @Autowired
    private GlobalSettingsRepository globalSettingsRepository;
    public SettingsResponse getGlobalSettings(){
        SettingsResponse settingsResponse = new SettingsResponse();
        GlobalSettings globalSettings = globalSettingsRepository.getRecentSettings();
        int first = globalSettings.getValue().indexOf(':') + 1;
        int second = globalSettings.getValue().indexOf(' ');
        int last = globalSettings.getValue().lastIndexOf(':') + 1;
        settingsResponse.setMultiuserMode(Boolean.parseBoolean(globalSettings.getValue().substring(first,second)));
        settingsResponse.setStatisticsPublic(Boolean.parseBoolean(globalSettings.getValue().substring(last)));
        settingsResponse.setPostPremoderation(false);
        return settingsResponse;
    }
}
