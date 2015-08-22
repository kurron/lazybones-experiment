package org.kurron.example.jpa.persistence

import org.springframework.data.repository.CrudRepository

/**
 * Handles interactions with the database.
 */
interface ParentRepository extends CrudRepository<Parent, Long> {
}
