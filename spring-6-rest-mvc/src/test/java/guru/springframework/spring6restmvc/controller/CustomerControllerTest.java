package guru.springframework.spring6restmvc.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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

import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.service.CustomerService;
import guru.springframework.spring6restmvc.service.CustomerServiceImpl;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @MockBean
    CustomerService customerService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    CustomerServiceImpl customerServiceImpl;

    @BeforeEach
    void setUp() {
        customerServiceImpl = new CustomerServiceImpl();
    }

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<CustomerDTO> customerArgumentCaptor;

    /**
     * Tests the functionality of patching a customer's details.
     * 
     * @throws Exception if any error occurs during the test.
     */
    @Test
    void testPatchCustomer() throws Exception {
        // Retrieve the first customer from the service.
        CustomerDTO customer = customerServiceImpl.getAllCustomers().get(0);

        // Create a map with updated customer details.
        Map<String, Object> customerMap = new HashMap<>();
        customerMap.put("name", "New Name");

        // Use mockMvc to simulate a PATCH request to update customer details.
        mockMvc.perform(patch( CustomerController.CUSTOMER_PATH_ID, customer.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerMap)))
                .andExpect(status().isNoContent()); // Expecting no content response (204 status).

        // Verify that the patchCustomerById method in customerService was called.
        verify(customerService).patchCustomerById(uuidArgumentCaptor.capture(),
                customerArgumentCaptor.capture());

        // Assert that the captured UUID matches the customer's ID.
        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(customer.getId());
        
        // Assert that the captured customer's name matches the updated name.
        assertThat(customerArgumentCaptor.getValue().getName())
                .isEqualTo(customerMap.get("name"));
    }

    /**
     * Tests the functionality of deleting a customer.
     * 
     * @throws Exception if any error occurs during the test.
     */
    @Test
    void testDeleteCustomer() throws Exception {
        // Retrieve the first customer from the service.
        CustomerDTO customer = customerServiceImpl.getAllCustomers().get(0);

        // Use mockMvc to simulate a DELETE request for the customer.
        mockMvc.perform(delete(CustomerController.CUSTOMER_PATH_ID, customer.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()); // Expecting no content response (204 status).

        // Verify that the deleteCustomerById method in customerService was called.
        verify(customerService).deleteCustomerById(uuidArgumentCaptor.capture());

        // Assert that the captured UUID matches the customer's ID.
        assertThat(customer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    /**
     * Tests the functionality of updating a customer's details.
     * 
     * @throws Exception if any error occurs during the test.
     */
    @Test
    void testUpdateCustomer() throws Exception {
        // Retrieve the first customer from the service.
        CustomerDTO customer = customerServiceImpl.getAllCustomers().get(0);

        // Use mockMvc to simulate a PUT request to update the customer's details.
        mockMvc.perform(put(CustomerController.CUSTOMER_PATH_ID, customer.getId())
                .content(objectMapper.writeValueAsString(customer))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()); // Expecting no content response (204 status).

        // Verify that the updateCustomerById method in customerService was called.
        verify(customerService).updateCustomerById(uuidArgumentCaptor.capture(), any(CustomerDTO.class));

        // Assert that the captured UUID matches the customer's ID.
        assertThat(customer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    /**
     * Tests the functionality of creating a new customer.
     * 
     * @throws Exception if any error occurs during the test.
     */
    @Test
    void testCreateCustomer() throws Exception {
        // Retrieve the first customer from the service and reset its ID and version.
        CustomerDTO customer = customerServiceImpl.getAllCustomers().get(0);
        customer.setId(null);
        customer.setVersion(null);

        // Mock the saveNewCustomer method to return another customer.
        given(customerService.saveNewCustomer(any(CustomerDTO.class)))
                .willReturn(customerServiceImpl.getAllCustomers().get(1));

        // Use mockMvc to simulate a POST request to create a new customer.
        mockMvc.perform(post(CustomerController.CUSTOMER_PATH).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    /**
     * Tests the functionality of listing all customers.
     * 
     * @throws Exception if any error occurs during the test.
     */
    @Test
    void listAllCustomers() throws Exception {
        // Mock the getAllCustomers method to return a list of all customers.
        given(customerService.getAllCustomers()).willReturn(customerServiceImpl.getAllCustomers());

        // Use mockMvc to simulate a GET request to retrieve all customers.
        mockMvc.perform(get(CustomerController.CUSTOMER_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    /**
     * Tests the functionality of retrieving a customer by ID when the customer is not found.
     * 
     * @throws Exception if any error occurs during the test.
     */
    @Test
    void getCustomerByIdNotFound() throws Exception {
        // Mock the getCustomerById method to return an empty Optional.
        given(customerService.getCustomerById(any(UUID.class))).willReturn(Optional.empty());

        // Use mockMvc to simulate a GET request to retrieve a customer by a random UUID.
        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    /**
     * Tests the functionality of retrieving a customer by its ID.
     * 
     * @throws Exception if any error occurs during the test.
     */
    @Test
    void getCustomerById() throws Exception {
        // Retrieve the first customer from the service.
        CustomerDTO customer = customerServiceImpl.getAllCustomers().get(0);

        // Mock the getCustomerById method to return the retrieved customer.
        given(customerService.getCustomerById(customer.getId())).willReturn(Optional.of(customer));

        // Use mockMvc to simulate a GET request to retrieve the customer by its ID.
        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID, customer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(customer.getName())));
    }

}
