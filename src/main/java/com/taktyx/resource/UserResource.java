/**
 * The UserResource class
 *
 * This resource handles requests regarding users
 * @author: Christopher Reeves <chrisreeves12@yahoo.com>
 */

package com.taktyx.resource;

import com.taktyx.resource.bean.UserForm;
import com.taktyx.service.enums.ServiceResultType;
import com.taktyx.service.*;
import javax.servlet.ServletContext;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("user")
public class UserResource extends AbstractResource
{  
  @Context
  private ServletContext context;

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public ResourceResponse create(UserForm userForm)
  {
    ResourceResponse resourceResponse = new ResourceResponse();
    
    // Validate incoming form
    ServiceLocator serviceLocator = (ServiceLocator) context.getAttribute("serviceLocator");
    UserService userService = (UserService) serviceLocator.get(UserService.class);
    
    ServiceResult result = userService.create(userForm);
    if (result.isSuccess())
    {
      resourceResponse.data = result.getData();
    }
    else
    {
      if (result.getResultType() == ServiceResultType.VALIDATION_ERROR)
      {
        resourceResponse = handleValidationErrors(result);
      }
    }
    
    // Set up return response
    resourceResponse.success = result.isSuccess();
    return resourceResponse;
  }
}
