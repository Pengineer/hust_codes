package csdc.tool.beanutil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

/**
 * SMDB中所有bean
 * @author xuhan
 *
 */
public class AllBeans {
	
	private static Class[] beans;
	
	
	public static Class[] findAllBeans() {
		if (beans != null) {
			return beans;
		}
		
		List<Class> result = new ArrayList<Class>();
		
		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(true);
		provider.addIncludeFilter(new AssignableTypeFilter(Object.class));

		// scan in csdc.bean
		Set<BeanDefinition> components = provider.findCandidateComponents("csdc/bean");
		for (BeanDefinition component : components) {
		    Class clazz;
			try {
				clazz = Class.forName(component.getBeanClassName());
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		    result.add(clazz);
		    // use class clazz found
		}
		
		beans = result.toArray(new Class[0]);
		return beans;
	}

}
