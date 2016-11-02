package demo;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.BodyExtractors;
import org.springframework.http.codec.BodyInserters;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.ServerRequest;
import org.springframework.web.reactive.function.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
class MessageHandler {

    @Autowired
    private ReactiveMessageRepository reactiveMessageRepository;

    public ServerResponse<Flux<Message>> findAll(ServerRequest request) {
        Flux<Message> messages = Flux.fromStream(reactiveMessageRepository.findAllAsStream());
        return ServerResponse.ok().body(BodyInserters.fromPublisher(messages, Message.class));
    }

    public ServerResponse<Publisher<Message>> findById(ServerRequest request) {
        Mono<Message> message = Mono.justOrEmpty(request.pathVariable("id"))
                .map(Long::valueOf)
                .map(reactiveMessageRepository::findById)
                .then(Mono::fromFuture);
        return ServerResponse.ok().body(BodyInserters.fromPublisher(message, Message.class));
    }

    public ServerResponse<Mono<Message>> save(ServerRequest request) {
        Mono<Message> message1 = request.body(BodyExtractors.toMono(Message.class));
        Mono<Message> message2 = message1.then(message -> {
            return Mono.just(reactiveMessageRepository.save(message));
        });
        return ServerResponse.ok().body(BodyInserters.fromPublisher(message2, Message.class));
    }
}

