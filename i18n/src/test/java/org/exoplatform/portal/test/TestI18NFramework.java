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
package org.exoplatform.portal.test;

import org.exoplatform.portal.i18n.I18NFramework;
import org.exoplatform.portal.i18n.I18Nized;
import org.exoplatform.portal.i18n.Injector;
import org.exoplatform.portal.i18n.Language;
import org.exoplatform.portal.i18n.LanguageSpace;

import java.util.Locale;

import junit.framework.TestCase;

import org.chromattic.api.Chromattic;
import org.chromattic.api.ChromatticBuilder;
import org.chromattic.api.ChromatticSession;

/**
 * @author <a href="mailto:khoi.nguyen@exoplatform.com">Nguyen Duc Khoi</a>
 * Apr 21, 2011
 */
public class TestI18NFramework extends TestCase
{
   Chromattic chromattic;
   ChromatticBuilder builder;
   @Override
   protected void setUp() throws Exception
   {
      builder = ChromatticBuilder.create();
      builder.add(I18Nized.class);
      builder.add(LanguageSpace.class);
      builder.add(Language.class);
      builder.add(NavigationNode.class);
      builder.add(Described.class);
      chromattic = builder.build();
      super.setUp();
   }
   
   public void testI18N()
   {
      String homepage_en = "Homepage";
      String homepage_vi = "Trangchu";
      ChromatticSession session = chromattic.openSession();
      session.addEventListener(new Injector(session));
      NavigationNode node = session.insert(NavigationNode.class, "node1");
      
      Described described = session.getEmbedded(node, Described.class);
      if (described == null)
      {
         described = session.create(Described.class);
      }
      
      session.setEmbedded(node, Described.class, described);

      I18NFramework framework = new I18NFramework(session);
      I18Nized i18n = framework.createI18nMixin(node);
      Described describe_en = i18n.putMixin(Described.class, "en");
      describe_en.setName(homepage_en);
      
      Described describe_vi = i18n.putMixin(Described.class, "vi");
      describe_vi.setName(homepage_vi);
      
      Language language = session.findByPath(Language.class, "node1/gtn:languages/en");
      assertNotNull(language);
      Described describe_en_new = session.getEmbedded(language, Described.class);
      assertEquals(describe_en_new.getName(), homepage_en);
      
      language = session.findByPath(Language.class, "node1/gtn:languages/vi");
      assertNotNull(language);
      Described describe_vi_new = session.getEmbedded(language, Described.class);
      assertEquals(describe_vi_new.getName(), homepage_vi);
      
      Described describe_newvalue = i18n.putMixin(Described.class, "vi");
      System.out.println(describe_newvalue.getName());
      
      session.save();
      session.close();
   }
}
