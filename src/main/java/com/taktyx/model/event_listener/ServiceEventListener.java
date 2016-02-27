/**
 * The ServiceEventListener class
 *
 * This handles event listeners that affect services
 * @author: Christopher Reeves <chrisreeves12@yahoo.com>
 */

package com.taktyx.model.event_listener;

import com.taktyx.model.Rating;
import com.taktyx.model.Service;
import com.taktyx.model.assoc.ServiceRating;
import com.taktyx.service.ServiceLocator;
import com.taktyx.service.ServiceResult;
import com.taktyx.service.ServiceService;
import org.hibernate.HibernateException;
import org.hibernate.event.spi.FlushEntityEvent;
import org.hibernate.event.spi.FlushEntityEventListener;

import java.util.ArrayList;
import java.util.List;

public class ServiceEventListener implements FlushEntityEventListener, EventListenerInterface
{
  private List<Service> servicesUpdated = new ArrayList<>();

  private ServiceLocator serviceLocator;

  @Override
  public void onFlushEntity(FlushEntityEvent flushEntityEvent) throws HibernateException
  {
    Object entity = flushEntityEvent.getEntity();
    if (entity instanceof ServiceRating)
    {
      ServiceRating serviceRating = (ServiceRating) entity;
      Service service = serviceRating.getService();
      Rating rating = serviceRating.getRating();

      // Update ratings average on service
      if (!servicesUpdated.contains(service))
      {
        ServiceService serviceService = (ServiceService) getServiceLocator().get(ServiceService.class);
        ServiceResult serviceResult = serviceService.updateRatingsAttributes(service, rating);
        if (serviceResult.isSuccess())
        {
          service = (Service) serviceResult.getData();
          servicesUpdated.add(service);
          flushEntityEvent.getSession().save(service);
          flushEntityEvent.getSession().flush();
        }
      }
    }
  }

  public ServiceLocator getServiceLocator()
  {
    return serviceLocator;
  }

  public void setServiceLocator(ServiceLocator serviceLocator)
  {
    this.serviceLocator = serviceLocator;
  }
}
