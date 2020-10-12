package main.service;

import main.Entity.GlobalSettings;
import main.Entity.GlobalSettingsRepository;
import main.api.response.SettingsResponse;
import org.hibernate.property.access.spi.Getter;
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
