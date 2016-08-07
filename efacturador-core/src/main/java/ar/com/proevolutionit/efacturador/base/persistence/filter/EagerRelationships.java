package ar.com.proevolutionit.efacturador.base.persistence.filter;

import static com.google.common.collect.FluentIterable.from;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.google.common.base.Predicate;
import com.topgroup.commons.utils.lang.BaseFilter;

/**
 * Collector de association paths para inicializar la lista de {@link BaseFilter#setEagerRelationShips(String[]) eager
 * relationships} de un {@code BaseFilter}.
 * <p>
 *
 * Ejemplo de uso: inicializa la lista de relaciones devuelta por {@link EagerRelationships#toArray()} con {@code "foo"}
 * , {@code "foo.bar"}, {@code "baz"} y {@code "baz.qux"}, navegable desde la entidad {@code Xyzzy}
 *
 * <pre>
 * EagerRelationships&lt;Xyzzy> eagerRelationships = new EagerRelationships&lt;>(Xyzzy.class);
 * eagerRelationships.add("foo.bar");
 * eagerRelationships.join("baz").add("qux");
 * </pre>
 *
 * Alternativamente, puede invocarse del siguiente modo:
 * <pre>
 * eagerRelationships.join(Baz.class).join(Qux.class);
 * </pre>
 *
 * @author jgodoy
 */
public final class EagerRelationships<E> extends AbstractEagerRelationships<E> {

	private Set<String> eagerRelationships = new TreeSet<String>();

	public EagerRelationships(Class<E> associationRoot) {
		super(associationRoot);
	}

	@Override
	public void add(String associationPath) {
		Assert.isTrue(associationPath.matches("\\w+(\\.\\w+)*"));
		int pos = associationPath.length();
		while (pos > 0 && eagerRelationships.add(associationPath = associationPath.substring(0, pos))) {
			pos = associationPath.lastIndexOf('.');
		}
	}

	private class Property {
		final AnnotatedElement member;

		final Class<?> declaringType;

		final Class<?> type;
		final String name;

		public Property(Method method, Class<?> declaringClass, Class<?> type, String name) {
			this.member = method;
			this.declaringType = method.getDeclaringClass();
			this.type = type;
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	private String mappedBy(AnnotatedElement method) {
		OneToMany oneToMany = method.getAnnotation(OneToMany.class);
		if (oneToMany != null && !oneToMany.mappedBy().isEmpty()) {
			return oneToMany.mappedBy();
		}
		OneToOne oneToOne = method.getAnnotation(OneToOne.class);
		if (oneToOne != null && !oneToOne.mappedBy().isEmpty()) {
			return oneToOne.mappedBy();
		}
		return null;
	}

	@Override
	public IEagerRelationships<?> join(String associationPath) {
		Class<?> targetEntity = navigate(associationRoot, associationPath);
		return join(targetEntity, associationPath);
	}

	@Override
	public <T> IEagerRelationships<T> join(Class<T> targetEntity, final String associationPath) {
		int pos = associationPath.indexOf('.');
		if (pos > 0) {
			return join(associationPath.substring(0, pos)).join(targetEntity, associationPath.substring(pos + 1));
		} else {
			if (from(findAssociationPaths(targetEntity)).filter(new Predicate<String>() {
				@Override
				public boolean apply(String _associationPath) {
					return _associationPath.equals(associationPath);
				}
			}).isEmpty()) {
				throw new IllegalArgumentException("No such association path " + associationRoot.getName() + "." + associationPath);
			}
		}
		add(associationPath);
		return NestedRelationship.newInstance(targetEntity, this, associationPath);
	}

	@Override
	public void add(EagerRelationships<E> eagerRelationships) {
		Assert.isTrue(this.associationRoot == eagerRelationships.getAssociationRoot());
		this.eagerRelationships.addAll(eagerRelationships.eagerRelationships);
	}

	private boolean isInverse(Property property1, Property property2) {
		if (property1 == null || property2 == null) {
			return false;
		}

		if (property1.declaringType == property2.type && property2.declaringType == property1.type) {
			String method1MappedBy = mappedBy(property1.member);
			String method2MappedBy = mappedBy(property2.member);
			if (property2.name.equals(method1MappedBy)) {
				return true;
			}
			if (property1.name.equals(method2MappedBy)) {
				return true;
			}
		}
		return false;
	}

	private String removeInverses(String associationPath) {
		Deque<Property> stack = new LinkedList<Property>();
		Queue<String> queue = new LinkedList<String>(Arrays.asList(associationPath.split("\\.")));

		while (!queue.isEmpty()) {
			String property = queue.poll();
			Property previous = stack.peek();
			Class<?> declaringClass = previous == null ? associationRoot : previous.type;
			Method accessor = findProperty(declaringClass, property);
			Property next = new Property(accessor, declaringClass, getReturnType(accessor), property);

			if (isInverse(previous, next)) {
				stack.pop();
			} else {
				stack.push(next);
			}
		}
		return StringUtils.join(stack.descendingIterator(), '.');
	}

	/** Devuelve la lista de asociaciones recolectadas como un arreglo de {@code String}. */
	public String[] toArray() {
		Set<String> eagerRelationships = new TreeSet<String>();
		for (String associationPath : this.eagerRelationships) {
			associationPath = removeInverses(associationPath);
			if (!associationPath.isEmpty()) {
				eagerRelationships.add(associationPath);
			}
		}
		this.eagerRelationships = eagerRelationships;
		return eagerRelationships.toArray(new String[eagerRelationships.size()]);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> EagerRelationships<T> as(Class<T> entityType) {
		associationRoot.asSubclass(entityType);
		return (EagerRelationships<T>) this;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
		sb.append(":").append(associationRoot.getSimpleName());
		sb.append("{");
		for (String associationPath : toArray()) {
			sb.append("\n ").append(associationPath);
		}
		if (!eagerRelationships.isEmpty()) {
			sb.append('\n');
		}
		sb.append("}");
		return sb.toString();
	}

	public Class<E> getAssociationRoot() {
		return associationRoot;
	}

}
