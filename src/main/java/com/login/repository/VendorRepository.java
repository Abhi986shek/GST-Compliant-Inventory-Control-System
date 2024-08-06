package com.login.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.login.model.Vendor;


public interface VendorRepository extends JpaRepository<Vendor, String> {

	@Query("SELECT v.vendorName from Vendor v")
	List<String> findByVendorNames();
}

