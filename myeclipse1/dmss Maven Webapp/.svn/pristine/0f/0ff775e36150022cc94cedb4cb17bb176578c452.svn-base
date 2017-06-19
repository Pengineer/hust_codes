package org.csdc.dao.listener;

import javax.annotation.PostConstruct;

import org.hibernate.SessionFactory;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * Hibernate事件监听管理器
 * @author jintf
 * @date 2014-6-15
 */
@Component
public class HibernateEventWiring {
	@Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private OnDeleteListener  onDeleteListener;

    @PostConstruct
    public void registerListeners() {
        EventListenerRegistry registry = ((SessionFactoryImpl) sessionFactory).getServiceRegistry().getService(
        EventListenerRegistry.class);
        registry.getEventListenerGroup(EventType.DELETE).appendListener(onDeleteListener);
    }
}
