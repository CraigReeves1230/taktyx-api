/**
 * The MessageService class
 * @author: Christopher Reeves <chrisreeves12@yahoo.com>
 */

package com.taktyx.service;

import com.taktyx.model.Message;
import com.taktyx.model.Service;
import com.taktyx.model.User;
import com.taktyx.model.assoc.ServiceMessage;
import com.taktyx.resource.bean.UserMessageForm;
import com.taktyx.service.enums.ServiceResultType;
import org.hibernate.Session;

public class MessageService extends AbstractService
{
  /**
   * Create service message
   * @param userMessageForm
   * @return
   */
  public ServiceResult createUserMessage(UserMessageForm userMessageForm)
  {
    ServiceResult serviceResult = new ServiceResult();

    // Create a message
    try (Session dbSession = getDBSessionFactory().openSession())
    {
      Message message = new Message();

      // Load user
      User user = dbSession.get(User.class, userMessageForm.user_id);
      if (user == null)
      {
        // The user cannot be found
        dbSession.close();
        serviceResult.setSuccess(false);
        serviceResult.setData("The user cannot be found.");
        serviceResult.setResultType(ServiceResultType.NO_RESULTS_FOUND);
      }
      else
      {
        // Save message
        dbSession.beginTransaction();
        message.setContent(userMessageForm.content);
        message.setDateCreated(null);
        message.setDateModified(null);
        message.setUser(user);
        dbSession.save(message);
        dbSession.getTransaction().commit();

        // Save relationships so that services receive the message
        for (Long service_id : userMessageForm.service_ids)
        {
          // Get service
          Service service = dbSession.get(Service.class, service_id);
          if (service != null)
          {
            ServiceMessage serviceMessage = new ServiceMessage();
            serviceMessage.setDateCreated(null);
            serviceMessage.setDateModified(null);
            serviceMessage.setMessage(message);
            serviceMessage.setService(service);
            serviceMessage.setRead(false);
            dbSession.save(serviceMessage);
          }
        }

        // Create service result
        serviceResult.setData(message);
        serviceResult.setSuccess(true);
      }
    }
    catch(Exception ex)
    {
      // An error occurred saving the message
      serviceResult.setData(ex.getMessage());
      serviceResult.setSuccess(false);
      serviceResult.setResultType(ServiceResultType.DB_ERROR);
    }

    return serviceResult;
  }
}
