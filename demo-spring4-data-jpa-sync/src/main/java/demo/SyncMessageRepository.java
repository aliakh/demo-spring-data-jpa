package demo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SyncMessageRepository extends JpaRepository<Message, Long> {

    Optional<Message> findById(long id);
}
