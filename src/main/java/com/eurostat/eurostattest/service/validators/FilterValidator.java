package com.eurostat.eurostattest.service.validators;

import com.eurostat.eurostattest.domain.Filter;
import com.eurostat.eurostattest.service.annotations.FilterConstraint;
import com.eurostat.eurostattest.service.mapper.ObjectToFilterMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class FilterValidator implements ConstraintValidator <FilterConstraint, Set<Object>> {
    @Override
    public void initialize(FilterConstraint filters) {
    }

    @Override
    public boolean isValid(Set<Object> filters, ConstraintValidatorContext cxt) {

        return filters.stream()
            .map(ObjectToFilterMapper::createFilterFromObject)
            .filter(Objects::nonNull)
            .map(Filter::getConfiguration)
            .filter(this::filterNotValid)
            .findAny()
            .isEmpty();
    }

    private boolean filterNotValid(String filter) {

        JSONObject json = null;
        try {
            json = new JSONObject(filter);
        } catch (JSONException e) {
            e.printStackTrace();
            return true;
        }

        Iterator<String> iterator = json.keys();
        while (iterator.hasNext()) {
            try {
                String key = iterator.next();
                Object value = json.get(key);

                if (JSONObject.NULL.equals(value)) {
                    return true;
                } else if (value instanceof Integer || value instanceof Long) {
                    if (((Number) value).longValue() < 0) {
                        return true;
                    }
                } else {
                    if (StringUtils.isBlank(json.getString(key))) {
                        return true;
                    }
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
                return true;
            }
        }
        return false;
    }
}
