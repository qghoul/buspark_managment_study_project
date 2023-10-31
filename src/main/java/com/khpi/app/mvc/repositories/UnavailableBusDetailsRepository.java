package com.khpi.app.mvc.repositories;

import com.khpi.app.mvc.models.UnavailableBusDetails;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnavailableBusDetailsRepository
        extends JpaRepository<UnavailableBusDetails, Integer>
{
    Optional<UnavailableBusDetails> findFirstByBus_id(Integer bus_id);
}
