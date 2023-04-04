package com.storemanagement.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.storemanagement.data.utils.DataUtils;
import com.storemanagement.dto.CustomerDTO;
import com.storemanagement.exception.AlreadyExistsException;
import com.storemanagement.exception.NoDataFoundException;
import com.storemanagement.filter.CustomerFilter;
import com.storemanagement.filter.utils.CustomerFilterUtil;
import com.storemanagement.model.CustomerEntity;
import com.storemanagement.pagination.PaginatedResult;
import com.storemanagement.service.CustomerService;

import springfox.documentation.annotations.ApiIgnore;

/**
 * @author chand
 *
 */
@RestController
@RequestMapping("/customer")
public class CustomerResource {

	@Autowired
	private CustomerService customerService;

	@GetMapping("/{id}")
	public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Long id) throws NoDataFoundException {
		CustomerEntity customerEntity = customerService.getCustomer(id);
		return new ResponseEntity<CustomerDTO>(DataUtils.converCustomerModelToDto(customerEntity, Boolean.FALSE),
				HttpStatus.OK);
	}
	
	@GetMapping("/email/{email}")
	public ResponseEntity<CustomerDTO> getCustomer(@PathVariable String email) throws NoDataFoundException {
		CustomerEntity customerEntity = customerService.getCustomerByEmail(email);
		return new ResponseEntity<CustomerDTO>(DataUtils.converCustomerModelToDto(customerEntity, Boolean.FALSE),
				HttpStatus.OK);
	}
	
	@GetMapping("/phone/{phone}")
	public ResponseEntity<CustomerDTO> getCustomerByPhone(@PathVariable String phone) throws NoDataFoundException {
		CustomerEntity customerEntity = customerService.getCustomerByPhone(phone);
		return new ResponseEntity<CustomerDTO>(DataUtils.converCustomerModelToDto(customerEntity, Boolean.FALSE),
				HttpStatus.OK);
	}

	@PostMapping
	public CustomerDTO addCustomer(@RequestBody CustomerDTO customer) throws AlreadyExistsException, Exception {
		CustomerEntity customerEntity = DataUtils.converCustomeDtoToModel(customer);
		customerEntity = customerService.createCustomer(customerEntity);
		return DataUtils.converCustomerModelToDto(customerEntity, Boolean.FALSE);
	}

	@PutMapping("/update/{customerId}")
	public CustomerDTO updateCustomer(@PathVariable("customerId") Long customerId, @RequestBody CustomerDTO customer)
			throws NoDataFoundException, AlreadyExistsException {
		customer.setId(customerId);
		CustomerEntity customerEntity = DataUtils.converCustomeDtoToModel(customer);
		customerEntity = customerService.updateCustomer(customerEntity);
		return DataUtils.converCustomerModelToDto(customerEntity, Boolean.FALSE);
	}

	@DeleteMapping("/delete/{customerId}")
	public ResponseEntity<String> deleteCustomer(@PathVariable("customerId") Long customerId) {
		customerService.deleteCustomer(customerId);
		return new ResponseEntity<String>("Deleted Customer with id:" + customerId + " successfully.", HttpStatus.OK);
	}

	@GetMapping("/list")
	public ResponseEntity<PaginatedResult<CustomerDTO>> getAllCustomers(
			@RequestParam(name = "pageNo", required = false) Integer pageNo,
			@RequestParam(name = "pageSize", required = false) Integer pageSize,
			@RequestParam(name = "sortBy", required = false) String sortBy,
			@RequestParam(name = "sortOrder", required = false) String sortOrder) {
		PageRequest pageRequest = CustomerFilterUtil.getPaginationRequest(pageNo, pageSize, sortBy, sortOrder);
		Page<CustomerEntity> page = customerService.getAllCustomers(pageRequest);
		List<CustomerDTO> customerDTOList = new ArrayList<>();
		page.getContent().forEach(each -> customerDTOList.add(DataUtils.converCustomerModelToDto(each, Boolean.FALSE)));
		PaginatedResult<CustomerDTO> paginatedResult = CustomerFilterUtil.getPaginatedResult(page, customerDTOList);
		paginatedResult.setResults(customerDTOList);
		return new ResponseEntity<PaginatedResult<CustomerDTO>>(paginatedResult, HttpStatus.OK);
	}
	
	@PostMapping(value = "/filter", consumes = "application/json")
	public ResponseEntity<PaginatedResult<CustomerDTO>> filterCustomers(
			@RequestBody CustomerFilter customerFilter,
			@RequestParam(name = "pageNo", required = false) Integer pageNo,
			@RequestParam(name = "pageSize", required = false) Integer pageSize,
			@RequestParam(name = "sortBy", required = false) String sortBy,
			@RequestParam(name = "sortOrder", required = false) String sortOrder) {
		PageRequest pageRequest = CustomerFilterUtil.getPaginationRequest(pageNo, pageSize, sortBy, sortOrder);
		CustomerFilterUtil.setDefaultPropertiesToFilter(customerFilter);
		Page<CustomerEntity> page = customerService.getCustomersByFilter(customerFilter, pageRequest);
		List<CustomerDTO> customerDTOList = new ArrayList<>();
		page.getContent().forEach(each -> customerDTOList.add(DataUtils.converCustomerModelToDto(each, Boolean.FALSE)));
		PaginatedResult<CustomerDTO> paginatedResult = CustomerFilterUtil.getPaginatedResult(page, customerDTOList);
		paginatedResult.setResults(customerDTOList);
		return new ResponseEntity<PaginatedResult<CustomerDTO>>(paginatedResult, HttpStatus.OK);
	}

	@ApiIgnore
	@PostMapping("/populate-sample-data")
	public List<CustomerDTO> populateSampleData(@RequestParam("size") int size) {
		List<CustomerEntity> customersList = DataUtils.prepareCutomersList(size);
		customersList.forEach(each -> {
			try {
				customerService.createCustomer(each);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		List<CustomerDTO> customersDTOList = new ArrayList<>();
		customersList.forEach(each -> customersDTOList.add(DataUtils.converCustomerModelToDto(each, Boolean.FALSE)));
		return customersDTOList;
	}

}
