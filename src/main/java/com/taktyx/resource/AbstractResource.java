/**
 * The AbstractResource class
 *
 * This class is the parent of all resources and shares
 * functions that are used in all the resources.
 * @author: Christopher Reeves <chrisreeves12@yahoo.com>
 */

package com.taktyx.resource;

import com.taktyx.resource.bean.ValidationErrors;
import com.taktyx.service.ServiceLocator;
import com.taktyx.service.ServiceResult;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import java.io.IOException;

public class AbstractResource
{
  @Context
  protected ServletContext context;

  protected ServiceLocator serviceLocator;

  public ServletContext getContext()
  {
    return context;
  }

  public ServiceLocator getServiceLocator()
  {
    if (serviceLocator != null)
    {
      serviceLocator = (ServiceLocator) getContext().getAttribute("serviceLocator");
    }

    return serviceLocator;
  }

  public void setContext(ServletContext context)
  {
    this.context = context;
  }

  protected ResourceResponse handleValidationErrors(ServiceResult result)
  {
    ValidationErrors validationErrors = (ValidationErrors) result.getData();
    ResourceResponse resourceResponse = new ResourceResponse();

    try
    {

      resourceResponse.data = new ObjectMapper().writeValueAsString(validationErrors.messages);
    }
    catch (IOException ex)
    {

    }

    return resourceResponse;
  }
}
