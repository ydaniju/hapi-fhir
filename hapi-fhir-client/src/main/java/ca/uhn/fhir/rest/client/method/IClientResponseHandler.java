/*
 * #%L
 * HAPI FHIR - Client Framework
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
package ca.uhn.fhir.rest.client.method;

import ca.uhn.fhir.rest.server.exceptions.BaseServerResponseException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface IClientResponseHandler<T> {

    T invokeClient(
            String theResponseMimeType,
            InputStream theResponseInputStream,
            int theResponseStatusCode,
            Map<String, List<String>> theHeaders)
            throws IOException, BaseServerResponseException;
}
