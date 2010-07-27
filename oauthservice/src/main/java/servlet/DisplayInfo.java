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
package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by The eXo Platform SAS
 * Author : Nguyen Duc Khoi
 *          khoi.nguyen@exoplatform.com
 * Jul 27, 2010  
 */
public class DisplayInfo implements Filter
{

   @Override
   public void init(FilterConfig filterConfig) throws ServletException
   {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
         ServletException
   {
      // TODO Auto-generated method stub
      HttpServletRequest httpRequest = (HttpServletRequest) request;
      httpRequest.getUserPrincipal();
      HttpServletResponse httpResponse = (HttpServletResponse) response;
      httpResponse.setContentType("text/html");
      PrintWriter out = response.getWriter();
      if (httpRequest.getAttribute("oauth_user_principal") != null)
      {
         out.print("You login with <b> " + httpRequest.getUserPrincipal() + "</b>");
      }
   }

   @Override
   public void destroy()
   {
      // TODO Auto-generated method stub
      
   }


   
}
