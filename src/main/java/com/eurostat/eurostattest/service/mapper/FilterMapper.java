package com.eurostat.eurostattest.service.mapper;

import com.eurostat.eurostattest.domain.Filter;
import com.eurostat.eurostattest.service.dto.FilterDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Filter} and its DTO {@link FilterDTO}.
 */
@Mapper(componentModel = "spring", uses = { CrawlerMapper.class })
public interface FilterMapper extends EntityMapper<FilterDTO, Filter> {
    @Mapping(target = "filters", source = "filters", qualifiedByName = "id")
    FilterDTO toDto(Filter s);
}
