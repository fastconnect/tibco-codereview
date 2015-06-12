/**
 * CodeReview.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fr.fastconnect.factory.tibco.bw.codereview.ws.xsd;

public class CodeReview  implements java.io.Serializable {
	private static final long serialVersionUID = 7882185807735830987L;

	private java.lang.String projectPath;

    private java.lang.String destination;

    private java.lang.String projectEncoding;

    private java.lang.String projectName;

    private fr.fastconnect.factory.tibco.bw.codereview.ws.xsd.Formats formats;

    private java.lang.String fileAliasesFile;

    private fr.fastconnect.factory.tibco.bw.codereview.ws.xsd.Language language;

    private fr.fastconnect.factory.tibco.bw.codereview.ws.xsd.DisabledRules disabledRules;

    public CodeReview() {
    }

    public CodeReview(
           java.lang.String projectPath,
           java.lang.String destination,
           java.lang.String projectEncoding,
           java.lang.String projectName,
           fr.fastconnect.factory.tibco.bw.codereview.ws.xsd.Formats formats,
           java.lang.String fileAliasesFile,
           fr.fastconnect.factory.tibco.bw.codereview.ws.xsd.Language language,
           fr.fastconnect.factory.tibco.bw.codereview.ws.xsd.DisabledRules disabledRules) {
           this.projectPath = projectPath;
           this.destination = destination;
           this.projectEncoding = projectEncoding;
           this.projectName = projectName;
           this.formats = formats;
           this.fileAliasesFile = fileAliasesFile;
           this.language = language;
           this.disabledRules = disabledRules;
    }


    /**
     * Gets the projectPath value for this CodeReview.
     * 
     * @return projectPath
     */
    public java.lang.String getProjectPath() {
        return projectPath;
    }


    /**
     * Sets the projectPath value for this CodeReview.
     * 
     * @param projectPath
     */
    public void setProjectPath(java.lang.String projectPath) {
        this.projectPath = projectPath;
    }


    /**
     * Gets the destination value for this CodeReview.
     * 
     * @return destination
     */
    public java.lang.String getDestination() {
        return destination;
    }


    /**
     * Sets the destination value for this CodeReview.
     * 
     * @param destination
     */
    public void setDestination(java.lang.String destination) {
        this.destination = destination;
    }


    /**
     * Gets the projectEncoding value for this CodeReview.
     * 
     * @return projectEncoding
     */
    public java.lang.String getProjectEncoding() {
        return projectEncoding;
    }


    /**
     * Sets the projectEncoding value for this CodeReview.
     * 
     * @param projectEncoding
     */
    public void setProjectEncoding(java.lang.String projectEncoding) {
        this.projectEncoding = projectEncoding;
    }

    /**
     * Gets the projectName value for this CodeReview.
     * 
     * @return projectName
     */
    public java.lang.String getProjectName() {
    	return projectName;
    }
    
    
    /**
     * Sets the projectName value for this CodeReview.
     * 
     * @param projectName
     */
    public void setProjectName(java.lang.String projectName) {
    	this.projectName = projectName;
    }
    

    /**
     * Gets the formats value for this CodeReview.
     * 
     * @return formats
     */
    public fr.fastconnect.factory.tibco.bw.codereview.ws.xsd.Formats getFormats() {
        return formats;
    }


    /**
     * Sets the formats value for this CodeReview.
     * 
     * @param formats
     */
    public void setFormats(fr.fastconnect.factory.tibco.bw.codereview.ws.xsd.Formats formats) {
        this.formats = formats;
    }


    /**
     * Gets the fileAliasesFile value for this CodeReview.
     * 
     * @return fileAliasesFile
     */
    public java.lang.String getFileAliasesFile() {
        return fileAliasesFile;
    }


    /**
     * Sets the fileAliasesFile value for this CodeReview.
     * 
     * @param fileAliasesFile
     */
    public void setFileAliasesFile(java.lang.String fileAliasesFile) {
        this.fileAliasesFile = fileAliasesFile;
    }


    /**
     * Gets the language value for this CodeReview.
     * 
     * @return language
     */
    public fr.fastconnect.factory.tibco.bw.codereview.ws.xsd.Language getLanguage() {
        return language;
    }


    /**
     * Sets the language value for this CodeReview.
     * 
     * @param language
     */
    public void setLanguage(fr.fastconnect.factory.tibco.bw.codereview.ws.xsd.Language language) {
        this.language = language;
    }


    /**
     * Gets the disabledRules value for this CodeReview.
     * 
     * @return disabledRules
     */
    public fr.fastconnect.factory.tibco.bw.codereview.ws.xsd.DisabledRules getDisabledRules() {
        return disabledRules;
    }


    /**
     * Sets the disabledRules value for this CodeReview.
     * 
     * @param disabledRules
     */
    public void setDisabledRules(fr.fastconnect.factory.tibco.bw.codereview.ws.xsd.DisabledRules disabledRules) {
        this.disabledRules = disabledRules;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CodeReview)) return false;
        CodeReview other = (CodeReview) obj;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.projectPath==null && other.getProjectPath()==null) || 
             (this.projectPath!=null &&
              this.projectPath.equals(other.getProjectPath()))) &&
            ((this.destination==null && other.getDestination()==null) || 
             (this.destination!=null &&
              this.destination.equals(other.getDestination()))) &&
            ((this.projectEncoding==null && other.getProjectEncoding()==null) || 
             (this.projectEncoding!=null &&
              this.projectEncoding.equals(other.getProjectEncoding()))) &&
            ((this.projectName==null && other.getProjectName()==null) || 
             (this.projectName!=null &&
              this.projectName.equals(other.getProjectName()))) &&
              ((this.formats==null && other.getFormats()==null) || 
             (this.formats!=null &&
              this.formats.equals(other.getFormats()))) &&
            ((this.fileAliasesFile==null && other.getFileAliasesFile()==null) || 
             (this.fileAliasesFile!=null &&
              this.fileAliasesFile.equals(other.getFileAliasesFile()))) &&
            ((this.language==null && other.getLanguage()==null) || 
             (this.language!=null &&
              this.language.equals(other.getLanguage()))) &&
            ((this.disabledRules==null && other.getDisabledRules()==null) || 
             (this.disabledRules!=null &&
              this.disabledRules.equals(other.getDisabledRules())));
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
        if (getProjectPath() != null) {
            _hashCode += getProjectPath().hashCode();
        }
        if (getDestination() != null) {
            _hashCode += getDestination().hashCode();
        }
        if (getProjectEncoding() != null) {
            _hashCode += getProjectEncoding().hashCode();
        }
        if (getProjectName() != null) {
        	_hashCode += getProjectName().hashCode();
        }
        if (getFormats() != null) {
            _hashCode += getFormats().hashCode();
        }
        if (getFileAliasesFile() != null) {
            _hashCode += getFileAliasesFile().hashCode();
        }
        if (getLanguage() != null) {
            _hashCode += getLanguage().hashCode();
        }
        if (getDisabledRules() != null) {
            _hashCode += getDisabledRules().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CodeReview.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.fastconnect.fr/FCTibcoFactory/CodeReview.xsd", ">code-review"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("projectPath");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.fastconnect.fr/FCTibcoFactory/CodeReview.xsd", "project-path"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destination");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.fastconnect.fr/FCTibcoFactory/CodeReview.xsd", "destination"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("projectEncoding");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.fastconnect.fr/FCTibcoFactory/CodeReview.xsd", "project-encoding"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("projectName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.fastconnect.fr/FCTibcoFactory/CodeReview.xsd", "project-name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("formats");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.fastconnect.fr/FCTibcoFactory/CodeReview.xsd", "formats"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.fastconnect.fr/FCTibcoFactory/CodeReview.xsd", ">formats"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fileAliasesFile");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.fastconnect.fr/FCTibcoFactory/CodeReview.xsd", "fileAliasesFile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("language");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.fastconnect.fr/FCTibcoFactory/CodeReview.xsd", "language"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.fastconnect.fr/FCTibcoFactory/CodeReview.xsd", ">language"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("disabledRules");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.fastconnect.fr/FCTibcoFactory/CodeReview.xsd", "disabled-rules"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.fastconnect.fr/FCTibcoFactory/CodeReview.xsd", ">disabled-rules"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
