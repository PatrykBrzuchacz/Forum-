package Main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Main.model.UserRole;



public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
	UserRole findByRole(String role);
}