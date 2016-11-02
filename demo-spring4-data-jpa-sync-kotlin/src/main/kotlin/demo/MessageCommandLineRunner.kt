package demo

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.util.stream.Stream

@Component
class MessageCommandLineRunner(val messageRepository: SyncMessageRepository) : CommandLineRunner {

    override fun run(vararg args: String?) {
        messageRepository.deleteAll()
        Stream.of(Message(1, "1"), Message(2, "2"), Message(3, "3")).forEach { m -> messageRepository.save(m) }
        messageRepository.findAllAsStream().forEach { println(it) }
    }
}
