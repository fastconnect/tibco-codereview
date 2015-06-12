/**
 * Language.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fr.fastconnect.factory.tibco.bw.codereview.ws.xsd;

import org.apache.axis.types.Token;

public class Language implements java.io.Serializable {
	private static final long serialVersionUID = 3590409178228441645L;

	private org.apache.axis.types.Token _value_;
    private static java.util.HashMap<Token, Language> _table_ = new java.util.HashMap<Token, Language>();

    // Constructor
    protected Language(org.apache.axis.types.Token value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final org.apache.axis.types.Token _FR = new org.apache.axis.types.Token("FR");
    public static final org.apache.axis.types.Token _EN = new org.apache.axis.types.Token("EN");
    public static final Language FR = new Language(_FR);
    public static final Language EN = new Language(_EN);
    public org.apache.axis.types.Token getValue() { return _value_;}
    public static Language fromValue(org.apache.axis.types.Token value)
          throws java.lang.IllegalArgumentException {
        Language enumeration = (Language)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static Language fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        try {
            return fromValue(new org.apache.axis.types.Token(value));
        } catch (Exception e) {
            throw new java.lang.IllegalArgumentException();
        }
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_.toString();}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           @SuppressWarnings("rawtypes") java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           @SuppressWarnings("rawtypes") java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Language.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.fastconnect.fr/FCTibcoFactory/CodeReview.xsd", ">language"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
