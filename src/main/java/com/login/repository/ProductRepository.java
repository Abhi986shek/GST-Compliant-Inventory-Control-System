package com.login.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.login.model.Product;

public interface ProductRepository extends JpaRepository<Product, String> {

    Product findByItemId(String itemId);

    @Query("SELECT i.itemId from Product i")
	List<String> findItemId();
    

    @Query("SELECT hsnCode FROM Product WHERE itemId = :itemid")
	String gethsn(@Param("itemid")String itemid);


	@Query("SELECT subCategory FROM Product WHERE category = :itemId")
    List<String> findSubCatByCat(@Param("itemId") String category);

	@Query("SELECT p.itemId from Product p where p.itemId = :itemId")
	String getByItemId(@Param("itemId")String itemid);
	
	@Query("SELECT CASE when COUNT(p) > 0 THEN TRUE ELSE FALSE END FROM Product p where p.itemName = :itemName")
	boolean existsByItemname(@Param("itemName") String itemName);

    // You can add more custom query methods as needed
}


