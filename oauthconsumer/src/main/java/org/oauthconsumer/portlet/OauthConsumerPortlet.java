/**
 * Copyright (C) 2009 eXo Platform SAS.
 * 
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 * 
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.oauthconsumer.portlet;

import org.oauthconsumer.service.OAuthStoreServicePortlet;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.ProcessAction;
import javax.portlet.RenderMode;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author <a href="mailto:khoi.nguyen@exoplatform.com">Nguyen Duc Khoi</a>  
 * Nov 24, 2010
 */
public class OauthConsumerPortlet extends GenericPortlet
{
   @RenderMode(name = "view")
   public void doView1(RenderRequest request, RenderResponse response) throws PortletException, IOException
   {
      OAuthStoreServicePortlet oAuthStore = new OAuthStoreServicePortlet();
      oAuthStore.addKey();
      getPortletContext().getRequestDispatcher("/jsp/index.jsp").include(request, response);
   }
   
   @RenderMode(name = "edit")
   public void edit(RenderRequest request, RenderResponse response) throws PortletException, IOException 
   {
      getPortletContext().getRequestDispatcher("/jsp/index.jsp").include(request, response);
   }
   
   @ProcessAction(name = "addConsumer")
   public void addConsumer(ActionRequest request, ActionResponse response)
   {
      
   }
}
