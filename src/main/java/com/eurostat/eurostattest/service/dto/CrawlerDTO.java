package com.eurostat.eurostattest.service.dto;

import com.eurostat.eurostattest.domain.Filter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import liquibase.pro.packaged.S;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.nio.file.attribute.FileTime;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.eurostat.eurostattest.domain.Crawler} entity.
 */
public class CrawlerDTO implements Serializable {

    private Long id;

    @NotNull
    @Pattern(regexp = "^[A-Za-z_-][A-Za-z0-9_-]*$")
    private String name;

    @NotNull
    @Min(value = -1)
    private Integer fetchInterval;

    @NotNull
    @Pattern(regexp = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")
    private String source;

    private Set<Object> filters;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFetchInterval() {
        return fetchInterval;
    }

    public void setFetchInterval(Integer fetchInterval) {
        this.fetchInterval = fetchInterval;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Set<Filter> getFilters() {
        return this.filters.stream()
            .map(this::createFilterFromObject)
            .collect(Collectors.toUnmodifiableSet());
    }

    private Filter createFilterFromObject(Object filter) {
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

    public void setFilters(Set<Object> filters) {
        this.filters = filters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CrawlerDTO)) {
            return false;
        }

        CrawlerDTO crawlerDTO = (CrawlerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, crawlerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CrawlerDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", fetchInterval=" + getFetchInterval() +
            ", source='" + getSource() + "'" +
            "}";
    }
}
