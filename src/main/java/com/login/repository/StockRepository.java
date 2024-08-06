package com.login.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.login.model.Stock;


public interface StockRepository extends JpaRepository<Stock, Integer> {
		
	@Query("SELECT batchNo FROM Stock WHERE itemId = :itemId")
    List<String> findBatchByItemId(@Param("itemId") String itemId);
	
	 @Query("SELECT s.quantity FROM Stock s WHERE s.itemId = :itemId AND s.batchNo = :batchNo")
	 Object findQuantityByItemIdAndBatchNo(@Param("itemId") String itemId, @Param("batchNo") String batchNo);

	 @Query("SELECT s.sellPrice FROM Stock s WHERE s.itemId = :itemId AND s.batchNo = :batchNo")
	 Object getSellValue(@Param("itemId") String itemid,  @Param("batchNo") String batchno);

	 
	@Modifying
	@Query("UPDATE Stock s SET s.quantity= :quantity where s.itemId = :itemId AND s.batchNo= :batchNo")
	@Transactional 
	void updateQuantity(@Param("itemId")String itemId1,@Param("batchNo") String batchNo1,@Param("quantity") Double quantity);

	 @Query("SELECT s FROM Stock s where s.itemId = :itemId AND s.batchNo= :batchNo")
	Stock findByBatchNo(@Param("itemId")String itemid, @Param("batchNo")String batchno);

	@Query("SELECT s.batchNo FROM Stock s where s.itemId = :itemId AND s.batchNo= :batchNo")
	String getBatchNo(@Param("itemId")String itemid, @Param("batchNo")String batchno);

	 @Modifying
	 @Query("DELETE FROM Stock s WHERE s.itemId = :itemId AND s.batchNo= :batchNo")
	 @Transactional 
	void deleteAllByIdInBatch(@Param("itemId")String itemid, @Param("batchNo")String batchno);

	 @Query("SELECT s FROM Stock s where s.expDate <= :exp")
	List<Stock> findByExpiry(@Param("exp")String formattedDate);

	 @Modifying
	 @Query("DELETE FROM Stock s WHERE s.expDate <= :expiryDate")
	 @Transactional
	 int deleteByExpiry(@Param("expiryDate") String expiryDate);

	 @Modifying
	 @Query("DELETE FROM Stock s WHERE s.itemId = :itemId")
	 @Transactional
	int deleteByItemId(@Param("itemId")String itemId);
	 
	 

}

