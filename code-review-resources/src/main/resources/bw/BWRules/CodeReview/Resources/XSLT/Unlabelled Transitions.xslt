<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:pd="http://xmlns.tibco.com/bw/process/2003">

	<xsl:param name="maxXPathLength" />
	<xsl:param name="minLabelLength" />
	
	<xsl:template match="/">
		<results>
			<xsl:for-each
				select="//pd:transition[pd:conditionType = 'xpath' and string-length(pd:xpath) &gt; $maxXPathLength and (not(exists(pd:xpathDescription)) or string-length(pd:xpathDescription) &lt; $minLabelLength)]">
				<result>
					<from><xsl:value-of select="pd:from" /></from>
					<to><xsl:value-of select="pd:to" /></to>
					<condition><xsl:value-of select="pd:xpath" /></condition>
				</result>
			</xsl:for-each>
		</results>
	</xsl:template>
</xsl:stylesheet>