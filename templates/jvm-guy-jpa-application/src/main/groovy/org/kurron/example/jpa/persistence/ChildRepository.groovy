package org.kurron.example.jpa.persistence

import org.springframework.data.repository.CrudRepository

/**
 * Handles interactions with the database.
 */
interface ChildRepository extends CrudRepository<Child, Long> {
}
