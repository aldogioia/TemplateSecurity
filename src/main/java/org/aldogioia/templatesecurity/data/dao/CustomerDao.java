package org.aldogioia.templatesecurity.data.dao;

import org.aldogioia.templatesecurity.data.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDao extends JpaRepository<Customer, String> {
}
