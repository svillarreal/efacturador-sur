package ar.com.proevolutionit.efacturador.base.persistence.filter;

import com.topgroup.commons.utils.lang.BaseFilter;

/**
 * Collector de association paths para inicializar la lista de {@link BaseFilter#setEagerRelationShips(String[]) eager
 * relationships} de un {@code BaseFilter}.
 * <p>
 *
 * @see EagerRelationships
 *
 * @author jgodoy
 */
public interface IEagerRelationships<E> {

	/**
	 * Agrega la relaci&oacute;n dada por {@code associationPath}.
	 *
	 * @param associationPath
	 *            La cadena de asociaciones.
	 * @throws IllegalArgumentException
	 *             si {@code associationPath} no es una cadena de asociaciones v&aacute;lida.
	 */
	void add(String associationPath);

	void add(EagerRelationships<E> eagerRelationships);

	/**
	 * Agrega la relaci&oacute;n dada por la concatenaci&oacute;n de {@code associationPath}.
	 *
	 * @param associationPath
	 *            La cadena de asociaciones.
	 * @throws IllegalArgumentException
	 *             si {@code associationPath} no es una cadena de asociaciones v&aacute;lida.
	 */
	void add(String... associationPath);

	/**
	 * Agrega la relaci&oacute;n dada por {@code associationPath} y devuelve una instancia de
	 * {@code IEagerRelationships} impl&iacute;citamente prefijada con {@code associationPath}.
	 *
	 * @param associationPath
	 *            La cadena de asociaciones.
	 * @throws IllegalArgumentException
	 *             si {@code associationPath} no es una cadena de asociaciones v&aacute;lida.
	 * @return Una instancia de {@code IEagerRelationships} que a&ntilde;ade relaciones sobre la instancia original,
	 *         incorporando autom&aacute;ticamente el prefijo {@code associationPath} con el que se invoc&oacute; este
	 *         m&eacute;todo.
	 */
	IEagerRelationships<?> join(String associationPath);

	/**
	 * Agrega la relaci&oacute;n dada por la concatenaci&oacute;n de {@code associationPath} y devuelve una instancia de
	 * {@code IEagerRelationships} impl&iacute;citamente prefijada con {@code associationPath}.
	 *
	 * @param associationPath
	 *            La cadena de asociaciones.
	 * @throws IllegalArgumentException
	 *             si {@code associationPath} no es una cadena de asociaciones v&aacute;lida.
	 * @return Una instancia de {@code IEagerRelationships} que a&ntilde;ade relaciones sobre la instancia original,
	 *         incorporando autom&aacute;ticamente el prefijo {@code associationPath} con el que se invoc&oacute; este
	 *         m&eacute;todo.
	 */
	IEagerRelationships<?> join(String... associationPath);

	<T> IEagerRelationships<T> join(Class<T> targetEntity);

	<T> IEagerRelationships<T> join(Class<T> targetEntity, String associationPath);

	<T> IEagerRelationships<T> as(Class<T> entityType);

}