package com.newrelic.ec.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewRelicEvent {
    private String text;
    private String env;
    private String alertType;
    private String priority;
    private String source;
}
