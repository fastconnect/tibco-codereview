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
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:pd="http://xmlns.tibco.com/bw/process/2003"
	xmlns:lp="http://fastconnect.fr/tibco/bw/fccodereview/lightprocess" exclude-result-prefixes="pd">
	<xsl:output method="xml" />
	<xsl:param name="processName" />
	<xsl:template match="/pd:ProcessDefinition">
		<lp:process>
			<xsl:attribute name="name">
			<xsl:value-of select="$processName" />
			</xsl:attribute>
			<xsl:apply-templates select="pd:group" />
			<xsl:apply-templates select="pd:activity[pd:type != 'com.tibco.pe.core.CallProcessActivity']" />
			<xsl:apply-templates select="pd:activity[pd:type = 'com.tibco.pe.core.CallProcessActivity']" />
		</lp:process>
	</xsl:template>

	<xsl:template match="pd:group">
		<lp:group>
			<xsl:attribute name="name">
			<xsl:value-of select="@name" />
			</xsl:attribute>
			<xsl:attribute name="type">
			<xsl:value-of select="config/pd:groupType" />
			</xsl:attribute>
			<xsl:if test="config/pd:scope" >
				<xsl:attribute name="more">
					<xsl:value-of select="config/pd:scope" />
				</xsl:attribute>
			</xsl:if>
			<xsl:apply-templates select="pd:group" />
			<xsl:apply-templates select="pd:activity" />
		</lp:group>
	</xsl:template>

	<xsl:template
		match="pd:activity[pd:type = 'com.tibco.pe.core.GetSharedVariableActivity' or pd:type = 'com.tibco.pe.core.SetSharedVariableActivity']"
		priority="10">
		<lp:activity>
			<xsl:attribute name="name">
				<xsl:value-of select="@name" />
			</xsl:attribute>
			<xsl:attribute name="type">
				<xsl:value-of select="pd:type" />
			</xsl:attribute>
			<lp:info>
				<xsl:attribute name="name">
					<xsl:value-of select="'SharedVariable'" />
				</xsl:attribute>
				<xsl:value-of select="config/variableConfig" />
			</lp:info>
		</lp:activity>
	</xsl:template>

	<xsl:template match="pd:activity[pd:type = 'com.tibco.pe.core.CallProcessActivity']" priority="10">
		<lp:callProcess>
			<xsl:attribute name="name">
			<xsl:value-of select="@name" />
			</xsl:attribute>
			<xsl:attribute name="calledProcessName">
			<xsl:value-of select="config/processName" />
			</xsl:attribute>
			<xsl:attribute name="resolved"><xsl:value-of select="'false'" /></xsl:attribute>
		</lp:callProcess>
	</xsl:template>

	<xsl:template match="pd:activity" priority="0">
		<lp:activity>
			<xsl:attribute name="name">
			<xsl:value-of select="@name" />
			</xsl:attribute>
			<xsl:attribute name="type">
				<xsl:value-of select="pd:type" />
			</xsl:attribute>
		</lp:activity>
	</xsl:template>

	<xsl:template match="*" />
</xsl:stylesheet>