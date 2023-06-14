/*-
 * #%L
 * hapi-fhir-storage-batch2-jobs
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
package ca.uhn.fhir.batch2.jobs.export.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BulkExportBinaryFileId extends BulkExportJobBase {

    @JsonProperty("binaryId")
    private String myBinaryId;

    @JsonProperty("resourceType")
    private String myResourceType;

    public BulkExportBinaryFileId() {}

    public String getBinaryId() {
        return myBinaryId;
    }

    public void setBinaryId(String theBinaryId) {
        myBinaryId = theBinaryId;
    }

    public String getResourceType() {
        return myResourceType;
    }

    public void setResourceType(String theResourceType) {
        myResourceType = theResourceType;
    }
}
