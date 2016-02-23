/**
 * The ServiceService class
 *
 * This class handles various methods regarding services
 * @author: Christopher Reeves <chrisreeves12@yahoo.com>
 */

package com.taktyx.service;

import com.taktyx.model.*;
import com.taktyx.model.assoc.UserService;
import com.taktyx.resource.bean.ServiceForm;
import com.taktyx.service.enums.ServiceResultType;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ServiceService extends AbstractService
{
  private AddressService addressService;

  @Override
  public void init(ServiceLocator serviceLocator)
  {
    addressService = (AddressService) serviceLocator.get(AddressService.class);
  }

  /**
   * This creates a service saves it
   * @param serviceForm
   * @return
   */
  public ServiceResult create(ServiceForm serviceForm)
  {
    ServiceResult serviceResult = new ServiceResult();

    // Validate form
    ServiceResult validationResult = validate_form(serviceForm);
    if (validationResult.isSuccess())
    {
      serviceResult.setData(serviceForm);

      // Attempt to save the service
      try (Session dbSession = getDBSessionFactory().openSession())
      {
        Transaction transaction = dbSession.beginTransaction();
        Service service = new Service();
        service.setName(serviceForm.name);
        service.setEmail(serviceForm.email);
        service.setPhoneNumber(serviceForm.phoneNumber);
        service.setDescription(serviceForm.description);
        service.setInactive(serviceForm.inactive);

        // Find the category
        Category category = dbSession.get(Category.class, serviceForm.category_id);
        if (category == null)
        {
          // The category could not be found
          transaction.rollback();
          dbSession.close();
          throw new Exception("The category id cannot be matched to a category in our database.");
        }

        service.setCategory(category);

        // Create an address
        Address service_address = new Address();
        service_address.setAddress1(serviceForm.address_line_1);
        service_address.setAddress2(serviceForm.address_line_2);
        service_address.setCity(serviceForm.city);
        service_address.setZipCode(serviceForm.zipcode);
        service_address.setCompany(serviceForm.name);
        service_address.setState(serviceForm.state);
        service_address.setDateCreated(null);
        service_address.setDateModified(null);
        dbSession.save(service_address);
        service.setAddress(service_address);
        service.setDateCreated(null);
        service.setDateModified(null);

        // Find the location for this address
        ServiceResult addressServiceResult = addressService.createLocation(service_address);
        Location serviceAddressLocation = (Location) addressServiceResult.getData();
        dbSession.save(serviceAddressLocation);
        service_address.setLocation(serviceAddressLocation);

        // Locate the user
        User user = dbSession.get(User.class, serviceForm.user_id);
        if (user == null)
        {
          // The user cannot be found
          transaction.rollback();
          dbSession.close();
          throw new Exception("You must be logged in to add a service.");
        }

        // Add relationship between service and user and save to database
        UserService userService = new UserService();
        userService.setUser(user);
        userService.setService(service);
        userService.setDateCreated(null);
        userService.setDateModified(null);
        dbSession.save(userService);

        dbSession.save(service);
        transaction.commit();

        // Return form
        serviceResult.setSuccess(true);
        serviceResult.setData(serviceForm);
      }
      catch (Exception ex)
      {
        // An error occurred
        serviceResult.setSuccess(false);
        serviceResult.setData(ex.getMessage());
        serviceResult.setResultType(ServiceResultType.DB_ERROR);
      }

    }
    else
    {
      // Validation failed
      serviceResult.setSuccess(false);
      serviceResult = validationResult;
    }

    return serviceResult;
  }
}
