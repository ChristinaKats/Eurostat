package com.eurostat.eurostattest.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FilterMapperTest {

    private FilterMapper filterMapper;

    @BeforeEach
    public void setUp() {
        filterMapper = new FilterMapperImpl();
    }
}
