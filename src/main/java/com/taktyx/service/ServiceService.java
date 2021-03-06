/**
 * The ServiceService class
 *
 * This class handles various methods regarding services
 * @author: Christopher Reeves <chrisreeves12@yahoo.com>
 */

package com.taktyx.service;

import com.taktyx.model.*;
import com.taktyx.model.assoc.ServiceRating;
import com.taktyx.resource.bean.AddRatingForm;
import com.taktyx.resource.bean.ServiceForm;
import com.taktyx.service.enums.ServiceResultType;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;

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

        service.setUser(user);

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

  /**
   * Finds a service based on location, proximity, and category
   * @param location
   * @param proximity
   * @param category
   * @return
   */
  public ServiceResult findServicesNearLocation(Location location, Double proximity, Long category)
  {
    ServiceResult serviceResult = new ServiceResult();
    ArrayList<Service> services = new ArrayList<>();

    // Find all services within the category
    Session dbSession = getDBSessionFactory().openSession();
    Category categoryObj = dbSession.get(Category.class, category);
    Criteria criteria = dbSession.createCriteria(Service.class);
    criteria.add(Restrictions.eq("category", categoryObj));
    ArrayList<Service> serviceSearchList = (ArrayList<Service>) criteria.list();
    dbSession.close();

    if (serviceSearchList.size() == 0)
    {
      // No services were found in category
      serviceResult.setSuccess(false);
      serviceResult.setResultType(ServiceResultType.NO_RESULTS_FOUND);
    }
    else
    {
      // Test location against each service
      for (Service testService : serviceSearchList)
      {
        Location testServiceLocation = testService.getAddress().getLocation();

        // Calculate distance between two points
        Double earthRadius = 6371000d;
        Double lat1 = Math.toRadians(location.getLatitude());
        Double lat2 = Math.toRadians(testServiceLocation.getLatitude());
        Double latDelta = Math.toRadians(testServiceLocation.getLatitude() - location.getLatitude());
        Double lngDelta = Math.toRadians(testServiceLocation.getLongitude() - location.getLongitude());

        Double a = Math.sin(latDelta / 2) * Math.sin(latDelta / 2) + Math.cos(lat1) * Math.cos(lat2) * Math.sin(lngDelta / 2) * Math.sin(lngDelta / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        Double distanceKm = (earthRadius * c) / 1000;
        Double distanceMiles = distanceKm * 0.621371;

        // Add it to the eligible services if it is within proximity
        if (distanceMiles <= proximity)
        {
          services.add(testService);
        }
      }

      serviceResult.setData(services);
      serviceResult.setSuccess(true);
    }

    return serviceResult;
  }

  /**
   * Add a rating to a service
   * @param addRatingForm
   * @return
   */
  public ServiceResult addRating(AddRatingForm addRatingForm)
  {
    ServiceResult serviceResult = new ServiceResult();

    try (Session dbSession = getDBSessionFactory().openSession())
    {
      // Find service, user and rating
      Service service = dbSession.get(Service.class, addRatingForm.service_id);
      User user = dbSession.get(User.class, addRatingForm.user_id);

      // Check if service and user exist
      if (user != null && service != null)
      {
        // Save rating
        dbSession.getTransaction().begin();
        Rating rating = new Rating();
        rating.setValue(addRatingForm.rating);
        rating.setUser(user);
        rating.setDateModified(null);
        rating.setDateCreated(null);
        dbSession.save(rating);

        // Create relationship between service and rating
        ServiceRating serviceRating = new ServiceRating();
        serviceRating.setService(service);
        serviceRating.setRating(rating);
        serviceRating.setDateCreated(null);
        serviceRating.setDateModified(null);
        dbSession.save(serviceRating);

        dbSession.getTransaction().commit();

        // Return success result
        serviceResult.setData(addRatingForm);
        serviceResult.setSuccess(true);
        serviceResult.setData(addRatingForm);
      }
      else
      {
        if (dbSession.isOpen())
          dbSession.close();

        serviceResult.setData("The user and/or service cannot be found in the database.");
        serviceResult.setSuccess(false);
        serviceResult.setResultType(ServiceResultType.NO_RESULTS_FOUND);
      }
    }
    catch (Exception ex)
    {
      // An error occurred while saving to database
      serviceResult.setResultType(ServiceResultType.DB_ERROR);
      serviceResult.setSuccess(false);
      serviceResult.setData(ex.getMessage());
    }

    return serviceResult;
  }

  /**
   * Calculates a rating score from the average of the incoming rating score and the exist
   * rating score. Also updates the number of ratings
   * @param service
   * @param rating
   * @return
   */
  public ServiceResult updateRatingsAttributes(Service service, Rating rating)
  {
    ServiceResult serviceResult = new ServiceResult();

    // Get current service rating
    Float currentRating = (service.getRatingScore() == null) ? 0 : service.getRatingScore();
    Float newRating = rating.getValue();

    Float calculatedRating = (currentRating + newRating) / 2;
    service.setRatingScore(calculatedRating);
    Long numberOfRatings = (service.getNumberOfRatings() == null) ? 0 : service.getNumberOfRatings();
    service.setNumberOfRatings(numberOfRatings + 1);
    serviceResult.setData(service);

    serviceResult.setSuccess(true);

    return serviceResult;
  }
}
