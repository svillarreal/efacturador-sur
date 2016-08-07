package ar.com.proevolutionit.efacturador.base.persistence.filter;

import static com.google.common.collect.FluentIterable.from;

import org.springframework.util.Assert;

import com.google.common.base.Predicate;

class NestedRelationship<E> extends AbstractEagerRelationships<E> {

	private final EagerRelationships<?> parent;

	private final String prefixPath;

	private NestedRelationship(Class<E> nestedRoot, EagerRelationships<?> parent, String prefixPath) {
		super(nestedRoot);
		Assert.notNull(prefixPath);
		this.parent = parent;
		this.prefixPath = prefixPath + ".";
	}

	public static <T> IEagerRelationships<T> newInstance(Class<T> targetEntity, EagerRelationships<?> parent, String prefixPath) {
		return new NestedRelationship<T>(targetEntity, parent, prefixPath);
	}

	@Override
	public void add(String associationPath) {
		parent.add(prefixPath + associationPath);
	}

	@Override
	public IEagerRelationships<?> join(String associationPath) {
		return parent.join(prefixPath + associationPath);
	}

	@Override
	public void add(EagerRelationships<E> eagerRelationships) {
		Assert.isTrue(this.associationRoot == eagerRelationships.getAssociationRoot());
		for (String relationship : eagerRelationships.toArray()) {
			add(relationship);
		}
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
				throw new IllegalArgumentException("No such association path " + associationRoot + "[" + associationPath + "]");
			}
		}
		add(associationPath);
		return NestedRelationship.newInstance(targetEntity, parent, prefixPath + associationPath);
	}

	@Override
	public <T> IEagerRelationships<T> as(Class<T> entityType) {
		associationRoot.asSubclass(entityType);
		return new NestedRelationship<T>(entityType, parent, getDotlessPrefixPath());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
		sb.append("[").append(getDotlessPrefixPath());
		sb.append(":").append(associationRoot.getSimpleName());
		sb.append("\n").append(parent);
		sb.append("]");
		return sb.toString();
	}

	private String getDotlessPrefixPath() {
		return prefixPath.substring(0, prefixPath.length() - 1);
	}
}