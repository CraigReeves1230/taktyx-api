/**
 * The ServiceResource class
 *
 * This resource handles requests regarding services
 * @author: Christopher Reeves <chrisreeves12@yahoo.com>
 */

package com.taktyx.resource;

import com.taktyx.resource.bean.ServiceForm;
import com.taktyx.service.ServiceLocator;
import com.taktyx.service.ServiceResult;
import com.taktyx.service.ServiceService;
import com.taktyx.service.enums.ServiceResultType;

import javax.servlet.ServletContext;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;


@Path("service")
public class ServiceResource extends AbstractResource
{
  @Context
  private ServletContext context;

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public ResourceResponse create(ServiceForm serviceForm)
  {
    ResourceResponse resourceResponse = new ResourceResponse();

    // Validate incoming form
    ServiceLocator serviceLocator = (ServiceLocator) context.getAttribute("serviceLocator");
    ServiceService serviceService = (ServiceService) serviceLocator.get(ServiceService.class);
    ServiceResult result = serviceService.create(serviceForm);

    resourceResponse.success = result.isSuccess();
    resourceResponse.data = result.getData();

    // Handle success creation of service
    if (result.isSuccess())
    {
      resourceResponse.data = result.getData();
    }
    else
    {
      if (result.getResultType() == ServiceResultType.VALIDATION_ERROR)
      {
        if (result.getResultType() == ServiceResultType.VALIDATION_ERROR)
        {
          resourceResponse = handleValidationErrors(result);
        }
      }
    }

    resourceResponse.success = result.isSuccess();
    return resourceResponse;
  }
}
