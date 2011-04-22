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
package org.exoplatform.portal.i18n;

import org.chromattic.api.annotations.Create;
import org.chromattic.api.annotations.MappedBy;
import org.chromattic.api.annotations.MixinType;
import org.chromattic.api.annotations.OneToOne;
import org.chromattic.api.annotations.Owner;



/**
 * @author <a href="mailto:khoi.nguyen@exoplatform.com">Nguyen Duc Khoi</a>
 * Apr 15, 2011
 */

@MixinType(name = "gtn:i18nized")

public abstract class I18Nized
{
   @Create
   public abstract LanguageSpace createLanguageSpace();
   
   @OneToOne
   @Owner
   @MappedBy("gtn:languages")
   public abstract LanguageSpace getLanguageSpace();
   
   public abstract void setLanguageSpace(LanguageSpace languageSpace);
   
   
   public <M> M putMixin(Class<M> classType, String locale)
   {
      LanguageSpace languageSpace = this.getLanguageSpace();
      if (languageSpace == null)
      {
         languageSpace = this.createLanguageSpace();
      }
      
      return languageSpace.addNewLanguage(classType, locale);
   }
}
