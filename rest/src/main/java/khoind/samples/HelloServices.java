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
package khoind.samples;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.exoplatform.application.gadget.Gadget;
import org.exoplatform.application.gadget.GadgetRegistryService;
import org.exoplatform.container.PortalContainer;
import org.exoplatform.portal.config.DataStorage;
import org.exoplatform.portal.config.Query;
import org.exoplatform.portal.config.model.Application;
import org.exoplatform.portal.config.model.ApplicationType;
import org.exoplatform.portal.config.model.Container;
import org.exoplatform.portal.config.model.Dashboard;
import org.exoplatform.portal.config.model.ModelObject;
import org.exoplatform.portal.config.model.Page;
import org.exoplatform.portal.config.model.PortalConfig;
import org.exoplatform.services.rest.resource.ResourceContainer;
import org.json.JSONObject;
import java.util.List;

/**
 * @author <a href="mailto:ndkhoi168@gmail.com">Nguyen Duc Khoi</a>
 */

@Path("helloServices")
public class HelloServices implements ResourceContainer
{
   @GET
   @Path("/hello/username")
   public Response hello(@QueryParam("name") String name)
   {
      return Response.created(UriBuilder.fromUri("calculator/hello").build()).entity("Hello " + name).type(MediaType.TEXT_PLAIN).status(Response.Status.OK).build();
   }

   @GET
   @Path("/gadget")
   public Response getGatgetList(@QueryParam("username") String username) throws Exception
   {
      String responseContent = "hello";
      PortalContainer container = PortalContainer.getInstance();
      DataStorage storage = (DataStorage)container.getComponentInstanceOfType(DataStorage.class);
      Query<Page> query = new Query<Page>(PortalConfig.USER_TYPE, username, Page.class);
      List<Page> results = storage.find(query).getAll();
      for (Page page : results)
      {
         String id = page.getChildren().get(0).getStorageId();
         Application app = (Application)page.getChildren().get(0);
         app.getType().getName();
         Dashboard dashboard = storage.loadDashboard(id);
         getGadget(dashboard);
      }

      return Response.created(UriBuilder.fromUri("helloServices/gadget").build()).entity(responseContent).type(MediaType.TEXT_PLAIN_TYPE).status(Response.Status.OK).build();

   }

   public void getGadget(Object model) throws Exception
   {
      if (model instanceof Container)
      {
         Container container = (Container) model;
         List<ModelObject> children = container.getChildren();
         for (Object child : children)
         {
            getGadget(child);
         }
      }
      else if (model instanceof Application)
      {
         Application application = (Application)model;
         if (((Application)model).getType() == ApplicationType.GADGET)
         {
            PortalContainer container = PortalContainer.getInstance();
            DataStorage storage = (DataStorage)container.getComponentInstanceOfType(DataStorage.class);
            GadgetRegistryService gadgetRegistryService = (GadgetRegistryService)container.getComponentInstanceOfType(GadgetRegistryService.class);
            String gadgetId = storage.getId(application.getState());
            Gadget gadgetModel = gadgetRegistryService.getGadget(gadgetId);
            System.out.println(gadgetModel.getName());
         }
      }

   }

   private String buildResponse(String dataType, List<?> reults)
   {
      JSONObject buffer = new JSONObject();

      return null;
   }
}
