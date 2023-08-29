package com.tomlegodais.api.service;

import com.tomlegodais.api.dto.CreatePriceInfoDto;
import com.tomlegodais.api.dto.PriceInfoDto;
import com.tomlegodais.api.mapper.PriceInfoMapper;
import com.tomlegodais.api.model.PriceInfoModel;
import com.tomlegodais.api.repository.PriceInfoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.*;

public class PriceInfoServiceTest {

    private AutoCloseable closeable;

    @Mock
    private PriceInfoRepository repository;

    @Mock
    private PriceInfoMapper mapper;

    @InjectMocks
    private PriceInfoService service;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        assertThat(service.findAll()).isEmpty();

        verify(repository, times(1)).findAll();
        verifyNoMoreInteractions(repository);
        verifyNoInteractions(mapper);
    }

    @Test
    public void testFindById() {
        Long id = 1L;
        var expectedDto = new PriceInfoDto();
        when(repository.findById(id)).thenReturn(Optional.of(new PriceInfoModel()));
        when(mapper.modelToDto(any())).thenReturn(expectedDto);

        var result = service.findById(id);

        assertThat(result).isSameInstanceAs(expectedDto);

        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).modelToDto(any());
        verifyNoMoreInteractions(repository);
        verifyNoMoreInteractions(mapper);
    }

    @Test
    public void testCreate() {
        var createDto = new CreatePriceInfoDto();
        var expectedDto = new PriceInfoDto();
        when(mapper.createDtoToModel(createDto)).thenReturn(new PriceInfoModel());
        when(repository.save(any())).thenReturn(new PriceInfoModel());
        when(mapper.modelToDto(any())).thenReturn(expectedDto);

        var result = service.create(createDto);

        assertThat(result).isSameInstanceAs(expectedDto);

        verify(mapper, times(1)).createDtoToModel(createDto);
        verify(repository, times(1)).save(any());
        verify(mapper, times(1)).modelToDto(any());
        verifyNoMoreInteractions(mapper);
        verifyNoMoreInteractions(repository);
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }
}
