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
package chromattic;

import junit.framework.TestCase;

import org.chromattic.api.Chromattic;
import org.chromattic.api.ChromatticBuilder;
import org.chromattic.api.ChromatticSession;

import quizzes.SuperClass;
import quizzes.PrimaryClass;
import quizzes.MixinClass;

/**
 * Created by The eXo Platform SAS
 * Author : Nguyen Duc Khoi
 *          khoi.nguyen@exoplatform.com
 * Jul 23, 2010  
 */
public class OneToOneTest extends TestCase
{
   protected Chromattic chromattic;
   protected ChromatticBuilder builder;
   @Override
   protected void setUp() throws Exception
   {
      
      builder = ChromatticBuilder.create();
      builder.add(SuperClass.class);
      builder.add(PrimaryClass.class);
      builder.add(MixinClass.class);
      chromattic = builder.build();
      super.setUp();
   }
   
   public void testRelationship()
   {
      System.out.println("abcd");
      ChromatticSession session = chromattic.openSession();
      PrimaryClass b1 = session.insert(PrimaryClass.class, "b");
      assertNull(b1.getMixin());
      MixinClass c1 = session.create(MixinClass.class);
      session.setEmbedded(b1, MixinClass.class, c1);
      assertSame(c1, b1.getMixin());
      assertSame(b1, c1.getEntity());
   }
}
