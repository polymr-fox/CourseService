package com.mrfox.senyast4745.coursesevice.repository;

import com.mrfox.senyast4745.coursesevice.model.Role;
import com.mrfox.senyast4745.coursesevice.model.UserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserModel, Long> {
    Optional<UserModel> findByUsername(String username);
    Iterable<UserModel> findByFullName(String fullName);
    Iterable<UserModel> findAllByRole(Role role);
}
