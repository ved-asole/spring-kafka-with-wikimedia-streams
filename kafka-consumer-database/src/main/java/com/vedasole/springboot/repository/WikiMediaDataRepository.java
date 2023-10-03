package com.vedasole.springboot.repository;

import com.vedasole.springboot.entity.WikimediaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WikiMediaDataRepository extends JpaRepository<WikimediaData, Long> {



}
