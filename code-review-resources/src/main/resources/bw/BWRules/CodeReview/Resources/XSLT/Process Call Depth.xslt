<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:lp="http://fastconnect.fr/tibco/bw/fccodereview/lightprocess"
	xmlns:pfx="http://www.fastconnect.fr/FCTibcoFactory/CodeReview/ReviewResult.xsd" version="1.0">

	<xsl:param name="max-depth" />

	<xsl:template match="/lp:project">
		<root>
			<xsl:for-each select="descendant::lp:callProcess[@depth &gt;= $max-depth and not(descendant::lp:callProcess)]">
				<pfx:process-call>
					<xsl:for-each select="ancestor::lp:process[1]">
						<xsl:call-template name="buildStack" />
					</xsl:for-each>
					<pfx:process>
						<xsl:value-of select="@calledProcessName" />
					</pfx:process>	
					<xsl:if test="lp:process">
						<pfx:process>
							<xsl:value-of select="@name" />
						</pfx:process>
					</xsl:if>
				</pfx:process-call>
			</xsl:for-each>
		</root>
	</xsl:template>
	 
	<xsl:template name="buildStack">
		<xsl:for-each select="ancestor::lp:process[1]">
			<xsl:call-template name="buildStack" />
		</xsl:for-each>
		<pfx:process>
			<xsl:value-of select="@name" />
		</pfx:process>
	</xsl:template>

	<xsl:template match="*" />

</xsl:stylesheet>