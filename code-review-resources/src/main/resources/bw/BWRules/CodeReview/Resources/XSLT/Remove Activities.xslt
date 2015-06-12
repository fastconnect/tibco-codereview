<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:lp="http://fastconnect.fr/tibco/bw/fccodereview/lightprocess">
	<xsl:output method="xml" />

	<xsl:template match="/lp:project">
		<lp:project>
			<xsl:apply-templates select="child::node()" />
		</lp:project>
	</xsl:template>

	<xsl:template match="lp:callProcess">
		<lp:callProcess>
			<xsl:variable name="lookup" select="@calledProcessName" />
			<xsl:attribute name="name">
			<xsl:value-of select="@name" />
			</xsl:attribute>
			<xsl:attribute name="calledProcessName">
			<xsl:value-of select="@calledProcessName" />
			</xsl:attribute>
			<xsl:attribute name="depth">
				<xsl:value-of select="@depth" />
			</xsl:attribute>
			<xsl:attribute name="resolved">
				<xsl:value-of select="@resolved" />
			</xsl:attribute>
			<xsl:if test="@recursive">
				<xsl:attribute name="recursive">
					<xsl:value-of select="@recursive" />
				</xsl:attribute>
			</xsl:if>
			<xsl:apply-templates select="lp:*" />
		</lp:callProcess>
	</xsl:template>

	<xsl:template match="lp:process">
		<xsl:param name="depth" />
		<lp:process>
			<xsl:attribute name="name">
			<xsl:value-of select="@name" />
			</xsl:attribute>
			<xsl:apply-templates select="lp:*" />
		</lp:process>
	</xsl:template>

	<xsl:template match="lp:group">
		<lp:group>
			<xsl:attribute name="name">
			<xsl:value-of select="@name" />
			</xsl:attribute>
			<xsl:attribute name="type">
			<xsl:value-of select="@type" />
			</xsl:attribute>
			<xsl:if test="@more">
				<xsl:attribute name="more">
					<xsl:value-of select="@more" />
				</xsl:attribute>
			</xsl:if>
			<xsl:apply-templates select="lp:*" />
		</lp:group>
	</xsl:template>

	<xsl:template match="*" />
</xsl:stylesheet>