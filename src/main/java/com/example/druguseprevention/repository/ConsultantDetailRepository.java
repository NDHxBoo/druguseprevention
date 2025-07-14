package com.example.druguseprevention.repository;

import com.example.druguseprevention.entity.ConsultantDetail;
import com.example.druguseprevention.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConsultantDetailRepository extends JpaRepository<ConsultantDetail, Long> {
    ConsultantDetail findByConsultantId(Long consultantId);

    List<ConsultantDetail> findByStatus(String status);

    Optional<Object> findByConsultant(User consultant);
}
