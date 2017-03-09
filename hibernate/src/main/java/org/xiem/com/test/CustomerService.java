package org.xiem.com.test;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xiem.com.test.CustomerDao;
import org.xiem.com.test.RecordNotFoundException;

@Transactional
@Service
public class CustomerServiceImpl implements CustomerService {

	private CustomerDao dao;

	@Autowired
	public CustomerServiceImpl(CustomerDao dao) {
		this.dao = dao;
	}

	public void newCustomer(Customer newCustomer) {
		dao.create(newCustomer);
	}

	public void updateCustomer(Customer changedCustomer) throws CustomerNotFoundException {
		// TODO Auto-generated method stub

	}

	public void deleteCustomer(Customer oldCustomer) throws CustomerNotFoundException {
		try {
			dao.delete(oldCustomer);
		} catch (RecordNotFoundException e) {
			throw new CustomerNotFoundException();
		}
	}

	public Customer findCustomerById(String customerId) throws CustomerNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Customer> findCustomersByName(String lastName, String firstName) throws CustomerNotFoundException {
		try {
			return dao.getByName(lastName, firstName);
		} catch (RecordNotFoundException e) {
			throw new CustomerNotFoundException();
		}
	}

	public List<Customer> getAllCustomers() {
		return dao.getAllCustomers();
}

}