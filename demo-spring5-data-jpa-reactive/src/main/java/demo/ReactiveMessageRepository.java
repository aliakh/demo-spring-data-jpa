package demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

interface ReactiveMessageRepository extends JpaRepository<Message, Long> {

    CompletableFuture<Message> findById(long id);

    @Query("select m from Message m")
    Stream<Message> findAllAsStream();
}
