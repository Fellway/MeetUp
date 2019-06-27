package com.skrzypczyk.meetings.service.role;

import com.skrzypczyk.meetings.model.Role;
import com.skrzypczyk.meetings.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }
}
