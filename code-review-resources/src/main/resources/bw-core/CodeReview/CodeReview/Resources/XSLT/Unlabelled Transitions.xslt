<?xml version="1.0"?>
<!--

    (C) Copyright 2011-2015 FastConnect SAS
    (http://www.fastconnect.fr/) and others.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

-->
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