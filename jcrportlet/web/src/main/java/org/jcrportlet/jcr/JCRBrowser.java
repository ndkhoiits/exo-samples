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
package org.jcrportlet.jcr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

import javax.activation.MimetypesFileTypeMap;
import javax.jcr.ItemExistsException;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.VersionException;

import org.apache.commons.fileupload.FileItem;
import org.exoplatform.container.ExoContainer;
import org.exoplatform.container.ExoContainerContext;
import org.exoplatform.services.jcr.RepositoryService;
import org.exoplatform.services.jcr.config.RepositoryConfigurationException;
import org.exoplatform.services.jcr.core.ManageableRepository;
import org.exoplatform.services.jcr.ext.common.SessionProvider;

/**
 * Created by The eXo Platform SAS
 * Author : Nguyen Duc Khoi
 *          khoi.nguyen@exoplatform.com
 * Sep 25, 2010  
 */
public class JCRBrowser
{
   private final static String REPOSITORY_NAME = "repository";

   private static final String WORKSPACE_NAME = "portal-system";

   private static final String NT_FOLDER = "nt:folder";

   private static final String NT_FILE = "nt:file";

   private static final String JCR_MIMETYPE = "jcr:mimeType";

   private static final String JCR_ENCODING = "jcr:encoding";

   private static final String JCR_DATA = "jcr:data";

   private static final String JCR_LAST_MODIFIED = "jcr:lastModified";

   private static final String JCR_CONTENT = "jcr:content";

   private static final String NT_RESOURCE = "nt:resource";

   private SessionProvider provider;

   private RepositoryService repositoryService;

   private Session session;

   private Node node;

   public boolean connectRepository(String repositoryName) throws RepositoryException, RepositoryConfigurationException
   {
      ExoContainer container = ExoContainerContext.getCurrentContainer();
      RepositoryService repositoryService = (RepositoryService) container
            .getComponentInstanceOfType(RepositoryService.class);
      ManageableRepository repository = repositoryService.getRepository(REPOSITORY_NAME);

      provider = SessionProvider.createSystemProvider();
      Session jcrSession = provider.getSession(WORKSPACE_NAME, repository);

      this.setSession(jcrSession);

      return true;
   }

   public Node createNode(String fileId)
   {
      Node childNode;

      if (this.node == null)
         return null;

      try
      {
         childNode = node.getNode(fileId);
         if (childNode != null)
            return childNode;
      }
      catch (PathNotFoundException e)
      {
         try
         {
            childNode = this.node.addNode(fileId, NT_FOLDER);
            childNode.getSession().save();
            return childNode;
         }
         catch (ItemExistsException e1)
         {
            // TODO Auto-generated catch block
            e1.printStackTrace();
         }
         catch (PathNotFoundException e1)
         {
            // TODO Auto-generated catch block
            e1.printStackTrace();
         }
         catch (NoSuchNodeTypeException e1)
         {
            // TODO Auto-generated catch block
            e1.printStackTrace();
         }
         catch (LockException e1)
         {
            // TODO Auto-generated catch block
            e1.printStackTrace();
         }
         catch (VersionException e1)
         {
            // TODO Auto-generated catch block
            e1.printStackTrace();
         }
         catch (ConstraintViolationException e1)
         {
            // TODO Auto-generated catch block
            e1.printStackTrace();
         }
         catch (RepositoryException e1)
         {
            // TODO Auto-generated catch block
            e1.printStackTrace();
         }
      }

      catch (RepositoryException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      return null;
   }

   public Node uploadItem(String folderName, FileItem file)
   {
      Node folderNode, fileNode;
      folderNode = this.createNode(folderName);
      try
      {
         try
         {
            fileNode = folderNode.getNode(file.getName());
            if (fileNode != null)
               return fileNode;
         }
         catch (PathNotFoundException e)
         {
            //            String mimeType = new MimetypesFileTypeMap().getContentType(file);
            String mimeType = file.getContentType();
            fileNode = folderNode.addNode(file.getName(), NT_FILE);
            Node resNode = fileNode.addNode(JCR_CONTENT, NT_RESOURCE);
            resNode.setProperty(JCR_MIMETYPE, mimeType);
            resNode.setProperty(JCR_ENCODING, "");
            try
            {
               resNode.setProperty(JCR_DATA, file.getInputStream());
            }
            catch (IOException e1)
            {
               // TODO Auto-generated catch block
               e1.printStackTrace();
            }
            Calendar lastModified = Calendar.getInstance();
            //            lastModified.setTimeInMillis(file.lastModified());
            resNode.setProperty(JCR_LAST_MODIFIED, lastModified);
            fileNode.getSession().save();
            resNode.getSession().save();
            return fileNode;
         }
      }
      catch (RepositoryException e)
      {
         e.printStackTrace();
      }

      return null;
   }

   /**
    * @param repositoryService the repositoryService to set
    */
   public void setRepositoryService(RepositoryService repositoryService)
   {
      this.repositoryService = repositoryService;
   }

   /**
    * @return the repositoryService
    */
   public RepositoryService getRepositoryService()
   {
      return repositoryService;
   }

   /**
    * @param session the session to set
    * @throws RepositoryException 
    */
   public void setSession(Session session) throws RepositoryException
   {
      this.session = session;
      this.node = this.session.getRootNode();
   }

   /**
    * @return the session
    */
   public Session getSession()
   {
      return session;
   }
}
