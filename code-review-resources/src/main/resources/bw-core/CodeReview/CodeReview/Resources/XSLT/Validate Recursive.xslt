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
			<xsl:for-each select="child::lp:process">
				<xsl:variable name="currentName" select="@name" />
				<!-- On ne recompie pas le process si il est appele quelquepart -->
				<xsl:if test="count(preceding::lp:process[@name=$currentName]) = 0 and count(following::lp:process[@name=$currentName]) = 0">
					<xsl:apply-templates select="." />
				</xsl:if>
			</xsl:for-each>
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
			<xsl:choose>
				<xsl:when test="count(ancestor::lp:process[@name = $lookup]) &gt; 0">
					<xsl:attribute name="recursive">true</xsl:attribute>
				</xsl:when>
				<xsl:otherwise>
					<xsl:apply-templates select="child::node()" />
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

	<xsl:template match="lp:activity">
		<xsl:copy-of select="." />
	</xsl:template>

	<xsl:template match="*" />
</xsl:stylesheet>