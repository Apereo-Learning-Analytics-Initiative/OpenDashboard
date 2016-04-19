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
package od.providers.course;

import java.util.List;

import od.providers.Provider;
import od.providers.ProviderException;
import od.providers.ProviderOptions;

import org.apereo.lai.Course;

/**
 * @author ggilbert
 *
 */
public interface CourseProvider extends Provider {
  Course getContext(ProviderOptions options) throws ProviderException;
  List<Course> getContexts(ProviderOptions options) throws ProviderException;
  List<Course> getContextsForUser(ProviderOptions options) throws ProviderException;
  String getLTIContextIdByCourseId(String courseId) throws ProviderException;
  String getCourseIdByLTIContextId(String ltiContextId) throws ProviderException;
}
