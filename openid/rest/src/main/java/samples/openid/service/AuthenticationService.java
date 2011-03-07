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

package samples.openid.service;

import org.exoplatform.services.rest.resource.ResourceContainer;
import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.InMemoryConsumerAssociationStore;
import org.openid4java.consumer.InMemoryNonceVerifier;
import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.message.AuthRequest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * @author <a href="mailto:khoi.nguyen@exoplatform.com">Nguyen Duc Khoi</a>  
 * Feb 24, 2011
 */

@Path("Authenticate")
public class AuthenticationService implements ResourceContainer
{
   @GET
   @Path("/hello/{username}/")
   public Response hello(@QueryParam("username") String name)
   {
      ConsumerManager manager;
      try
      {
         manager = new ConsumerManager();

         manager.setAssociations(new InMemoryConsumerAssociationStore());
         manager.setNonceVerifier(new InMemoryNonceVerifier(5000));
         String openId = name;
         List discoveries = manager.discover(openId);
         DiscoveryInformation discovered = manager.associate(discoveries);
         
         AuthRequest authReq = manager.authenticate(discovered, "http://khoinguyen.info");
         return Response.ok(authReq.getDestinationUrl(true)).build();
      }
      catch (Exception exp)
      {
         return Response.ok(exp.toString()).build();
      }
   }
}
