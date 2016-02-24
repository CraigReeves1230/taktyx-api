/**
 * The MessageResource class
 * @author: Christopher Reeves <chrisreeves12@yahoo.com>
 */

package com.taktyx.resource;

import com.taktyx.resource.bean.UserMessageForm;
import com.taktyx.service.MessageService;
import com.taktyx.service.ServiceResult;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("message")
public class MessageResource extends AbstractResource
{
  @POST
  @Path("user")
  @Produces(MediaType.APPLICATION_JSON)
  public ResourceResponse createUserMessage(UserMessageForm userMessageForm)
  {
    ResourceResponse resourceResponse = new ResourceResponse();
    MessageService messageService = (MessageService) getServiceLocator().get(MessageService.class);

    ServiceResult result = messageService.createUserMessage(userMessageForm);
    resourceResponse.success = result.isSuccess();
    resourceResponse.data = result.getData();

    return resourceResponse;
  }
}
