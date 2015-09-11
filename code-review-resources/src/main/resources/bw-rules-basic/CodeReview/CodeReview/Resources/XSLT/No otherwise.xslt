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
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:pd="http://xmlns.tibco.com/bw/process/2003">
	<xsl:output method="xml" />

	<xsl:template match="/">
		<results>
			<xsl:call-template name="start" use-when="count(//pd:starter) = 0" /> <!-- handling the Start activity (when there is no other starter)-->
			<xsl:apply-templates select="descendant::pd:activity" />
			<xsl:apply-templates select="descendant::pd:starter" />
		</results>
	</xsl:template>

	<xsl:template match="pd:activity | pd:starter">
		<xsl:variable name="activityName" select="@name" />
		<xsl:if test="count(../pd:transition[pd:from = $activityName and pd:conditionType='xpath']) &gt; 0 and count(../pd:transition[pd:from = $activityName and pd:conditionType='otherwise']) = 0">
			<result>
				<xsl:value-of select="@name" />
			</result>
		</xsl:if>
	</xsl:template>

	<xsl:template name="start">
		<xsl:variable name="activityName" select="'Start'" />
		<xsl:if test="count(/pd:ProcessDefinition/pd:transition[pd:from = $activityName and pd:conditionType='xpath']) &gt; 0 and count(/pd:ProcessDefinition/pd:transition[pd:from = $activityName and pd:conditionType='otherwise']) = 0">
			<result>
				<xsl:value-of select="'Start'" />
			</result>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>