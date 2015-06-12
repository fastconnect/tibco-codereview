/**
 * DisabledRules.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fr.fastconnect.factory.tibco.bw.codereview.ws.xsd;

public class DisabledRules implements java.io.Serializable {
	private static final long serialVersionUID = 5704198565149058849L;

	private org.apache.axis.types.Token[] disabledRule;

    public DisabledRules() {
    }

    public DisabledRules(
           org.apache.axis.types.Token[] disabledRule) {
           this.disabledRule = disabledRule;
    }


    /**
     * Gets the disabledRule value for this DisabledRules.
     * 
     * @return disabledRule
     */
    public org.apache.axis.types.Token[] getDisabledRule() {
        return disabledRule;
    }


    /**
     * Sets the disabledRule value for this DisabledRules.
     * 
     * @param disabledRule
     */
    public void setDisabledRule(org.apache.axis.types.Token[] disabledRule) {
        this.disabledRule = disabledRule;
    }

    public org.apache.axis.types.Token getDisabledRule(int i) {
        return this.disabledRule[i];
    }

    public void setDisabledRule(int i, org.apache.axis.types.Token _value) {
        this.disabledRule[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DisabledRules)) return false;
        DisabledRules other = (DisabledRules) obj;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.disabledRule==null && other.getDisabledRule()==null) || 
             (this.disabledRule!=null &&
              java.util.Arrays.equals(this.disabledRule, other.getDisabledRule())));
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
        if (getDisabledRule() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDisabledRule());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDisabledRule(), i);
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
        new org.apache.axis.description.TypeDesc(DisabledRules.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.fastconnect.fr/FCTibcoFactory/CodeReview.xsd", ">disabled-rules"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("disabledRule");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.fastconnect.fr/FCTibcoFactory/CodeReview.xsd", "disabled-rule"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.fastconnect.fr/FCTibcoFactory/CodeReview.xsd", "disabled-rule"));
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
