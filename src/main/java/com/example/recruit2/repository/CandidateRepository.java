package com.example.recruit2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.recruit2.models.Candidate;

import jakarta.transaction.Transactional;

public interface CandidateRepository extends JpaRepository<Candidate,Integer>{
    Page<Candidate> findAll(Pageable pageable);
     @Transactional
    @Modifying
    @Query("UPDATE Candidate c SET c.meetDate = :meetDate WHERE c.fullname = :fullname")
    int updateMeetDateByFullname(@Param("meetDate") String meetDate, @Param("fullname") String fullname);
}
