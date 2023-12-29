package tech.elsoft.wizercodechallenge.repositories.specifications;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.EscapeCharacter;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class ExampleSpecification<T>
{

	public Specification<T> getSpecificationFromExample(Example<T> example) 
	{
		List<Predicate> predicates = new ArrayList<>();
		
	    return (root,  query, builder) -> {
			
	    	predicates.add(QueryByExamplePredicateBuilder.getPredicate(
	    			root, 
	    			builder, 
	    			example, EscapeCharacter.of('\\')));
	    	
	    	return builder.and(predicates.toArray(new Predicate[0]));
	    };
	 }
	
}
