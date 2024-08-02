package ru.test.authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.test.authorization.entity.AuthorizationFormEntity;

public interface AuthorizationFormRepository extends JpaRepository<AuthorizationFormEntity, Long> {
}
