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