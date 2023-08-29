package com.tomlegodais.app.repository;

import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DummyToolRepositoryTest {

    @Test
    public void testStaticInitialization() {
        assertThat(DummyToolRepository.findByCode("CHNS")).isNotNull();
        assertThat(DummyToolRepository.findByCode("LADW")).isNotNull();
        assertThat(DummyToolRepository.findByCode("JAKD")).isNotNull();
        assertThat(DummyToolRepository.findByCode("JAKR")).isNotNull();
    }

    @Test
    public void testFindByCodeReturnsToolWhenFound() {
        var tool = DummyToolRepository.findByCode("CHNS");

        assertThat(tool).isNotNull();
        assertThat(tool.getCode()).isEqualTo("CHNS");
    }

    @Test
    public void testFindByCodeReturnsNullWhenNotFound() {
        assertThat(DummyToolRepository.findByCode("NOT FOUND")).isNull();
    }

    @Test
    public void testAddingDuplicateToolThrowsException() {
        var tool = DummyToolRepository.findByCode("CHNS");
        assertThat(tool).isNotNull();

        var thrown = assertThrows(
                IllegalArgumentException.class,
                () -> DummyToolRepository.addTool(tool));

        assertThat(thrown.getMessage()).isEqualTo("Tool with code CHNS already exists");
    }
}
