package org.xiem.com.test;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class CustomerDaoHibernateImpl implements CustomerDao {

	@Autowired
	private HibernateTemplate template;

	public void create(Customer customer) {
		template.save(customer);
	}

	public Customer getById(String customerId) throws RecordNotFoundException {
		List<Customer> results = (List<Customer>) template.find("from Customer where customerId=?", customerId);
		if (results.isEmpty())
			throw new RecordNotFoundException();
		return results.get(0);
	}

	public List<Customer> getByName(String lastName, String firstName) throws RecordNotFoundException {
		return (List<Customer>) template.findByNamedParam("from Customer where lastName=? and firstName=?", lastName,
				firstName);
	}

	public void update(Customer customerToUpdate) throws RecordNotFoundException {
		// TODO Auto-generated method stub

	}

	public void delete(Customer oldCustomer) throws RecordNotFoundException {
		Customer foundCustomer = template.get(Customer.class, oldCustomer.getCustomerId());
		template.delete(foundCustomer);
	}

	public List<Customer> getAllCustomers() {
		return (List<Customer>) template.find("from Customer");
}

}