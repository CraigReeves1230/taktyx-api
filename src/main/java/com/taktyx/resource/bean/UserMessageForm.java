/**
 * The UserMessageForm class
 * @author: Christopher Reeves <chrisreeves12@yahoo.com>
 */

package com.taktyx.resource.bean;

import java.util.List;

public class UserMessageForm implements FormInterface
{
  public Long user_id;

  public String content;

  public List<Long> service_ids;
}
