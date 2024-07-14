package com.danslans.alertbuyers.business.services.impl;

import com.danslans.alertbuyers.business.services.IForecastUseCaseService;
import com.danslans.alertbuyers.data.repositories.IForecastCodesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForecastUseCaseService implements IForecastUseCaseService {
    IForecastCodesRepository forecastCodesRepository;

    @Autowired
    public ForecastUseCaseService(IForecastCodesRepository forecastCodesRepository) {
        this.forecastCodesRepository = forecastCodesRepository;
    }

    @Override
    public Boolean getExistsCode(int code) {
        return forecastCodesRepository.existsByCode(code);
    }
}
