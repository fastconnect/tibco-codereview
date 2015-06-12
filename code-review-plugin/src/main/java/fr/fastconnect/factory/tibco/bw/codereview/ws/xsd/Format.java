/**
 * Format.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fr.fastconnect.factory.tibco.bw.codereview.ws.xsd;

import org.apache.axis.types.Token;

public class Format implements java.io.Serializable {
	private static final long serialVersionUID = 2720339359355433743L;

	private org.apache.axis.types.Token _value_;
    private static java.util.HashMap<Token, Format> _table_ = new java.util.HashMap<Token, Format>();

    // Constructor
    protected Format(org.apache.axis.types.Token value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final org.apache.axis.types.Token _HTML = new org.apache.axis.types.Token("HTML");
    public static final org.apache.axis.types.Token _XML = new org.apache.axis.types.Token("XML");
    public static final Format HTML = new Format(_HTML);
    public static final Format XML = new Format(_XML);
    public org.apache.axis.types.Token getValue() { return _value_;}
    public static Format fromValue(org.apache.axis.types.Token value)
          throws java.lang.IllegalArgumentException {
        Format enumeration = (Format)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static Format fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(Format.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.fastconnect.fr/FCTibcoFactory/CodeReview.xsd", ">format"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
