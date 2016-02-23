/**
 * The AbstractService class
 *
 * This class has all of the common methods that all services should have.
 * @author: Christopher Reeves
 */

package com.taktyx.service;

import com.taktyx.resource.bean.FormInterface;
import com.taktyx.resource.bean.ValidationErrors;
import com.taktyx.service.enums.ServiceResultType;
import org.hibernate.SessionFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Iterator;
import java.util.Set;

abstract public class AbstractService implements ServiceInterface
{
  private SessionFactory sessionFactory;

  @Override
  public void setDBSessionFactory(SessionFactory sessionFactory)
  {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public SessionFactory getDBSessionFactory()
  {
    return sessionFactory;
  }

  
  @Override
  public void init(ServiceLocator serviceLocator)
  {
    
  }

  /**
   * Validates the information
   * @param formInterface
   * @return
   */
  protected ServiceResult validate_form(FormInterface formInterface)
  {
    ServiceResult validationResult = new ServiceResult();

    // Validate the form
    boolean formValid = true;
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();
    ValidationErrors errors = new ValidationErrors();

    // Check for constraints set in form class
    Set<ConstraintViolation<FormInterface>> constraintViolations = validator.validate(formInterface);
    if (constraintViolations.size() > 0)
    {
      formValid = false;

      // Get errors in validation of form
      for (ConstraintViolation<FormInterface> next : constraintViolations)
      {
        errors.messages.put(next.getPropertyPath().toString(), next.getMessage());
      }

      // Return validation errors
      validationResult.setResultType(ServiceResultType.VALIDATION_ERROR);
      validationResult.setData(errors);
    }

    validationResult.setSuccess(formValid);
    return validationResult;
  }
}
