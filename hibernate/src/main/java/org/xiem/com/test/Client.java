package org.xiem.com.test;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Client {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext container = new ClassPathXmlApplicationContext("hibernate-application.xml");
		try {
			CustomerService customer = container.getBean(CustomerService.class);
			Customer c1 = new Customer("Joe", "Smith", "jsmith@gmail.com");
			System.out.println(c1);
			System.out.println("The customer last name is " + c1.getLastName());
			customer.newCustomer(c1);

			List<Customer> allCustomers = customer.getAllCustomers();

			for (Customer c : allCustomers) {
				System.out.println(c);
			}
		} finally {
			container.close();
		}
}
}