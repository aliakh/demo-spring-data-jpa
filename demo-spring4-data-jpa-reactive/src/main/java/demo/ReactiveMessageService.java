package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReactiveMessageService {

    @Autowired
    private ReactiveMessageRepository reactiveMessageRepository;

    public Mono<Message> findById(long id) {
        return Mono.justOrEmpty(reactiveMessageRepository.getOne(id));
    }

    public Flux<Message> findAll() {
        return Flux.fromStream(reactiveMessageRepository.findAllAsStream());
    }

    public Mono<Message> save(Mono<Message> messageMono) {
        return messageMono.then(message -> {
            return Mono.just(reactiveMessageRepository.save(message));
        });
    }
}
