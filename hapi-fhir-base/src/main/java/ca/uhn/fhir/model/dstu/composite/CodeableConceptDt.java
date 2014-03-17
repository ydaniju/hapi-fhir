















package ca.uhn.fhir.model.dstu.composite;

import java.util.*;
import ca.uhn.fhir.model.api.*;
import ca.uhn.fhir.model.api.annotation.*;

import ca.uhn.fhir.model.dstu.valueset.AddressUseEnum;
import ca.uhn.fhir.model.dstu.composite.CodingDt;
import ca.uhn.fhir.model.dstu.valueset.ContactSystemEnum;
import ca.uhn.fhir.model.dstu.valueset.ContactUseEnum;
import ca.uhn.fhir.model.dstu.valueset.EventTimingEnum;
import ca.uhn.fhir.model.dstu.valueset.IdentifierUseEnum;
import ca.uhn.fhir.model.dstu.valueset.NameUseEnum;
import ca.uhn.fhir.model.dstu.resource.Organization;
import ca.uhn.fhir.model.dstu.composite.PeriodDt;
import ca.uhn.fhir.model.dstu.valueset.QuantityCompararatorEnum;
import ca.uhn.fhir.model.dstu.composite.QuantityDt;
import ca.uhn.fhir.model.dstu.composite.ResourceReferenceDt;
import ca.uhn.fhir.model.dstu.valueset.UnitsOfTimeEnum;
import ca.uhn.fhir.model.dstu.resource.ValueSet;
import ca.uhn.fhir.model.primitive.Base64BinaryDt;
import ca.uhn.fhir.model.primitive.BooleanDt;
import ca.uhn.fhir.model.primitive.BoundCodeDt;
import ca.uhn.fhir.model.primitive.CodeDt;
import ca.uhn.fhir.model.primitive.DateTimeDt;
import ca.uhn.fhir.model.primitive.DecimalDt;
import ca.uhn.fhir.model.primitive.IntegerDt;
import ca.uhn.fhir.model.primitive.StringDt;
import ca.uhn.fhir.model.primitive.UriDt;

/**
 * HAPI/FHIR <b>CodeableConcept</b> Datatype
 * (Concept - reference to a terminology or just  text)
 *
 * <p>
 * <b>Definition:</b>
 * A concept that may be defined by a formal reference to a terminology or ontology or may be provided by text
 * </p> 
 *
 * <p>
 * <b>Requirements:</b>
 * This is a common pattern in healthcare - a concept that may be defined by one or more codes from formal definitions including LOINC and SNOMED CT, and/or defined by the provision of text that captures a human sense of the concept
 * </p> 
 */
@DatatypeDef(name="CodeableConcept") 
public class CodeableConceptDt 
        extends  BaseElement         implements ICompositeDatatype  {


	@Child(name="coding", type=CodingDt.class, order=0, min=0, max=Child.MAX_UNLIMITED)	
	@Description(
		shortDefinition="Code defined by a terminology system",
		formalDefinition="A reference to a code defined by a terminology system"
	)
	private java.util.List<CodingDt> myCoding;
	
	@Child(name="text", type=StringDt.class, order=1, min=0, max=1)	
	@Description(
		shortDefinition="Plain text representation of the concept",
		formalDefinition="A human language representation of the concept as seen/selected/uttered by the user who entered the data and/or which represents the intended meaning of the user"
	)
	private StringDt myText;
	

	@Override
	public boolean isEmpty() {
		return super.isBaseEmpty() && ca.uhn.fhir.util.ElementUtil.isEmpty(  myCoding,  myText);
	}
	
	@Override
	public java.util.List<IElement> getAllPopulatedChildElements() {
		return getAllPopulatedChildElementsOfType(null);
	}

	@Override
	public <T extends IElement> List<T> getAllPopulatedChildElementsOfType(Class<T> theType) {
		return ca.uhn.fhir.util.ElementUtil.allPopulatedChildElements(theType, myCoding, myText);
	}

	/**
	 * Gets the value(s) for <b>coding</b> (Code defined by a terminology system).
	 * creating it if it does
	 * not exist. Will not return <code>null</code>.
	 *
     * <p>
     * <b>Definition:</b>
     * A reference to a code defined by a terminology system
     * </p> 
	 */
	public java.util.List<CodingDt> getCoding() {  
		if (myCoding == null) {
			myCoding = new java.util.ArrayList<CodingDt>();
		}
		return myCoding;
	}

	/**
	 * Sets the value(s) for <b>coding</b> (Code defined by a terminology system)
	 *
     * <p>
     * <b>Definition:</b>
     * A reference to a code defined by a terminology system
     * </p> 
	 */
	public void setCoding(java.util.List<CodingDt> theValue) {
		myCoding = theValue;
	}

	/**
	 * Adds and returns a new value for <b>coding</b> (Code defined by a terminology system)
	 *
     * <p>
     * <b>Definition:</b>
     * A reference to a code defined by a terminology system
     * </p> 
	 */
	public CodingDt addCoding() {
		CodingDt newType = new CodingDt();
		getCoding().add(newType);
		return newType; 
	}

  
	/**
	 * Gets the value(s) for <b>text</b> (Plain text representation of the concept).
	 * creating it if it does
	 * not exist. Will not return <code>null</code>.
	 *
     * <p>
     * <b>Definition:</b>
     * A human language representation of the concept as seen/selected/uttered by the user who entered the data and/or which represents the intended meaning of the user
     * </p> 
	 */
	public StringDt getText() {  
		if (myText == null) {
			myText = new StringDt();
		}
		return myText;
	}

	/**
	 * Sets the value(s) for <b>text</b> (Plain text representation of the concept)
	 *
     * <p>
     * <b>Definition:</b>
     * A human language representation of the concept as seen/selected/uttered by the user who entered the data and/or which represents the intended meaning of the user
     * </p> 
	 */
	public void setText(StringDt theValue) {
		myText = theValue;
	}


 	/**
	 * Sets the value for <b>text</b> (Plain text representation of the concept)
	 *
     * <p>
     * <b>Definition:</b>
     * A human language representation of the concept as seen/selected/uttered by the user who entered the data and/or which represents the intended meaning of the user
     * </p> 
	 */
	public void setText( String theString) {
		myText = new StringDt(theString); 
	}

 



}