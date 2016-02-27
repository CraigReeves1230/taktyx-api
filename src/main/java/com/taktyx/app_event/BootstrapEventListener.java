/**
 * Where all initialization takes place to start the system.
 * @author: Christopher Reeves <chrisreeves12@yahoo.com>
 */

package com.taktyx.app_event;

import com.taktyx.model.event_listener.ServiceEventListener;
import com.taktyx.service.ServiceLocator;
import com.taktyx.service.SessionFactoryService;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.hibernate.SessionFactory;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;

@WebListener
public class BootstrapEventListener implements ServletContextListener
{

  @Override
  public void contextInitialized(ServletContextEvent sce)
  {
    // Initialize Hibernate Session Factory
    sce.getServletContext().setAttribute("sessionFactory", SessionFactoryService.getInstance());

    // Initialize service locator
    ServiceLocator serviceLocator = new ServiceLocator();
    serviceLocator.setSessionFactory((SessionFactory) sce.getServletContext().getAttribute("sessionFactory"));
    sce.getServletContext().setAttribute("serviceLocator", serviceLocator);

    // Initialize Hibernate event listeners
    EventListenerRegistry eventListenerRegistry = ((SessionFactoryImpl) SessionFactoryService.getInstance())
      .getServiceRegistry().getService(EventListenerRegistry.class);

    ServiceEventListener serviceEventListener = new ServiceEventListener();
    serviceEventListener.setServiceLocator(serviceLocator);
    eventListenerRegistry.getEventListenerGroup(EventType.FLUSH_ENTITY).appendListener(serviceEventListener);
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce)
  {
    
  }
}
