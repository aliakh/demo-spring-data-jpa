package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.LongStream;

@Component
class MessageCommandLineRunner implements CommandLineRunner {

    @Autowired
    private ReactiveMessageRepository reactiveMessageRepository;

    @Override
    public void run(String... strings) throws Exception {
        reactiveMessageRepository.deleteAll();
        LongStream.rangeClosed(1, 3).forEach(i -> reactiveMessageRepository.save(new Message(String.valueOf(i))));
        reactiveMessageRepository.findAll().forEach(System.out::println);
    }
}
