/**
 * The EventListenerInterface interface
 * @author: Christopher Reeves <chrisreeves12@yahoo.com>
 */

package com.taktyx.model.event_listener;

import com.taktyx.service.ServiceLocator;

interface EventListenerInterface
{
  public ServiceLocator getServiceLocator();

  public void  setServiceLocator(ServiceLocator serviceLocator);
}
