/*******************************************************************************
 * Copyright 2015 Unicon (R) Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *******************************************************************************/
/**
 * 
 */
package od.providers.course.sakai;

import java.util.List;

import od.framework.model.OpenDashboardModel;

import org.apereo.lai.Course;
import org.apereo.lai.impl.CourseImpl;

/**
 * @author ggilbert
 *
 */
public class SakaiSiteCollection extends OpenDashboardModel {
  
  private static final long serialVersionUID = 1L;

  public SakaiSiteCollection () {}
  
  private List<Course> site_collection;

  public List<Course> getSite_collection() {
    return site_collection;
  }

  public void setSite_collection(List<Course> site_collection) {
    this.site_collection = site_collection;
  }
}
