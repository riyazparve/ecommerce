package com.riyaz.ecom.userauthservice.dtos;

import com.riyaz.ecom.userauthservice.models.Role;
import com.riyaz.ecom.userauthservice.models.User;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserMapperImplTest {

    private final UserMapperImpl userMapper = new UserMapperImpl();

    @Test
    void toDtoReturnsNullForNullUser() {
        assertNull(userMapper.toDto(null));
    }

    @Test
    void toDtoMapsUserIdAndRoles() {
        User user = new User();
        user.setId(42L);
        user.setName("User");
        user.setEmail("user@example.com");
        Role role = new Role();
        role.setId(7L);
        role.setName("NON_ADMIN");
        user.setRole(Arrays.asList(role, null));

        UserDto result = userMapper.toDto(user);

        assertEquals("42", result.getId());
        assertEquals("User", result.getName());
        assertEquals("user@example.com", result.getEmail());
        assertEquals(2, result.getRole().size());
        assertEquals(7L, result.getRole().get(0).getId());
        assertEquals("NON_ADMIN", result.getRole().get(0).getName());
        assertNull(result.getRole().get(1));
    }

    @Test
    void toDtoHandlesMissingIdAndRoles() {
        User user = new User();
        user.setRole(null);

        UserDto result = userMapper.toDto(user);

        assertNull(result.getId());
        assertNull(result.getRole());
    }

    @Test
    void toDtoHandlesEmptyRoles() {
        User user = new User();
        user.setRole(Collections.emptyList());

        UserDto result = userMapper.toDto(user);

        assertEquals(Collections.emptyList(), result.getRole());
    }
}
