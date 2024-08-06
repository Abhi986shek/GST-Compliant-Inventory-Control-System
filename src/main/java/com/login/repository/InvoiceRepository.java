package com.login.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.login.model.Invoice;


public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

	Optional<Invoice> findByItemIdAndBatchNo(String itemId, String batchNo);

	@Query(value = "SELECT MAX(invoiceNo) FROM Invoice")
	Integer getMaxinvoiceNo();

	@Query("Select i from Invoice i where i.invoiceNo = :invoiceno") 
	List<Invoice> findByInvoiceNo(@Param("invoiceno")Integer invoiceno2);

	

	

	
}

