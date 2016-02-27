/**
 * The UserMessage class
 *
 * Represents the relationship between users and messages
 * @author: Christopher Reeves <chrisreeves12@yahoo.com>
 */

package com.taktyx.model.assoc;

import com.taktyx.model.Message;
import com.taktyx.model.Service;
import com.taktyx.model.User;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "AssocUsersMessages")
public class UserMessage implements Serializable
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  protected Long id;

  @Column(name = "date_created")
  protected Timestamp dateCreated;

  @Column(name = "date_modified")
  protected Timestamp dateModified;

  @ManyToOne(targetEntity = User.class)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  protected User user;

  @ManyToOne(targetEntity = Service.class)
  @JoinColumn(name = "service_id", referencedColumnName = "id")
  protected Service service;

  @ManyToOne(targetEntity = Message.class)
  @JoinColumn(name = "message_id", referencedColumnName = "id")
  protected Message message;

  public Service getService()
  {
    return service;
  }

  public void setService(Service service)
  {
    this.service = service;
  }

  public User getUser()
  {
    return user;
  }

  public void setUser(User user)
  {
    this.user = user;
  }

  public Message getMessage()
  {
    return message;
  }

  public void setMessage(Message message)
  {
    this.message = message;
  }

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public Timestamp getDateCreated()
  {
    return dateCreated;
  }

  public Timestamp getDateModified()
  {
    return dateModified;
  }

  public void setDateCreated(Timestamp dateCreated)
  {
    if (dateCreated != null)
    {
      this.dateCreated = dateCreated;
    }
    else
    {
      Date date = new Date();
      this.dateCreated = new Timestamp(date.getTime());
    }
  }

  public void setDateModified(Timestamp dateModified)
  {
    if (dateModified != null)
    {
      this.dateModified = dateModified;
    }
    else
    {
      Date date = new Date();
      this.dateModified = new Timestamp(date.getTime());
    }
  }
}
