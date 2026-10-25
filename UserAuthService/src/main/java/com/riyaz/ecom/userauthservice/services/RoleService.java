//package com.riyaz.ecom.userauthservice.services;
//
//import com.riyaz.ecom.userauthservice.models.Role;
//import com.riyaz.ecom.userauthservice.repositories.RoleRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class RoleService implements IRoleService {
//
//    private final RoleRepository roleRepository;
//
//    public RoleService(RoleRepository roleRepository) {
//        this.roleRepository = roleRepository;
//    }
//
//    @Override
//    public List<Role> getAllRoles() {
//        return roleRepository.findAll();
//    }
//
//    @Override
//    public Optional<Role> getRoleById(Long id) {
//        return roleRepository.findById(id);
//    }
//
//    @Override
//    public Role createRole(Role role) {
//        return roleRepository.save(role);
//    }
//
//    @Override
//    public Role updateRole(Role role) {
//        return roleRepository.save(role);
//    }
//
//    @Override
//    public Role replaceRole(Role role) {
//        return roleRepository.save(role);
//    }
//
//    @Override
//    public void deleteRole(Long id) {
//        if (id == null)
//            throw new IllegalArgumentException("Role id cannot be null");
//        roleRepository.deleteById(id);
//    }
//}
