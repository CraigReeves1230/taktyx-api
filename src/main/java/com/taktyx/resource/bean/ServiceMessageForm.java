package com.taktyx.resource.bean;

import java.util.ArrayList;

/**
 * Created by Christopher Reeves
 */
public class ServiceMessageForm implements FormInterface
{
  public Long service_id;

  public String message;

  public ArrayList<Long> user_ids;
}
