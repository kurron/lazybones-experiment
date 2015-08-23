package org.kurron.example.jpa.persistence

import org.springframework.data.repository.CrudRepository

/**
 * Handles interactions with the database.
 */
interface ReferenceDataRepository extends CrudRepository<ReferenceData, Long> {
}
