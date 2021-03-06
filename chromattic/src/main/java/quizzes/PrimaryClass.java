/*
 * Copyright (C) 2003-2009 eXo Platform SAS.
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

package quizzes;

import org.chromattic.api.RelationshipType;
import org.chromattic.api.annotations.Owner;
import org.chromattic.api.annotations.PrimaryType;
import org.chromattic.api.annotations.OneToOne;
import org.chromattic.api.annotations.MappedBy;
import org.chromattic.api.annotations.Property;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
@PrimaryType(name = "qz:b")
public abstract class PrimaryClass {

//  @OneToOne
//  @MappedBy("b")
//  public abstract MixinClass getParent();
//
//  public abstract void setParent(MixinClass b);

  @OneToOne(type = RelationshipType.EMBEDDED)
  @Owner
  public abstract MixinClass getMixin();

  public abstract void setMixin(MixinClass b);

  @OneToOne(type = RelationshipType.EMBEDDED)
  @Owner
  public abstract SuperClass getSuper();

  public abstract void setSuper(SuperClass a);

  @Property(name = "name")
  protected abstract String getName();
  
  protected abstract void setName(String name);
}
