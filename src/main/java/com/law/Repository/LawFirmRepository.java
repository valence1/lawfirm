package com.law.Repository;

import com.law.Model.LawFirm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LawFirmRepository extends JpaRepository<LawFirm, Long> {


    List<LawFirm> findByLawyerNameContaining(String lawyerName);
}
