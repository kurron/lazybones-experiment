package org.example.rest.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * A hypermedia control that supports all the states of the sample resource.
 */
@JsonInclude( JsonInclude.Include.NON_NULL )
class SimpleMediaType {

    /**
     * The MIME Type to use when referring to this structure.
     */
    @JsonIgnore
    public static final MEDIA_TYPE = 'application/json;type=simple-media-type;version=0.0.0'

    /**
     * Option field that will be filled when a singular instance is requested.
     */
    @JsonProperty( 'item' )
    Item item

    /**
     * Option field that will be filled when multiple instances are requested. A production system
     * would likely have the notion of 'shallow' and 'deep' collections.  The 'shallow' versions would
     * contain only a portion of the Item's data and URIs to load the full details.
     */
    @JsonProperty( 'collection' )
    List<Item> collection

    /**
     * Optional field that will be filled in only when error states are reached.
     */
    @JsonProperty( 'error' )
    ErrorContext error

    /**
     * Optional field used to insert or update data in the system. Another alternative might be to use some sort of
     * dynamic system where individual fields are described, perhaps suggesting prompt strings that the UI can
     * present to the user when building out a form.
     */
    @JsonProperty( 'template' )
    Item template
}

