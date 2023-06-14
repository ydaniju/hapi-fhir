/*-
 * #%L
 * HAPI FHIR JPA Server - Firely Query Language
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
package ca.uhn.fhir.jpa.fql.executor;

import ca.uhn.fhir.jpa.fql.parser.FqlStatement;

import java.util.List;

public interface IFqlResult {

	List<String> getColumnNames();

	boolean hasNext();

	Row getNextRow();

	boolean isClosed();

	void close();

	String getSearchId();

	int getLimit();

	FqlStatement getStatement();

	class Row {

		private final List<String> myValues;
		private final int mySearchRowNumber;

		public Row(int theSearchRowNumber, List<String> theValues) {
			mySearchRowNumber = theSearchRowNumber;
			myValues = theValues;
		}

		public int searchRowNumber() {
			return mySearchRowNumber;
		}

		public List<String> values() {
			return myValues;
		}


	}


}
