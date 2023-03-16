package ca.uhn.fhir.jpa.model.entity;

/*-
 * #%L
 * HAPI FHIR JPA Model
 * %%
 * Copyright (C) 2014 - 2023 Smile CDR, Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ResourceModifiedEntityPK implements IResourceModifiedPK, Serializable {
	@Column(name = "RES_ID", nullable = false)
	private String myResourcePid;

	@Column(name = "RES_VER", nullable = false)
	private String myResourceVersion;

	public String getResourcePid() {
		return myResourcePid;
	}

	public ResourceModifiedEntityPK setResourcePid(String theResourcePid) {
		myResourcePid = theResourcePid;
		return this;
	}

	public String getResourceVersion() {
		return myResourceVersion;
	}

	public ResourceModifiedEntityPK setResourceVersion(String theResourceVersion) {
		myResourceVersion = theResourceVersion;
		return this;
	}

	public static ResourceModifiedEntityPK with(String theResourcePid, String theResourceVersion){
		return new ResourceModifiedEntityPK().setResourcePid(theResourcePid).setResourceVersion(theResourceVersion);
	}
}
