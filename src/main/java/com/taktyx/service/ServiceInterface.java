/**
 * The ServiceInterface class
 *
 * Services must implement this interface to be used by the ServiceLocator
 * @author: Christopher Reeves
 */

package com.taktyx.service;

import org.hibernate.SessionFactory;

public interface ServiceInterface
{
  public void setDBSessionFactory(SessionFactory sessionFactory);
  
  public SessionFactory getDBSessionFactory();
  
  /**
   * Called when a service is created
   * @param serviceLocator
   */
  public void init(ServiceLocator serviceLocator);
}
