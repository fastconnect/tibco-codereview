<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:lp="http://fastconnect.fr/tibco/bw/fccodereview/lightprocess"
	xmlns:pfx="http://www.fastconnect.fr/FCTibcoFactory/CodeReview/TestResult.xsd" version="1.0">

	<xsl:param name="max-depth" />

	<xsl:template match="/lp:project">
		<root>
			<xsl:for-each select="/lp:project/lp:process/descendant::lp:callProcess[@recursive = 'true']">
				<xsl:variable name="calledProcess" select="@calledProcessName" />
				<pfx:process-call>
					<pfx:process>
						<xsl:value-of select="$calledProcess" />
					</pfx:process>
					<xsl:for-each select="ancestor::lp:process[not(descendant::lp:process[@name = $calledProcess])]">
						<pfx:process>
							<xsl:value-of select="@name" />
						</pfx:process>
					</xsl:for-each>
				</pfx:process-call>
			</xsl:for-each>
		</root>
	</xsl:template>

	<xsl:template match="*" />

</xsl:stylesheet>