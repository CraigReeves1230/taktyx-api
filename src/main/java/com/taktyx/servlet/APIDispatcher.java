/**
 * This overrides the standard jersey servlet
 *
 * @author: Christopher Reeves <chrisreeves12@yahoo.com>
 */

package com.taktyx.servlet;

import org.glassfish.jersey.servlet.ServletContainer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "APIDispatcher", urlPatterns = {"/api/*"}, initParams = {
  @WebInitParam(name = "jersey.config.server.provider.packages", value = "com.taktyx.resource")
})
public class APIDispatcher extends ServletContainer
{
  @Override
  public void init() throws ServletException
  {
    super.init();
  }
}
