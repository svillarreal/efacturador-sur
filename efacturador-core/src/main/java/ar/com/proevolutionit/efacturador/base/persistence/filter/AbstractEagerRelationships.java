package ar.com.proevolutionit.efacturador.base.persistence.filter;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.google.common.base.CaseFormat;

abstract class AbstractEagerRelationships<E> implements IEagerRelationships<E> {

	protected final Class<E> associationRoot;

	public AbstractEagerRelationships(Class<E> associationRoot) {
		this.associationRoot = associationRoot;
	}

	protected final String concat(String... associationPath) {
		Assert.notEmpty(associationPath);
		return StringUtils.join(associationPath, '.');
	}

	protected final Class<?> getReturnType(Method method) {
		Class<?> type = method.getReturnType();
		if (Collection.class.isAssignableFrom(type)) {
			Type gType = method.getGenericReturnType();
			if (gType instanceof ParameterizedType) {
				ParameterizedType pType = (ParameterizedType) gType;
				Type typeArg = pType.getActualTypeArguments()[0];
				if (pType.getActualTypeArguments().length == 1 && typeArg instanceof Class) {
					return (Class<?>) typeArg;
				}
			}
		}
		return type;
	}

	protected final Method findProperty(Class<?> associationRoot, String associationPath) {
		int pos = associationPath.indexOf('.');
		if (pos > 0) {
			associationRoot = navigate(associationRoot, associationPath.substring(0, pos));
			return findProperty(associationRoot, associationPath.substring(pos + 1));
		} else {
			for (Class<?> sourceEntity = associationRoot; sourceEntity != null; sourceEntity = sourceEntity.getSuperclass()) {
				for (Method method : sourceEntity.getMethods()) {
					if (method.getName().startsWith("get")
							&& method.getName().substring(3).equals(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, associationPath))) {
						return method;
					}
				}
			}
		}
		return null;
	}

	protected final Class<?> navigate(Class<?> associationRoot, String associationPath) {
		Method method = findProperty(associationRoot, associationPath);
		return (method != null) ? getReturnType(method) : null;
	}

	protected final Set<String> findAssociationPaths(Class<?> targetEntity) {
		Set<String> paths = new HashSet<String>();

		for (Class<?> sourceEntity = associationRoot; sourceEntity != null; sourceEntity = sourceEntity.getSuperclass()) {
			for (Method method : sourceEntity.getMethods()) {
				String name = method.getName();
				if (name.startsWith("get") && getReturnType(method) == targetEntity) {
					paths.add(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, name.substring(3)));
				}
			}
		}

		return paths;
	}

	protected final String findAssociationPath(Class<?> targetEntity) {
		Set<String> associationPaths = findAssociationPaths(targetEntity);
		if (associationPaths.isEmpty()) {
			throw new IllegalArgumentException("No path from " + associationRoot.getName() + " to " + targetEntity.getName());
		}
		if (associationPaths.size() > 1) {
			throw new IllegalArgumentException("Ambiguous navigation path from " + associationRoot.getName() + " to " + targetEntity.getName());
		}
		return associationPaths.iterator().next();
	}

	@Override
	public abstract void add(String associationPath);

	@Override
	public final void add(String... associationPath) {
		this.add(concat(associationPath));
	}

	@Override
	public abstract IEagerRelationships<?> join(String associationPath);

	@Override
	public final IEagerRelationships<?> join(String... associationPath) {
		return join(concat(associationPath));
	}

	@Override
	public final <T> IEagerRelationships<T> join(Class<T> targetEntity) {
		return join(targetEntity, findAssociationPath(targetEntity)).as(targetEntity);
	}


}
