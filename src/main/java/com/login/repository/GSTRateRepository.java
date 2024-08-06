package com.login.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.login.model.GSTRate;


public interface GSTRateRepository extends JpaRepository<GSTRate, String> {

	@Query("SELECT DISTINCT i.cat FROM GSTRate i")
	List<String> findDistinctCat();
	
	@Query("SELECT item FROM GSTRate WHERE cat = :itemId")
    List<String> findItemNamesByItemId(@Param("itemId") String cat);

	@Query("SELECT rate FROM GSTRate WHERE item = :name")
	double findRateByItemName(@Param("name") String cat);

	@Modifying
	@Query("DELETE FROM GSTRate g WHERE g.item = :name")
	@Transactional
	int deleteGst(@Param("name") String itemName);

	


	
	
}

