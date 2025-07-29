package org.aldogioia.templatesecurity.service.interfaces;


import org.aldogioia.templatesecurity.data.dto.CustomerCreateDto;

public interface CustomerService {
    void createCustomer(CustomerCreateDto customerCreateDto);
}
