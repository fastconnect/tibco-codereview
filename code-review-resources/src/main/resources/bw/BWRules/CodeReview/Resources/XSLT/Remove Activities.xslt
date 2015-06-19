<?xml version="1.0" encoding="UTF-8"?>
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