package guru.springframework.spring6restmvc.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import guru.springframework.spring6restmvc.model.Customer;
import guru.springframework.spring6restmvc.service.CustomerService;
import guru.springframework.spring6restmvc.service.CustomerServiceImpl;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

	/**
	 * Mock representation of the CustomerService for isolating controller behavior.
	 */
	@MockBean
	CustomerService customerService;

	/**
	 * Provides a mechanism for testing server-side HTTP requests.
	 */
	@Autowired
	MockMvc mockMvc;

	/**
	 * Helper for converting between Java objects and JSON.
	 */
	@Autowired
	ObjectMapper objectMapper;

	/**
	 * Actual implementation of the CustomerService, likely for setup or utility
	 * purposes.
	 */
	CustomerServiceImpl customerServiceImpl;

	/**
	 * Setup method that initializes the required components before each test.
	 */
	@BeforeEach
	void setUp() {
		customerServiceImpl = new CustomerServiceImpl();
	}

	/**
	 * Captures the UUID argument passed during a method call for verification.
	 */
	@Captor
	ArgumentCaptor<UUID> uuidArgumentCaptor;

	/**
	 * Captures the Customer argument passed during a method call for verification.
	 */
	@Captor
	ArgumentCaptor<Customer> customerArgumentCaptor;

	/**
	 * Tests the PATCH endpoint for updating a customer's name.
	 */
	@Test
	void testPatchCustomer() throws Exception {
		// Retrieve the first customer from the service implementation.
		Customer customer = customerServiceImpl.getAllCustomers().get(0);

		// Create a map to represent the partial update of the customer's name.
		Map<String, Object> customerMap = new HashMap<>();
		customerMap.put("name", "New Name");

		// Simulate a PATCH request to update the customer's name.
		mockMvc.perform(patch("/api/v1/customer/" + customer.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(customerMap))).andExpect(status().isNoContent());

		// Verify that the patchCustomerById method was called with the expected
		// arguments.
		verify(customerService).patchCustomerById(uuidArgumentCaptor.capture(), customerArgumentCaptor.capture());

		// Assert that the captured UUID and name match the expected values.
		assertThat(uuidArgumentCaptor.getValue()).isEqualTo(customer.getId());
		assertThat(customerArgumentCaptor.getValue().getName()).isEqualTo(customerMap.get("name"));
	}

	/**
	 * Tests the DELETE endpoint for removing a customer.
	 * <p>
	 * The method follows these steps: 1. Retrieves the first customer from the
	 * service implementation. 2. Simulates a DELETE request to remove the customer
	 * using MockMvc. 3. Expects the server to respond with a 204 No Content status,
	 * indicating the deletion was successful. 4. Verifies that the
	 * deleteCustomerById method of the customerService was invoked. 5. Asserts that
	 * the captured UUID argument (customer ID) from the service call matches the
	 * original customer's ID.
	 * </p>
	 * <p>
	 * Note: There seems to be an inconsistency where the comment mentions verifying
	 * the invocation of the 'patchCustomerById' method. This might be a mistake and
	 * could require correction.
	 * </p>
	 *
	 * @throws Exception if any exception occurs during the mock HTTP request or
	 *                   assertions.
	 */

	@Test
	void testDeleteCustomer() throws Exception {
		// Retrieve the first customer from the service implementation.
		Customer customer = customerServiceImpl.getAllCustomers().get(0);

		// Simulate a DELETE request to remove the customer using MockMvc.
		// The content type is set to JSON.
		mockMvc.perform(delete("/api/v1/customer/" + customer.getId()).contentType(MediaType.APPLICATION_JSON))
				// Expect the server to respond with a 204 No Content status, indicating the
				// deletion was successful.
				.andExpect(status().isNoContent());

		// Verify that the deleteCustomerById method of the customerService was invoked.
		verify(customerService).deleteCustomerById(uuidArgumentCaptor.capture());

		// Assert that the captured UUID argument (customer ID) from the service call
		// matches the original customer's ID.
		assertThat(customer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
	}

	/**
	 * Tests the POST endpoint for creating a new customer.
	 */
	@Test
	void testCreateCustomer() throws Exception {
		// Retrieve the first customer from the service implementation.
		Customer customer = customerServiceImpl.getAllCustomers().get(0);

		// Reset the ID and version of the customer object to simulate a new customer
		// creation.
		customer.setId(null);
		customer.setVersion(null);

		// Mock the behavior of the saveNewCustomer method of the customerService.
		// When a new customer is saved, it returns a different customer (for the
		// purpose of this test).
		given(customerService.saveNewCustomer(any(Customer.class)))
				.willReturn(customerServiceImpl.getAllCustomers().get(1));

		// Simulate a POST request to create a new customer.
		// Set the content type to JSON and provide the customer data as the request
		// body.
		mockMvc.perform(post("/api/v1/customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(customer)))
				// Expect the server to respond with a 201 Created status, indicating the
				// customer was successfully created.
				.andExpect(status().isCreated())
				// Also expect the server to return a "Location" header, indicating the URI of
				// the newly created customer.
				.andExpect(header().exists("Location"));
	}

	/**
	 * Tests the GET endpoint for retrieving a list of all customers.
	 */
	@Test
	void listAllCustomers() throws Exception {
		// Mock the behavior of the customerService's getAllCustomers method to return
		// the list of customers from the actual service implementation.
		// This ensures that the test does not depend on the actual service layer but
		// uses a predetermined list.
		given(customerService.getAllCustomers()).willReturn(customerServiceImpl.getAllCustomers());

		// Use MockMvc to simulate an HTTP GET request to retrieve the list of all
		// customers.
		// The expected response type (as indicated by the accept header) is JSON.
		mockMvc.perform(get("/api/v1/customer").accept(MediaType.APPLICATION_JSON))
				// Expect the server to respond with a 200 OK status, indicating the request was
				// successful.
				.andExpect(status().isOk())
				// Expect the content type of the response to be JSON.
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				// Using a JSON path expression, expect the size of the returned list (or array)
				// of customers to be 3.
				.andExpect(jsonPath("$.length()", is(3)));
	}

	/**
	 * Tests the GET endpoint for retrieving a specific customer by ID.
	 */
	@Test
	void getCustomerById() throws Exception {
		// Retrieve the first customer from the service implementation.
		Customer customer = customerServiceImpl.getAllCustomers().get(0);

		// Mock the behavior of the customerService's getCustomerById method.
		// When called with the above customer's ID, it should return that specific
		// customer.
		// This ensures that the test does not rely on the actual service layer but uses
		// a predetermined customer.
		given(customerService.getCustomerById(customer.getId())).willReturn(customer);

		// Use MockMvc to simulate an HTTP GET request to retrieve the customer by its
		// ID.
		// The expected response type (as indicated by the accept header) is JSON.
		mockMvc.perform(get("/api/v1/customer/" + customer.getId()).accept(MediaType.APPLICATION_JSON))
				// Expect the server to respond with a 200 OK status, indicating the request was
				// successful.
				.andExpect(status().isOk())
				// Expect the content type of the response to be JSON.
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				// Using a JSON path expression, expect the name of the returned customer to
				// match the name of our mock customer.
				.andExpect(jsonPath("$.name", is(customer.getName())));
	}

}
