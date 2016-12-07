package com.taskapp.repository;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;

import com.taskapp.model.UserModel;

public interface UserMongoRepository extends CrudRepository<UserModel, Serializable>{
	@Override
	public <S extends UserModel> S save(S arg0);
}
