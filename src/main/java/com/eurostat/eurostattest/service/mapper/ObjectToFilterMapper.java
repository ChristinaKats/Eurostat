package com.eurostat.eurostattest.service.mapper;

import com.eurostat.eurostattest.domain.Filter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

@UtilityClass
public class ObjectToFilterMapper {

    public static Filter createFilterFromObject(Object filter) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            String filterJson = mapper.writeValueAsString(filter);
            Filter filterObject = new Filter();

            JSONObject jsonObject = new JSONObject(filterJson);
            filterObject.setId(Long.parseLong(String.valueOf(jsonObject.get("id"))));
            filterObject.setConfiguration(String.valueOf(jsonObject.get("configuration")));

            return filterObject;
        } catch (JsonProcessingException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
