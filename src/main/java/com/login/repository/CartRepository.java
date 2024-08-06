package com.login.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.login.model.Cart;


public interface CartRepository extends JpaRepository<Cart, Integer> {

	List<Cart> findByUserName(String username);


	@Query("Select c from Cart c where c.userName = :username")
	List<Cart> getCartItemsByUsername(@Param("username") String username);

	@Query("Select c.quantity from Cart c where c.userName = :username AND c.itemId= :itemId AND c.batchNo= :batchNo")
	Double findMatch(@Param("username")String usernameField,@Param("itemId") String itemid,@Param("batchNo") String batchno);


	@Modifying
	@Query("UPDATE Cart c SET c.quantity= :newQuantity , c.transanctionValue= :totaltrans , c.totalwithGst= :totalWithgst, c.gst= :gstAmount1, c.cgst= :gstcg, c.sgst= :gstcg where c.userName = :username AND c.itemId= :itemId AND c.batchNo= :batchNo")
	@Transactional
	void updateQty(@Param("username")String usernameField,@Param("itemId") String itemid,@Param("batchNo") String batchno,
			@Param("newQuantity") Double qty,@Param("totaltrans") Double totaltrans,@Param("totalWithgst") Double totalWithgst,
			@Param("gstAmount1") Double gstAmount1,@Param("gstcg") Double gstcg);

	@Transactional
	@Modifying
	@Query("DELETE FROM Cart c WHERE c.userName = :username")
	void deleteCartByUserName(@Param("username")String usernameField);

	
}

