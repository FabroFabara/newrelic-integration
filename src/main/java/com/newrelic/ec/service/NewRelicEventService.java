package com.newrelic.ec.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.ec.utils.NewRelicEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class NewRelicEventService {
    public void createCriticalAlarm(String key, String errorDescr) {
        try {
            NewRelic.getAgent().getInsights().recordCustomEvent("CustomEvent", createEvent("error", "Critical", errorDescr));
        } catch (Exception e) {
            log.error("Error creating critical alarm for key: " + key, e);
            e.printStackTrace();
        }
    }

    private Map<String, Object> createEvent(String alertType, String priority, String descr) {
        ObjectMapper oMapper = new ObjectMapper();
        NewRelicEvent newRelicEvent = new NewRelicEvent();
        newRelicEvent.setText(descr);
        newRelicEvent.setEnv("production");
        newRelicEvent.setAlertType(alertType);
        newRelicEvent.setPriority(priority);
        newRelicEvent.setSource("MyApp");

        Map<String, Object> newRelicAttributes = oMapper.convertValue(newRelicEvent, Map.class);
        return newRelicAttributes;
    }
}
