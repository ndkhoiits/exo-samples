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
package org.jcrportlet.portlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.RenderMode;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.portlet.PortletFileUpload;
import org.exoplatform.container.ExoContainer;
import org.exoplatform.container.ExoContainerContext;
import org.exoplatform.download.DownloadResource;
import org.exoplatform.download.DownloadService;
import org.exoplatform.download.InputStreamDownloadResource;
import org.exoplatform.services.jcr.config.RepositoryConfigurationException;
import org.jcrportlet.jcr.JCRBrowser;

/**
 * Created by The eXo Platform SAS
 * Author : Nguyen Duc Khoi
 *          khoi.nguyen@exoplatform.com
 * Sep 24, 2010  
 */
public class JCRPortlet extends GenericPortlet
{
   public void doView(RenderRequest req, RenderResponse resp) throws PortletException, IOException
   {
      getPortletContext().getRequestDispatcher("/jsp/index.jsp").include(req, resp);
   }

   @Override
   public void processAction(ActionRequest request, ActionResponse response) throws PortletException, IOException
   {
      // TODO Auto-generated method stub
      if (PortletFileUpload.isMultipartContent(request))
      {
         FileItemFactory factory = new DiskFileItemFactory();
         PortletFileUpload fu = new PortletFileUpload(factory);
         List<FileItem> list;
         try
         {
            FileItem item = null;
            list = fu.parseRequest(request);
            for (FileItem fileItem : list)
            {
               item = fileItem;
            }

            JCRBrowser jcrBrowser = new JCRBrowser();
            jcrBrowser.connectRepository("repository");

            Node fileNode = jcrBrowser.uploadItem("hehe", item);
         }
         catch (FileUploadException e)
         {
            e.printStackTrace();
         }
         catch (RepositoryException e)
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
         catch (RepositoryConfigurationException e)
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
   }

   public static Map<String, String> getDownloadLinks() throws IOException
   {
      Map<String, String> links = new HashMap<String, String>();
      JCRBrowser jcrBrowser = new JCRBrowser();
      try
      {
         jcrBrowser.connectRepository("repository");

         ExoContainer container = ExoContainerContext.getCurrentContainer();
         DownloadService downloadService = (DownloadService) container
               .getComponentInstanceOfType(DownloadService.class);

         Node folderNode = jcrBrowser.createNode("hehe");
         for (NodeIterator i = folderNode.getNodes(); i.hasNext();)
         {
            Node fileNode = i.nextNode();
            Node resNode = fileNode.getNode("jcr:content");

            InputStream stream = resNode.getProperty("jcr:data").getStream();

            DownloadResource downloadResource = new InputStreamDownloadResource(stream, resNode.getProperty(
                  "jcr:mimeType").getString());
            downloadResource.setDownloadName(fileNode.getName());
            String downloadLink = downloadService
                  .getDownloadLink(downloadService.addDownloadResource(downloadResource));
            links.put(fileNode.getName(), downloadLink);
            InputStream is = downloadResource.getInputStream();
         }

      }
      catch (RepositoryException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      catch (RepositoryConfigurationException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      
      return links;
   }
}
