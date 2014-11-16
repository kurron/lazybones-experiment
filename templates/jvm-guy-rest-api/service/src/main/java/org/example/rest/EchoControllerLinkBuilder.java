package org.example.rest;

import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

/**
 * A Java-based link builder needed to work around a Groovy/HATEOAS mismatch.
 */
public class EchoControllerLinkBuilder {

    private EchoControllerLinkBuilder() {
        // utility class
    }

    /**
     * Creates a URI to the 'instance' resources.
     * @param magicNumber the instance identifier of the resource.
     * @return populate URI builder.
     */
    public static UriComponents buildInstanceURI(String magicNumber)
    {
        return MvcUriComponentsBuilder.fromMethodCall( on( EchoController.class ).fetchSpecificItem( "instance" ) ).buildAndExpand( magicNumber );
    }
}
