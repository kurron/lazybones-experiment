package org.kurron.example.jpa.persistence

import org.springframework.data.repository.Repository

/**
 * Handles interactions with the database.
 */
interface ParentRepository extends Repository<Parent, Long> {
}
