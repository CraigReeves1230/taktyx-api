/**
 * The UserService class
 *
 * This class represents the user service that does many things regarding users
 * @author: Christopher Reeves <chrisreeves12@yahoo.com>
 */

package com.taktyx.service;

import com.taktyx.model.User;
import com.taktyx.resource.bean.LoginForm;
import com.taktyx.resource.bean.ValidationErrors;
import com.taktyx.resource.bean.UserForm;
import com.taktyx.service.enums.ServiceResultType;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class UserService extends AbstractService
{
  /**
   * Creates a user from registration
   * @param userForm
   * @return
   */
  public ServiceResult create (UserForm userForm)
  {
    ServiceResult serviceResult = new ServiceResult();
    
    // Validate the form
    ServiceResult validationResult = validate_form(userForm);
    if (validationResult.isSuccess())
    {
      try (Session dbSession = getDBSessionFactory().openSession())
      {
        // Check if there is a user with the same email
        if (!_check_duplicate_email(userForm.email, dbSession))
        {
          // Duplicate user exists, exit
          validationResult.setSuccess(false);
          validationResult.setResultType(ServiceResultType.VALIDATION_ERROR);
          ValidationErrors errors = new ValidationErrors();
          errors.messages.put("email", "A user with this email address already exists in our records.");
          validationResult.setData(errors);
          serviceResult = validationResult;
        }
        else
        {
          // Persist the user
          Transaction transaction = dbSession.beginTransaction();
          User user = new User();
          user.setFirstName(userForm.firstName);
          user.setLastName(userForm.lastName);
          user.setEmail(userForm.email);
          user.setPhoneNumber(userForm.phoneNumber);
          user.setInactive(false);
          user.setPassword(BCrypt.hashpw(userForm.password, BCrypt.gensalt()));
          user.setRole("member");
          user.setDateCreated(null);
          user.setDateModified(null);
          dbSession.save(user);

          transaction.commit();

          // Return successful result
          serviceResult.setSuccess(true);
          serviceResult.setData(userForm);
          serviceResult.setResultType(ServiceResultType.SUCCESS);
        }
      }
      catch (Exception e)
      {
        // Handle error
        serviceResult.setSuccess(false);
        serviceResult.setResultType(ServiceResultType.DB_ERROR);
        serviceResult.setData(e.getMessage());
      }
    }
    else
    {
      // The form was invalid
      serviceResult = validationResult;
    }
    
    return serviceResult;
  }

  /**
   * Authenticates a user
   * @param loginForm
   * @return
   */
  public ServiceResult authenticate(LoginForm loginForm)
  {
    ServiceResult serviceResult = new ServiceResult();
    
    // Validate form
    ServiceResult validationResult = validate_form(loginForm);
    if (validationResult.isSuccess())
    {
      // Authenticate the user
      Session dbSession = getDBSessionFactory().openSession();
      Criteria criteria = dbSession.createCriteria(User.class);
      criteria.add(Restrictions.like("email", loginForm.email));
      
      if (criteria.list().size() == 0)
      {
        // The user cannot be found
        serviceResult.setSuccess(false);
        serviceResult.setData("The email and/or password does not match our records.");
      }
      else
      {
        serviceResult.setSuccess(true);
        User user = (User) criteria.list().get(0);
        
        // Check for password match
        if (BCrypt.checkpw(loginForm.password, user.getPassword()))
        {          
          // Set session id
          Date curDate = new Date();
          try
          {
            MessageDigest md = MessageDigest.getInstance("MD5");
            String timeString = curDate.toString();
            byte[] digest = md.digest(timeString.getBytes());
            String sessionId = digest.toString();
            
            dbSession.beginTransaction();
            user.setSessionId(sessionId);
            dbSession.getTransaction().commit();
          }
          catch (NoSuchAlgorithmException ex)
          {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
          }
          
          serviceResult.setSuccess(true);
          serviceResult.setData(user);
        }
        else
        {
          // The password was incorrect
          serviceResult.setSuccess(false);
          serviceResult.setData("The email and/or password does not match our records.");
        }
      }
    }
    else
    {
      serviceResult = validationResult;
    }
    
    return serviceResult;
  }

  /**
   * Checks if a user with the passed in email address exists in the database
   * @param email
   * @param dbSession
   * @return
   */
  private boolean _check_duplicate_email(String email, Session dbSession)
  {
    Criteria criteria = dbSession.createCriteria(User.class);
    criteria.add(Restrictions.like("email", email));
    return criteria.list().size() <= 0;
  }
}
