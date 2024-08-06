package com.login.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.login.model.Sell;


public interface SellRepository extends JpaRepository<Sell, Integer> {

	 @Query(value = "SELECT MAX(sellId) FROM Sell")
	Integer latestStockId();
	 
	 @Query("Select s from Sell s where s.sellId = :sellid")
	Sell findByMaxSellId(@Param("sellid")Integer latestStockId);

	 @Query("SELECT s FROM Sell s WHERE s.transDate BETWEEN :startDate AND :endDate")
	 List<Sell> findSellinfoByDate(@Param("startDate") String startDate, @Param("endDate") String endDate);

	

	

	
}

