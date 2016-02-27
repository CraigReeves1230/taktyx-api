/**
 * The MessageService class
 * @author: Christopher Reeves <chrisreeves12@yahoo.com>
 */

package com.taktyx.service;

import com.taktyx.model.Message;
import com.taktyx.model.Service;
import com.taktyx.model.User;
import com.taktyx.model.assoc.ServiceMessage;
import com.taktyx.model.assoc.UserMessage;
import com.taktyx.resource.bean.ServiceMessageForm;
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

  /**
   * Create a message that gets sent to the user from a service
   * @param serviceMessageForm
   * @return
   */
  public ServiceResult createServiceMessage(ServiceMessageForm serviceMessageForm)
  {
    ServiceResult serviceResult = new ServiceResult();

    try (Session dbSession = getDBSessionFactory().openSession())
    {
      Message message = new Message();

      // Load service
      Service service = dbSession.get(Service.class, serviceMessageForm.service_id);

      if (service != null)
      {
        // Find user of service
        User user = service.getUser();
        if (user == null)
        {
          // The user of the service cannot be found
          if (dbSession.isOpen())
            dbSession.close();

          serviceResult.setSuccess(false);
          serviceResult.setData("The user of the service cannot be found.");
          serviceResult.setResultType(ServiceResultType.NO_RESULTS_FOUND);
        }
        else
        {
          // Save user of service as author
          dbSession.getTransaction().begin();
          message.setContent(serviceMessageForm.message);
          message.setUser(user);
          dbSession.save(message);

          // Load recipients
          for (Long recipient_id : serviceMessageForm.user_ids)
          {
            User recipient = dbSession.get(User.class, recipient_id);
            if (recipient != null)
            {
              // Save user message relationship
              UserMessage userMessage = new UserMessage();
              userMessage.setUser(recipient);
              userMessage.setService(service);
              userMessage.setMessage(message);
              userMessage.setDateModified(null);
              userMessage.setDateCreated(null);
              dbSession.save(userMessage);
            }
          }

          // Commit to database
          dbSession.getTransaction().commit();

          // Set successful return
          serviceResult.setSuccess(true);
          serviceResult.setData(serviceMessageForm);
        }
      }
      else
      {
        // The service couldn't be located
        if (dbSession.isOpen())
          dbSession.close();

        serviceResult.setSuccess(false);
        serviceResult.setResultType(ServiceResultType.NO_RESULTS_FOUND);
        serviceResult.setData("The service could sending the message could not be loacted");
      }

    }
    catch (Exception ex)
    {
      serviceResult.setSuccess(false);
      serviceResult.setData(ex.getMessage());
      serviceResult.setResultType(ServiceResultType.DB_ERROR);
    }

    return serviceResult;
  }
}
