<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:lp="http://fastconnect.fr/tibco/bw/fccodereview/lightprocess">
	<xsl:output method="xml" />

	<xsl:param name="max-depth" />

	<xsl:template match="/lp:project">
		<lp:project>
			<xsl:apply-templates select="child::node()">
				<xsl:with-param name="depth" select="1" />
			</xsl:apply-templates>
		</lp:project>
	</xsl:template>

	<xsl:template match="lp:callProcess[@resolved='false']">
		<xsl:param name="depth" />
		<xsl:variable name="lookup" select="@calledProcessName" />
		<lp:callProcess>
			<xsl:attribute name="name">
				<xsl:value-of select="@name" />
			</xsl:attribute>
			<xsl:attribute name="calledProcessName">
				<xsl:value-of select="$lookup" />
			</xsl:attribute>
			<xsl:attribute name="resolved"><xsl:value-of select="'true'" /></xsl:attribute>
			<xsl:attribute name="depth">
				<xsl:value-of select="$depth + 1" />
			</xsl:attribute>
			<!-- Here is the magic recursive call -->
			<xsl:choose>
				<xsl:when test="$depth &gt; $max-depth">
					<xsl:attribute name="recursive"><xsl:value-of select="'true'" /></xsl:attribute>
				</xsl:when>
				<xsl:otherwise>
					<xsl:apply-templates select="//lp:process[@name = $lookup]">
						<xsl:with-param name="depth" select="$depth + 1" />
					</xsl:apply-templates>
				</xsl:otherwise>
			</xsl:choose>
		</lp:callProcess>
	</xsl:template>

	<xsl:template match="lp:callProcess[@resolved='true']">
		<xsl:param name="depth" />
		<lp:callProcess>
			<xsl:variable name="lookup" select="@calledProcessName" />
			<xsl:attribute name="name">
			<xsl:value-of select="@name" />
			</xsl:attribute>
			<xsl:attribute name="calledProcessName">
			<xsl:value-of select="@calledProcessName" />
			</xsl:attribute>
			<xsl:attribute name="resolved"><xsl:value-of select="'true'" /></xsl:attribute>
			<xsl:choose>
				<xsl:when test="$depth &gt; $max-depth">
					<xsl:attribute name="recursive"><xsl:value-of select="'true'" /></xsl:attribute>
				</xsl:when>
				<xsl:otherwise>
					<xsl:apply-templates select="child::node()">
						<xsl:with-param name="depth" select="$depth + 1" />
					</xsl:apply-templates>
				</xsl:otherwise>
			</xsl:choose>
		</lp:callProcess>
	</xsl:template>

	<xsl:template match="lp:process">
		<xsl:param name="depth" />
		<lp:process>
			<xsl:attribute name="name">
			<xsl:value-of select="@name" />
			</xsl:attribute>
			<xsl:apply-templates select="lp:*">
				<xsl:with-param name="depth" select="$depth" />
			</xsl:apply-templates>
		</lp:process>
	</xsl:template>

	<xsl:template match="lp:group">
		<xsl:param name="depth" />
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
			<xsl:apply-templates select="lp:*">
				<xsl:with-param name="depth" select="$depth" />
			</xsl:apply-templates>
		</lp:group>
	</xsl:template>

	<xsl:template match="lp:activity">
		<xsl:copy-of select="." />
	</xsl:template>

	<xsl:template match="*" />
</xsl:stylesheet>