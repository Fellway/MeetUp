package com.skrzypczyk.meetings.repository;

import com.skrzypczyk.meetings.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
