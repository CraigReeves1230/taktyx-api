package com.taktyx.service;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * The Hibernate Session Factory service
 * @author Christopher Reeves
 */
public class SessionFactoryService
{
  static private SessionFactory firstInstance = null;
  
  /**
   * Returns an instance of Hibernate session factory
   * @return SessionFactory
   */
  static public SessionFactory getInstance()
  {
    if (firstInstance == null)
    {
      // A SessionFactory is set up once for an application!
      final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
              .configure() // configures settings from hibernate.cfg.xml
              .build();
      try {
          firstInstance = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
          System.out.println("Session Factory Is Created");
      }
      catch (Exception ex) {
          StandardServiceRegistryBuilder.destroy( registry );
          throw new ExceptionInInitializerError(ex);
      }
    }
    
    return firstInstance;
  }
}