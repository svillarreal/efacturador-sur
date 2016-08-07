package ar.com.proevolutionit.efacturador.base.persistence.filter;

public interface EagerRelationshipInitializer<E> {

	EagerRelationships<E> getEagerRelationships();

}
