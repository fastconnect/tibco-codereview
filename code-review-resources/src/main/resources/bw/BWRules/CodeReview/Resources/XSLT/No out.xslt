<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:pd="http://xmlns.tibco.com/bw/process/2003">
	<xsl:output method="xml" />

	<xsl:template match="/">
		<results>
			<xsl:apply-templates select="descendant::pd:activity" />
		</results>
	</xsl:template>

	<xsl:template match="pd:activity">
		<xsl:variable name="activityName" select="@name" />
		<xsl:if test="pd:type != 'com.tibco.pe.core.GenerateErrorActivity' and pd:type != 'com.tibco.pe.core.RethrowActivity' and count(../pd:transition[pd:from = $activityName]) = 0">
			<result>
				<xsl:value-of select="@name" />
			</result>
		</xsl:if>

	</xsl:template>
</xsl:stylesheet>