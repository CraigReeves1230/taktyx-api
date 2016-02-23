/**
 * The ValidationErrors class
 *
 * This class represents errors made during form validation
 * @author: Christopher Reeves <chrisreeves12@yahoo.com>
 */

package com.taktyx.resource.bean;

import java.util.HashMap;

public class ValidationErrors
{
  public String elementName;
  
  public HashMap<String, String> messages;
  
  public ValidationErrors()
  {
    messages = new HashMap<>();
  }
}
