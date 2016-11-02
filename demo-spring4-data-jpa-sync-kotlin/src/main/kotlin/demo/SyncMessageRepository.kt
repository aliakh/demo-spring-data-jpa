package demo

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface SyncMessageRepository : JpaRepository<Message, Long> {

    fun findById(id: Long): Optional<Message>
}