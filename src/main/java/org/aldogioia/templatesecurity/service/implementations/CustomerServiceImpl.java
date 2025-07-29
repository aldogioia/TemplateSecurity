package org.aldogioia.templatesecurity.service.implementations;

import lombok.RequiredArgsConstructor;
import org.aldogioia.templatesecurity.data.dao.CustomerDao;
import org.aldogioia.templatesecurity.data.dto.CustomerCreateDto;
import org.aldogioia.templatesecurity.data.entities.Customer;
import org.aldogioia.templatesecurity.service.interfaces.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerDao customerDao;
    private final ModelMapper modelMapper;

    @Override
    public void createCustomer(CustomerCreateDto customerCreateDto) {
        Customer customer = modelMapper.map(customerCreateDto, Customer.class);
        customerDao.save(customer);
    }
}
