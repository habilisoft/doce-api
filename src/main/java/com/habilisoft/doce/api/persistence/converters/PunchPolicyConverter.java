package com.habilisoft.doce.api.persistence.converters;

import com.habilisoft.doce.api.domain.model.punch.policy.LastPunchIsOutPunchPolicy;
import com.habilisoft.doce.api.domain.model.punch.policy.PunchPolicy;
import com.habilisoft.doce.api.domain.model.punch.policy.PunchPolicyType;
import com.habilisoft.doce.api.domain.model.punch.policy.TimeRangePunchPolicy;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;

/**
 * Created on 25/12/22.
 */
@Component
public class PunchPolicyConverter {

    public  PunchPolicy getPunchPolicy(LinkedHashMap<String, Object> punchPolicyData) {
        if(punchPolicyData == null) {
            return null;
        }
        PunchPolicyType type = PunchPolicyType.valueOf(punchPolicyData.get("type").toString());

        return switch (type) {
            case LAST_PUNCH_IS_OUT -> LastPunchIsOutPunchPolicy.builder()
                    .type(PunchPolicyType.LAST_PUNCH_IS_OUT)
                    .build();
            case IN_TIME_RANGE -> TimeRangePunchPolicy.builder()
                    .inStart(parse(punchPolicyData.get("inStart").toString()))
                    .inEnd(parse(punchPolicyData.get("inEnd").toString()))
                    .type(PunchPolicyType.IN_TIME_RANGE)
                    .build();
        };
    }

    private LocalTime parse(String value) {
        if(value.contains("[")) {
            value = value.replace("[","");
            value = value.replace("]","");
            return LocalTime.parse(value, DateTimeFormatter.ofPattern("H, m"));
        }
        return LocalTime.parse(value);
    }

    public LinkedHashMap<String, Object> getPunchPolicy(PunchPolicy punchPolicy) {
        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
         switch (punchPolicy.getType()) {
            case LAST_PUNCH_IS_OUT -> data.put("type", PunchPolicyType.LAST_PUNCH_IS_OUT.name());
            case IN_TIME_RANGE -> {
                data.put("inStart", ((TimeRangePunchPolicy) punchPolicy).getInStart());
                data.put("inEnd", ((TimeRangePunchPolicy) punchPolicy).getInEnd());
                data.put("type", PunchPolicyType.IN_TIME_RANGE.name());
            }
        };

        return data;
    }
}
