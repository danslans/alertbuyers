package com.danslans.alertbuyers.data.repositories;

import com.danslans.alertbuyers.business.models.entities.ForecastCodesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IForecastCodesRepository extends JpaRepository<ForecastCodesEntity, Long> {

    boolean existsByCode(int code);
}
