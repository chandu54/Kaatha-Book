package com.storemanagement.pagination;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.storemanagement.dto.BaseDTO;
import com.storemanagement.model.BaseEntity;

public class PaginationUtils {
	public static PageRequest getPaginationRequest(Integer pageNo, Integer pageSize, String sortBy, String sortOrder) {
		Direction sortDirection = Direction.DESC;
		if (pageNo == null || pageNo <= 0) {
			pageNo = 1;
		}
		if (pageSize == null || pageSize <= 0) {
			pageSize = 10;
		}
		if (sortBy == null || sortBy.isEmpty()) {
			sortBy = "id";
		}
		if (sortOrder != null && !sortOrder.isEmpty()
				&& (sortOrder.equalsIgnoreCase("ASC") || sortOrder.equalsIgnoreCase("ascending"))) {
			sortDirection = Direction.ASC;
		}
		PageRequest pageRequest = PageRequest.of(pageNo - 1, pageSize, Sort.by(sortDirection, sortBy));
		return pageRequest;
	}

	// TODO : We can use Builder design pattern instead
	public static <T extends BaseDTO, U extends BaseEntity> PaginatedResult<T> getPaginatedResult(Page<U> resultsPage,
			List<T> resultsDTOList) {
		PaginatedResult<T> paginatedResult = new PaginatedResult<>();
		paginatedResult.setCurrentPage(resultsPage.getPageable().getPageNumber() + 1);
		paginatedResult.setPageSize(resultsPage.getSize());
		// TODO need to write a generic method to get this list
		paginatedResult.setResults(resultsDTOList);
		paginatedResult.setTotalPages(resultsPage.getTotalPages());
		paginatedResult.setTotalCount(resultsPage.getTotalElements());
		return paginatedResult;
	}
}
