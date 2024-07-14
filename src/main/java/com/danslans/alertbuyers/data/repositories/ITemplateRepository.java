package com.danslans.alertbuyers.data.repositories;

import com.danslans.alertbuyers.business.models.entities.TemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITemplateRepository extends JpaRepository<TemplateEntity, Long> {

    TemplateEntity findByActiveIsTrue();
}
