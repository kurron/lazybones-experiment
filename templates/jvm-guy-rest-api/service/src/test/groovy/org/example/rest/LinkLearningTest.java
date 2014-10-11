package org.example.rest;

import org.junit.Test;
import org.springframework.hateoas.Link;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.junit.Before;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Learning test to exercise the Spring HATEOAS library.
 */
public class LinkLearningTest {

    protected MockHttpServletRequest request;

    @Before
    public void setUp() {

        request = new MockHttpServletRequest();
        ServletRequestAttributes requestAttributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(requestAttributes);
    }

    @Test
    public void exerciseHyperLinking() {
        Link link = linkTo( EchoController.class ).withRel( "to-echo-controller" );
        assertThat(link.getRel(), is("to-echo-controller"));

        Link link2 = linkTo( methodOn( EchoController.class ).fetchSpecificItem( "valid" ) ).withRel( "valid" );
        assertThat(link2.getRel(), is("valid"));
    }
}
