package csdc.tool;

import javax.annotation.PostConstruct;

import org.hibernate.SessionFactory;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.dao.listener.ProjectAnninspectionFlushListener;
import csdc.dao.listener.ProjectApplicationReviewFlushListener;
import csdc.dao.listener.ProjectEndinspectionFlushListener;
import csdc.dao.listener.ProjectEndinspectionReviewFlushListener;
//import csdc.dao.listener.ProjectFeeFlushListener;
import csdc.dao.listener.ProjectFundingFlushListener;
import csdc.dao.listener.ProjectGrantedFlushListener;
import csdc.dao.listener.ProjectMemberFlushListener;
import csdc.dao.listener.ProjectMidinspectionFlushListener;
import csdc.dao.listener.ProjectVariationFlushListener;
import csdc.dao.listener.TeacherPersistEventListener;

@Component
public class HibernateEventWiring {

	@Autowired
	private SessionFactory sessionFactory;

	@PostConstruct
	public void registerListeners() {
		System.out.println("Start Wiring Hibernate event listeners.");
		
		EventListenerRegistry eventListenerRegistry = ((SessionFactoryImpl) sessionFactory).getServiceRegistry().getService(EventListenerRegistry.class);

		eventListenerRegistry.prependListeners(EventType.FLUSH_ENTITY, new ProjectAnninspectionFlushListener());
		eventListenerRegistry.prependListeners(EventType.FLUSH_ENTITY, new ProjectApplicationReviewFlushListener());
		eventListenerRegistry.prependListeners(EventType.FLUSH_ENTITY, new ProjectEndinspectionFlushListener());
		eventListenerRegistry.prependListeners(EventType.FLUSH_ENTITY, new ProjectEndinspectionReviewFlushListener());
//		eventListenerRegistry.prependListeners(EventType.FLUSH_ENTITY, new ProjectFeeFlushListener());
		eventListenerRegistry.prependListeners(EventType.FLUSH_ENTITY, new ProjectFundingFlushListener());
		eventListenerRegistry.prependListeners(EventType.FLUSH_ENTITY, new ProjectGrantedFlushListener());
		eventListenerRegistry.prependListeners(EventType.FLUSH_ENTITY, new ProjectMemberFlushListener());
		eventListenerRegistry.prependListeners(EventType.FLUSH_ENTITY, new ProjectMidinspectionFlushListener());
		eventListenerRegistry.prependListeners(EventType.FLUSH_ENTITY, new ProjectVariationFlushListener());
		eventListenerRegistry.prependListeners(EventType.FLUSH_ENTITY, new TeacherPersistEventListener());

		System.out.println("Finish wiring Hibernate event listeners.");
	}
}
