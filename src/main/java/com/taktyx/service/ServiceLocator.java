/**
 * The ServiceLocator class
 *
 * Manages, instantiates services that implement ServiceInterface
 * @author: Christopher Reeves <chrisreeves12@yahoo.com>
 */

package com.taktyx.service;

import java.util.HashMap;
import org.hibernate.SessionFactory;

public class ServiceLocator
{
  private final HashMap<Class<?>, ServiceInterface> services;
  private SessionFactory sessionFactory;

  public ServiceLocator()
  {
    this.services = new HashMap<>();
  }

  public SessionFactory getSessionFactory()
  {
    return sessionFactory;
  }

  public void setSessionFactory(SessionFactory sessionFactory)
  {
    this.sessionFactory = sessionFactory;
  }
  
  /**
   * Retrieves service by passing in the class
   * @param class_key
   * @return 
   */
  public ServiceInterface get(Class class_key)
  {
    ServiceInterface service = null;
    
    // Check if service has already been created, if so use it
    if (services.containsKey(class_key) && services.get(class_key) != null)
    {
      // If the service has already been created, get it from storage here
      service = services.get(class_key); 
    }
    else
    {
      // Attempt to create the service and initialize it
      try
      {
        Object service_instance = class_key.getConstructor().newInstance();
        
        if (!(service_instance instanceof ServiceInterface))
        {
          throw new ClassNotFoundException("Services must implement the ServiceInterface.");
        }

        // Initialize the service
        service = (ServiceInterface) class_key.getConstructor().newInstance();
        service.init(this);
        service.setDBSessionFactory(sessionFactory);
        
        // Add it to table of services for later use
        services.put(class_key, service);
      }
      catch (Throwable ex)
      {
        System.err.println("Error creating service: " + ex.getMessage());
      }
    }
    
    return service;
  }
}
