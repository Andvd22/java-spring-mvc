package vn.hoidanit.laptopshop.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hoidanit.laptopshop.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User dovanan);

    void deleteById(long id);

    List<User> findOneByEmail(String email);

    User findById(long id);

    List<User> findAll();

    boolean existsByEmail(String email);

    User findByEmail(String email);
}
