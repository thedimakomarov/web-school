package com.komarov.webschool.service;

import com.komarov.webschool.dto.PerformanceDto;

import java.util.List;

public interface PerformanceService {
    List<PerformanceDto> findDtoAll();
    PerformanceDto findDtoById(Long id);
    PerformanceDto create(PerformanceDto progressDto);
    PerformanceDto update(Long id, PerformanceDto progressDto);
    void deleteById(Long id);
}
