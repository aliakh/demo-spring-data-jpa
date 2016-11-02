package demo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

import javax.validation.Valid

@RestController
@RequestMapping("message/sync")
class SyncMessageController @Autowired constructor(val syncMessageRepository: SyncMessageRepository) {

    @RequestMapping(value = "/count", method = arrayOf(RequestMethod.GET))
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    fun count() = syncMessageRepository.count()

    @RequestMapping(value = "/", method = arrayOf(RequestMethod.GET))
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    fun findAll() = syncMessageRepository.findAll()

    @RequestMapping(value = "/{id}", method = arrayOf(RequestMethod.GET))
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    fun findById(@PathVariable("id") id: Long): Message {
        val messageOpt = syncMessageRepository.findById(id)
        return messageOpt.orElseThrow({ MessageNotFoundException(id) })
    }

    @RequestMapping(value = "/", method = arrayOf(RequestMethod.POST))
    @ResponseStatus(HttpStatus.OK)
    fun create(@Valid @RequestBody message: Message) = syncMessageRepository.save(message)

    @RequestMapping(value = "/{id}", method = arrayOf(RequestMethod.PUT))
    @ResponseStatus(HttpStatus.OK)
    fun update(@PathVariable("id") id: Long, @RequestBody text: String): Message {
        val messageOpt: Optional<Message> = syncMessageRepository.findById(id)
        return messageOpt.map({ message ->
            message.text = text
            syncMessageRepository.save(message)
        }).orElseThrow({ MessageNotFoundException(id) })
    }

    @RequestMapping(value = "/", method = arrayOf(RequestMethod.DELETE))
    @ResponseStatus(HttpStatus.OK)
    fun deleteAll() = syncMessageRepository.deleteAll()

    @RequestMapping(value = "/{id}", method = arrayOf(RequestMethod.DELETE))
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    fun deleteById(@PathVariable("id") id: Long) = syncMessageRepository.delete(id)

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleMessageNotFoundException(e: MessageNotFoundException) {
    }
}
