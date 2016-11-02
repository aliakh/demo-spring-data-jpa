package demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface AsyncMessageRepository extends JpaRepository<Message, Long> {

    @Async
    CompletableFuture<List<Message>> findAllAsFuture();

    Optional<Message> findById(long id);
}
