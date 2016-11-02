package demo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
data class Message(@Id var id: Long? = null, var text: String? = null)
