/**
 * The ServiceResult class
 *
 * This class represents a result from a service method
 * @author: Christopher Reeves <chrisreeves12@yahoo.com>
 */

package com.taktyx.service;

import com.taktyx.service.enums.ServiceResultType;

public class ServiceResult
{
  private ServiceResultType resultType;
  
  private Object data;
  
  private boolean success;

  public Object getData()
  {
    return data;
  }

  public void setData(Object data)
  {
    this.data = data;
  }

  public boolean isSuccess()
  {
    return success;
  }

  public void setSuccess(boolean success)
  {
    this.success = success;
  }

  public ServiceResultType getResultType()
  {
    return resultType;
  }

  public void setResultType(ServiceResultType resultType)
  {
    this.resultType = resultType;
  }
}
