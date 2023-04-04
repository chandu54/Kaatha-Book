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
import com.storemanagement.dto.TransactionDTO;
import com.storemanagement.exception.NoDataFoundException;
import com.storemanagement.filter.TransactionFilter;
import com.storemanagement.filter.utils.TransactionFilterUtil;
import com.storemanagement.model.CustomerEntity;
import com.storemanagement.model.TransactionEntity;
import com.storemanagement.model.enums.TransactionType;
import com.storemanagement.pagination.PaginatedResult;
import com.storemanagement.service.CustomerService;
import com.storemanagement.service.TransactionService;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/transaction")
public class TransactionResource {

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private CustomerService customerService;

	@GetMapping("/{id}")
	public TransactionDTO getTransaction(@PathVariable Long id) throws NoDataFoundException {
		TransactionEntity transaction = transactionService.getTransaction(id);
		return DataUtils.converTransactionModelToDTO(transaction);
	}

	@PostMapping
	public TransactionDTO createTransaction(@RequestBody TransactionDTO transaction) throws NoDataFoundException {
		TransactionEntity transactionEntity = DataUtils.converTransactionDTOToModel(transaction);
		transactionEntity = transactionService.createTransaction(transactionEntity);
		return DataUtils.converTransactionModelToDTO(transactionEntity);
	}

	@PutMapping("/update/{transactionId}")
	public TransactionDTO updateTransaction(@RequestBody TransactionDTO transaction,
			@PathVariable("transactionId") Long transactionId) throws NoDataFoundException {
		transaction.setId(transactionId);
		TransactionEntity transactionEntity = DataUtils.converTransactionDTOToModel(transaction);
		transactionEntity = transactionService.updateTransaction(transactionEntity);
		return DataUtils.converTransactionModelToDTO(transactionEntity);
	}

	@DeleteMapping("/delete/{transactionId}")
	public ResponseEntity<String> deleteTransaction(@PathVariable("transactionId") Long transactionId)
			throws NoDataFoundException {
		transactionService.deleteTransaction(transactionId);
		return new ResponseEntity<String>("Deleted Transaction with id:" + transactionId + " successfully.",
				HttpStatus.OK);
	}

	@GetMapping("/list")
	public ResponseEntity<PaginatedResult<TransactionDTO>> getAllTransactions(
			@RequestParam(name = "pageNo", required = false) Integer pageNo,
			@RequestParam(name = "pageSize", required = false) Integer pageSize,
			@RequestParam(name = "sortBy", required = false) String sortBy,
			@RequestParam(name = "sortOrder", required = false) String sortOrder) {
		PageRequest pageRequest = TransactionFilterUtil.getPaginationRequest(pageNo, pageSize, sortBy, sortOrder);
		Page<TransactionEntity> page = transactionService.getAllTransactions(pageRequest);
		List<TransactionDTO> transactionDTOList = new ArrayList<>();
		page.getContent().forEach(each -> transactionDTOList.add(DataUtils.converTransactionModelToDTO(each)));
		PaginatedResult<TransactionDTO> paginatedResult = TransactionFilterUtil.getPaginatedResult(page, transactionDTOList);
		paginatedResult.setResults(transactionDTOList);
		return new ResponseEntity<PaginatedResult<TransactionDTO>>(paginatedResult, HttpStatus.OK);
	}

	@PostMapping(value = "/filter", consumes = "application/json")
	public ResponseEntity<PaginatedResult<TransactionDTO>> filterTransactions(
			@RequestBody TransactionFilter transactionFilter,
			@RequestParam(name = "pageNo", required = false) Integer pageNo,
			@RequestParam(name = "pageSize", required = false) Integer pageSize,
			@RequestParam(name = "sortBy", required = false) String sortBy,
			@RequestParam(name = "sortOrder", required = false) String sortOrder) {
		PageRequest pageRequest = TransactionFilterUtil.getPaginationRequest(pageNo, pageSize, sortBy, sortOrder);
		TransactionFilterUtil.setDefaultPropertiesToFilter(transactionFilter);
		Page<TransactionEntity> page = transactionService.getTransactionsByFilter(transactionFilter, pageRequest);
		List<TransactionDTO> transactionDTOList = new ArrayList<>();
		page.getContent().forEach(each -> transactionDTOList.add(DataUtils.converTransactionModelToDTO(each)));
		PaginatedResult<TransactionDTO> paginatedResult = TransactionFilterUtil.getPaginatedResult(page, transactionDTOList);
		return new ResponseEntity<PaginatedResult<TransactionDTO>>(paginatedResult, HttpStatus.OK);
	}

	@ApiIgnore
	@PostMapping("/populate-sample-data/{customerId}")
	public List<TransactionDTO> populateSampleData(@PathVariable("customerId") Long customerId,
			@RequestParam("size") Integer size, @RequestParam("type") TransactionType type) {
		CustomerEntity customer = customerService.getCustomer(customerId);
		List<TransactionDTO> transactionDTOList = new ArrayList<>();
		List<TransactionEntity> transactions = DataUtils.prepareTransactionList(size, customer, type);
		transactions.forEach(each -> {
			transactionService.createTransaction(each);
			transactionDTOList.add(DataUtils.converTransactionModelToDTO(each));
		});
		return transactionDTOList;
	}

}
