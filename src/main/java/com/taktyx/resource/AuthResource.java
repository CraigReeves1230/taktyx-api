/**
 * The AuthResource class
 *
 * Resource for authentication.
 * @author: <chrisreeves12@yahoo.com>
 */

package com.taktyx.resource;

import com.taktyx.resource.bean.LoginForm;
import com.taktyx.service.AbstractService;
import com.taktyx.service.ServiceLocator;
import com.taktyx.service.ServiceResult;
import com.taktyx.service.UserService;
import javax.servlet.ServletContext;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("auth")
public class AuthResource extends AbstractResource
{
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public ResourceResponse authenticate(LoginForm loginForm)
  {
    UserService userService = (UserService) getServiceLocator().get(UserService.class);
    
    ServiceResult serviceResult = userService.authenticate(loginForm);
    
    ResourceResponse response = new ResourceResponse();
    response.success = serviceResult.isSuccess();
    response.data = serviceResult.getData();
    
    return response;
  }
}
