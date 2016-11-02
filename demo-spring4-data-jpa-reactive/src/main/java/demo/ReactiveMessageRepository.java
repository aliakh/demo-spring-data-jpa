package demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.stream.Stream;

public interface ReactiveMessageRepository extends JpaRepository<Message, Long> {

    @Query("select m from Message m")
    Stream<Message> findAllAsStream();
}
