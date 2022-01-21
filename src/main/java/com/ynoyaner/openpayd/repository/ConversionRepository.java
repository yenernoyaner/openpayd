package com.ynoyaner.openpayd.repository;

import com.ynoyaner.openpayd.entity.Conversion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface ConversionRepository extends JpaRepository<Conversion,Long>, JpaSpecificationExecutor<Conversion> {

}
