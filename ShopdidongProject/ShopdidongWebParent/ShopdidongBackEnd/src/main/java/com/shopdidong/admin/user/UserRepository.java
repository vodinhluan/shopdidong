package com.shopdidong.admin.user;

import org.springframework.data.repository.CrudRepository;
import com.shopdidong.common.entity.User;

public interface UserRepository extends CrudRepository<User,Integer> {

}
