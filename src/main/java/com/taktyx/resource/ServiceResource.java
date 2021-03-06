/**
 * The ServiceResource class
 *
 * This resource handles requests regarding services
 * @author: Christopher Reeves <chrisreeves12@yahoo.com>
 */

package com.taktyx.resource;

import com.taktyx.model.Location;
import com.taktyx.resource.bean.AddRatingForm;
import com.taktyx.resource.bean.ServiceForm;
import com.taktyx.service.ServiceResult;
import com.taktyx.service.ServiceService;
import com.taktyx.service.enums.ServiceResultType;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("service")
public class ServiceResource extends AbstractResource
{
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public ResourceResponse create(ServiceForm serviceForm)
  {
    ResourceResponse resourceResponse = new ResourceResponse();

    // Validate incoming form
    ServiceService serviceService = (ServiceService) getServiceLocator().get(ServiceService.class);
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

  @GET
  @Path("location")
  public ResourceResponse findByLocation(@QueryParam("category") Long category, @QueryParam("latitude") Double lat, @QueryParam("longitude") Double lng)
  {
    ResourceResponse resourceResponse = new ResourceResponse();
    Location location = new Location();
    location.setLatitude(lat);
    location.setLongitude(lng);

    ServiceService serviceService = (ServiceService) getServiceLocator().get(ServiceService.class);
    ServiceResult result = serviceService.findServicesNearLocation(location, 15d, category);
    resourceResponse.success = result.isSuccess();
    resourceResponse.data = result.getData();

    return resourceResponse;
  }

  @POST
  @Path("rating")
  public ResourceResponse addRating(AddRatingForm addRatingForm)
  {
    ResourceResponse resourceResponse = new ResourceResponse();

    ServiceService serviceService = (ServiceService) getServiceLocator().get(ServiceService.class);
    ServiceResult result = serviceService.addRating(addRatingForm);
    resourceResponse.data = result.getData();
    resourceResponse.success = result.isSuccess();

    return resourceResponse;
  }
}
