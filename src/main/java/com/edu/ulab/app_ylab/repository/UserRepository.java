package com.edu.ulab.app_ylab.repository;


import com.edu.ulab.app_ylab.entity.Person;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import javax.persistence.LockModeType;


public interface UserRepository extends CrudRepository<Person, Long> {

    /*
    User has books - book - started - comited status - other logic
    User has books - book - in progress
    User has books - book - finished
     */


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Person findPersonById(Long id);


}

