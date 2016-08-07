/**
 * 
 */
package ar.com.proevolutionit.efacturador.base.view.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.vaadin.ui.Component;
import com.vaadin.ui.HasComponents;

/**
 * @author svillarreal
 *
 */
public class VaadinChildrenFinderUtil {

	/**
	 * A Vaadin helper utility for getting children of Component By.type
	 * 
	 * @param rootComponent
	 *            - component where to start
	 * @param criteriaType
	 * @return List of components that match type
	 */
	public static List<Component> getChildrenByClass(HasComponents rootComponent, final Class<?> criteriaType) {
		return getChildren(rootComponent, By.type(criteriaType));
	}

	/**
	 * A Vaadin helper utility for getting children of Component By.type
	 * 
	 * @param rootComponent
	 *            - component where to start
	 * @param idOfComponent
	 * @return List of components that match type
	 */
	public static List<Component> getChildrenByIdString(HasComponents rootComponent, final String idOfComponent) {
		return getChildren(rootComponent, By.id(idOfComponent));
	}

	/**
	 * A Vaadin helper utility for getting children of Component By.type
	 * 
	 * @param rootComponent
	 *            - component where to start
	 * @param idString
	 * @return List of components that match type
	 */
	public static List<Component> getChildrenByPartialIdString(HasComponents rootComponent, final String idString) {
		return getChildren(rootComponent, By.containingIdString(idString));
	}

	protected static List<Component> getChildren(HasComponents rootComponent, ComponentMatcher componentMatcher) {
		List<Component> foundChildrenComponents = new ArrayList<Component>();
		getChildren(rootComponent.iterator(), componentMatcher, foundChildrenComponents);
		return foundChildrenComponents;
	}

	/* A bit similar to Selenium2 / WebDriver By selectors */
	public static class By {
		public static ComponentMatcher id(final String id) {
			return new ComponentMatcher() {
				@Override
				public boolean doesItMatch(Component component) {
					if (component != null && component.getId() != null && component.getId().equals(id)) {
						return true;
					}
					return false;
				}
			};
		}

		public static ComponentMatcher containingIdString(final String idString) {
			return new ComponentMatcher() {
				@Override
				public boolean doesItMatch(Component component) {
					if (component != null && component.getId() != null && component.getId().contains(idString)) {
						return true;
					}
					return false;
				}
			};
		}

		public static ComponentMatcher type(final Class<?> type) {
			return new ComponentMatcher() {
				@Override
				public boolean doesItMatch(Component component) {
					if (type.isAssignableFrom(component.getClass())) {
						return true;
					}
					return false;
				}
			};
		}
	}

	public static interface ComponentMatcher {
		boolean doesItMatch(Component component);
	}

	protected static void getChildren(Iterator<Component> componentIterator, ComponentMatcher matcher, List<Component> children) {
		if (!componentIterator.hasNext()) {
			return;
		} else {
			Component component = componentIterator.next();
			if (HasComponents.class.isAssignableFrom(component.getClass())) {
				HasComponents hasComponents = (HasComponents) component;
				getChildren(hasComponents.iterator(), matcher, children);
			}
			if (matcher.doesItMatch(component)) {
				// match found
				children.add(component);
			}
			getChildren(componentIterator, matcher, children);
		}
	}

}
