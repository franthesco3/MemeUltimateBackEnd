package com.ProjectMeme.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ProjectMeme.model.User;

public interface UserRepository  extends JpaRepository<User, Integer> {

}
