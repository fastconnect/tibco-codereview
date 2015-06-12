/**
 * Formats.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fr.fastconnect.factory.tibco.bw.codereview.ws.xsd;

public class Formats  implements java.io.Serializable {
	private static final long serialVersionUID = -5688431746155197789L;

	private fr.fastconnect.factory.tibco.bw.codereview.ws.xsd.Format[] format;

    public Formats() {
    }

    public Formats(
           fr.fastconnect.factory.tibco.bw.codereview.ws.xsd.Format[] format) {
           this.format = format;
    }


    /**
     * Gets the format value for this Formats.
     * 
     * @return format
     */
    public fr.fastconnect.factory.tibco.bw.codereview.ws.xsd.Format[] getFormat() {
        return format;
    }


    /**
     * Sets the format value for this Formats.
     * 
     * @param format
     */
    public void setFormat(fr.fastconnect.factory.tibco.bw.codereview.ws.xsd.Format[] format) {
        this.format = format;
    }

    public fr.fastconnect.factory.tibco.bw.codereview.ws.xsd.Format getFormat(int i) {
        return this.format[i];
    }

    public void setFormat(int i, fr.fastconnect.factory.tibco.bw.codereview.ws.xsd.Format _value) {
        this.format[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Formats)) return false;
        Formats other = (Formats) obj;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.format==null && other.getFormat()==null) || 
             (this.format!=null &&
              java.util.Arrays.equals(this.format, other.getFormat())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getFormat() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFormat());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFormat(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Formats.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.fastconnect.fr/FCTibcoFactory/CodeReview.xsd", ">formats"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("format");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.fastconnect.fr/FCTibcoFactory/CodeReview.xsd", "format"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.fastconnect.fr/FCTibcoFactory/CodeReview.xsd", "format"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           @SuppressWarnings("rawtypes") java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           @SuppressWarnings("rawtypes") java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
