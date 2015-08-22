/**
 * We are going to use this to store Hibernate-specific annotations that apply to the entire package..
 */

@NamedQueries( {@NamedQuery( name="myCustomQuery", query = "select i from Item order by i.name asc" )} )
package org.kurron.example.jpa.persistence;

//NOTE: best practise is to use FQNs for any Hibernate annotations.  They often collide with JPA annotations and it
//be easier to find them when switching to newer versions of Hibernate.
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
