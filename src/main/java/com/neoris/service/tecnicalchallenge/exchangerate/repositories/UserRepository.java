package com.neoris.service.tecnicalchallenge.exchangerate.repositories;

import com.neoris.service.tecnicalchallenge.exchangerate.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<UserModel, Integer> {

    UserModel findOneByEmail(String email);

}
