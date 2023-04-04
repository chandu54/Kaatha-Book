package com.storemanagement.resource;

import java.util.List;

import com.storemanagement.dto.BaseDTO;
import com.storemanagement.model.BaseEntity;

public interface AbstractResource {

	public <T extends BaseDTO, U extends BaseEntity> U getModel(T dto);

	public <T extends BaseDTO, U extends BaseEntity> T getDTO(U model);

	public <T extends BaseDTO, U extends BaseEntity> List<U> getModelList(List<T> dtoList);

	public <T extends BaseDTO, U extends BaseEntity> List<T> getDTOList(List<U> modelList);
}
