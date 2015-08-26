<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
	xmlns:ns0="http://www.fastconnect.fr/FCTibcoFactory/CodeReview/ReviewResult.xsd" xmlns:l="http://fastconnect.fr/Lang.xsd" xmlns:xhtml="http://www.w3.org/1999/xhtml">

	<xsl:output method="html" encoding="UTF-8" media-type="text/html" omit-xml-declaration="yes" doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN"
		doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" indent="yes" />


	<xsl:param name="lang" />
	<xsl:param name="svg-filename" />

	<!-- MAIN TEMPLATE -->
	<xsl:template match="ns0:review-result">

		<xhtml:html xmlns:ns0="http://www.fastconnect.fr/FCTibcoFactory/CodeReview/TestResult.xsd" xmlns="http://www.w3.org/1999/xhtml">
			<head>
				<title>FC Code Review</title>
				<link rel="stylesheet" title="CodeReviewCSS" type="text/css" media="screen" href="resources/code-review.css">
				</link>
				<script language="Javascript" type="text/javascript" src="resources/code-review.js">
					<xsl:comment>
					</xsl:comment>
				</script>
			</head>
			<body onload="menue('GEN')">
				<div id="Head">
					<div class="IMGLogoFastConnect1">
						<span class="FCTitle">
							<xsl:value-of select="$lang/l:lang/l:word[@key = 'title']" />
							<br />
							<xsl:value-of select="$lang/l:lang/l:word[@key = 'subtitle']" />
						</span>
						<span class="ProjectData">
							<br />
							<xsl:value-of select="@project-name" />
							<br />
							<xsl:value-of select="substring-before(translate(@timestamp, 'T', ' '), '.')" />
						</span>
						<span class="ProjectData">
							<xsl:element name="embed">
								<xsl:attribute name="src"><xsl:value-of select="$svg-filename" /></xsl:attribute>
								<xsl:attribute name="width">200</xsl:attribute>
								<xsl:attribute name="height">200</xsl:attribute>
							</xsl:element>
						</span>
					</div>
					<xsl:call-template name="displayMenu" />
				</div>
				<div id="Container">
					<!-- . . . . . . . . -->
					<!-- GENERAL -->
					<!-- . . . . . . . . -->
					<div id="GEN" class="NotVisible">
						<xsl:call-template name="render-GEN-001" />
						<xsl:call-template name="render-GEN-002" />
						<xsl:call-template name="render-GEN-003" />
						<xsl:call-template name="render-GEN-004" />
					</div>

					<!-- . . . . . . . . -->
					<!-- RELIABILITY -->
					<!-- . . . . . . . . -->
					<div id="REL" class="NotVisible">
						<xsl:call-template name="render-REL-001" />
						<xsl:call-template name="render-REL-002" />
						<xsl:call-template name="render-REL-003" />
						<xsl:call-template name="render-REL-004" />
						<xsl:call-template name="render-REL-005" />
						<xsl:call-template name="render-REL-006" />
					</div>

					<!-- . . . . . . . . -->
					<!-- EFFICIENCY -->
					<!-- . . . . . . . . -->
					<div id="EFF" class="NotVisible">
						<xsl:call-template name="render-EFF-001" />
						<xsl:call-template name="render-EFF-002" />
						<xsl:call-template name="render-EFF-003" />
						<xsl:call-template name="render-EFF-004" />
						<xsl:call-template name="render-EFF-005" />
						<xsl:call-template name="render-EFF-006" />
					</div>

					<!-- . . . . . . . . -->
					<!-- MAINTAINABILITY -->
					<!-- . . . . . . . . -->
					<div id="MAI" class="NotVisible">
						<xsl:call-template name="render-MAI-001" />
						<xsl:call-template name="render-MAI-002" />
						<xsl:call-template name="render-MAI-003" />
						<xsl:call-template name="render-MAI-004" />
						<xsl:call-template name="render-MAI-005" />
						<xsl:call-template name="render-MAI-006" />
						<xsl:call-template name="render-MAI-007" />
						<xsl:call-template name="render-MAI-008" />
						<xsl:call-template name="render-MAI-009" />
						<xsl:call-template name="render-MAI-010" />
						<xsl:call-template name="render-MAI-011" />
					</div>

					<!-- . . . . . . -->
					<!-- PORTABILITY -->
					<!-- . . . . . . -->
					<div id="POR" class="NotVisible">
						<xsl:call-template name="render-POR-001" />
						<xsl:call-template name="render-POR-002" />
					</div>

					<!-- . . . . . . -->
					<!-- . ERRORS . -->
					<!-- . . . . . . -->
					<div id="Errors" class="NotVisible">
						<div class="CodeReviewSection">
							<xsl:call-template name="sectionHeader">
								<xsl:with-param name="title">
									<xsl:value-of select="concat($lang/l:lang/l:word[@key = 'ERR'], ' (', count(ns0:errors/ns0:error), ')')" />
								</xsl:with-param>
								<xsl:with-param name="ruleId" select="'ERR'" />
								<xsl:with-param name="disabled" select="false()" />
							</xsl:call-template>
							<span id="ERR" class="">
								<div class="CodeReviewData">
									<table>
										<tr>
											<th class="SmallInputText">
												<xsl:value-of select="$lang/l:lang/l:word[@key = 'Context']" />
											</th>
											<th class="SmallInputText">
												<xsl:value-of select="$lang/l:lang/l:word[@key = 'Rule']" />
											</th>
											<th class="LargeInputText">
												<xsl:value-of select="$lang/l:lang/l:word[@key = 'Error']" />
											</th>
										</tr>
										<xsl:call-template name="renderErrors" />
									</table>
								</div>
							</span>
						</div>
					</div>

					<!-- . . . . . . -->
					<!-- . INFOS . . -->
					<!-- . . . . . . -->
					<div id="Infos" class="NotVisible">
							<xsl:element name="embed">
								<xsl:attribute name="src"><xsl:value-of select="$svg-filename" /></xsl:attribute>
								<xsl:attribute name="width">400</xsl:attribute>
								<xsl:attribute name="height">400</xsl:attribute>
							</xsl:element>
						<div class="CodeReviewSection">
							<xsl:call-template name="sectionHeader">
								<xsl:with-param name="title">
									<xsl:value-of select="$lang/l:lang/l:word[@key = 'Configuration']" />
								</xsl:with-param>
								<xsl:with-param name="ruleId" select="'CONF'" />
								<xsl:with-param name="disabled" select="false()" />
							</xsl:call-template>
							<span id="CONF" class="">
								<div class="CodeReviewData">
									<table>
										<tr>
											<th class="SmallInputText">
												<xsl:value-of select="$lang/l:lang/l:word[@key = 'ConfigurationEntry']" />
											</th>
											<th class="SmallInputText">
												<xsl:value-of select="$lang/l:lang/l:word[@key = 'Value']" />
											</th>
										</tr>
										<xsl:call-template name="renderConf" />
									</table>
								</div>
							</span>
						</div>

						<div class="CodeReviewSection">
							<xsl:call-template name="sectionHeader">
								<xsl:with-param name="title">
									<xsl:value-of select="$lang/l:lang/l:word[@key = 'Files']" />
								</xsl:with-param>
								<xsl:with-param name="ruleId" select="'FILES'" />
								<xsl:with-param name="disabled" select="false()" />
							</xsl:call-template>
							<span id="FILES" class="NotVisible">
								<div class="CodeReviewData">
									<table>
										<tr>
											<th class="SmallInputText">
												<xsl:value-of select="$lang/l:lang/l:word[@key = 'File']" />
											</th>
										</tr>
										<xsl:call-template name="renderFiles" />
									</table>
								</div>
							</span>
						</div>
					</div>

				</div>

				<div id="Footer">
					FC Code Review for TIBCO BusinessWorks - &#169; 2013 FastConnect SAS -
					<a href="http://www.fastconnect.fr/" target="_blank">
						http://www.fastconnect.fr/
					</a>
					<br />
					Project :
					<xsl:value-of select="@project-name" />
					- Review Date :
					<xsl:value-of select="substring-before(translate(@timestamp, 'T', ' '), '.')" />
					- Review Duration :
					<xsl:value-of select="@duration" />
					sec.
				</div>
			</body>
		</xhtml:html>
	</xsl:template>

	<xsl:template name="renderErrors">
		<xsl:for-each select="ns0:errors/ns0:error">
			<xsl:sort select="ns0:rule" />
			<xsl:call-template name="renderLines">
				<xsl:with-param name="line_type">
					<xsl:value-of select="position() mod 2" />
				</xsl:with-param>
				<xsl:with-param name="first">
					<xsl:value-of select="ns0:context" />
				</xsl:with-param>
				<xsl:with-param name="second">
					<xsl:value-of select="ns0:rule" />
				</xsl:with-param>
				<xsl:with-param name="third">
					<xsl:value-of select="ns0:error-name" />
				</xsl:with-param>
			</xsl:call-template>
		</xsl:for-each>
	</xsl:template>

	<xsl:template name="renderConf">
		<xsl:for-each select="ns0:additional-data/ns0:config/ns0:entry">
			<xsl:sort select="ns0:key" />
			<xsl:call-template name="renderLines">
				<xsl:with-param name="line_type">
					<xsl:value-of select="position() mod 2" />
				</xsl:with-param>
				<xsl:with-param name="first">
					<xsl:value-of select="ns0:key" />
				</xsl:with-param>
				<xsl:with-param name="second">
					<xsl:value-of select="ns0:value" />
				</xsl:with-param>
			</xsl:call-template>
		</xsl:for-each>
	</xsl:template>

	<xsl:template name="renderFiles">
		<xsl:for-each select="ns0:additional-data/ns0:files/ns0:file">
			<xsl:sort select="." />
			<xsl:call-template name="renderLines">
				<xsl:with-param name="line_type">
					<xsl:value-of select="position() mod 2" />
				</xsl:with-param>
				<xsl:with-param name="first">
					<xsl:value-of select="." />
				</xsl:with-param>
			</xsl:call-template>
		</xsl:for-each>
	</xsl:template>

	<xsl:template name="renderLines">
		<xsl:param name="line_type" />
		<xsl:param name="first" />
		<xsl:param name="second" select="''" />
		<xsl:param name="third" select="''" />
		<xsl:param name="fourth" select="''" />
		<xsl:choose>
			<xsl:when test="$line_type=0">
				<tr class="even">
					<td class="even">
						<xsl:value-of select="$first" />
					</td>
					<xsl:if test="string-length($second)>0">
						<td class="odd">
							<xsl:value-of select="$second" />
						</td>
					</xsl:if>
					<xsl:if test="string-length($third)>0">
						<td class="even">
							<xsl:value-of select="$third" />
						</td>
					</xsl:if>
					<xsl:if test="string-length($fourth)>0">
						<td class="odd">
							<xsl:value-of select="$fourth" />
						</td>
					</xsl:if>
				</tr>
			</xsl:when>
			<xsl:otherwise>
				<tr class="odd">
					<td class="even">
						<xsl:value-of select="$first" />
					</td>
					<xsl:if test="string-length($second)>0">
						<td class="odd">
							<xsl:value-of select="$second" />
						</td>
					</xsl:if>
					<xsl:if test="string-length($third)>0">
						<td class="even">
							<xsl:value-of select="$third" />
						</td>
					</xsl:if>
					<xsl:if test="string-length($fourth)>0">
						<td class="odd">
							<xsl:value-of select="$fourth" />
						</td>
					</xsl:if>
				</tr>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<!-- GEN -->
	<xsl:template name="render-GEN-001">
		<div class="CodeReviewSection">
			<xsl:call-template name="sectionHeader">
				<xsl:with-param name="title">
					<xsl:value-of select="$lang/l:lang/l:word[@key = 'GEN-001']" />
				</xsl:with-param>
				<xsl:with-param name="ruleId" select="ns0:general/@ruleId" />
				<xsl:with-param name="disabled" select="ns0:general/@disabled" />
			</xsl:call-template>
			<span id="GEN001" class="">
				<div class="CodeReviewData">
					<b>
						<xsl:value-of select="$lang/l:lang/l:word[@key = 'Sources']" />
					</b>
					<br />
					<div class="CodeReviewLine">
						<span class="CodeReviewLabel">
							<xsl:value-of select="$lang/l:lang/l:word[@key = 'Number of processes']" />
						</span>
						<span class="CodeReviewContent">
							<xsl:value-of select="ns0:general/ns0:number-of-processes/text()" />
						</span>
					</div>
					<div class="CodeReviewLine">
						<span class="CodeReviewLabel">
							<xsl:value-of select="$lang/l:lang/l:word[@key = 'Number of activities']" />
						</span>
						<span class="CodeReviewContent">
							<xsl:value-of select="ns0:general/ns0:number-of-activities/text()" />
						</span>
					</div>
					<div class="CodeReviewLine">
						<span class="CodeReviewLabel">
							<xsl:value-of select="$lang/l:lang/l:word[@key = 'Number of files']" />
						</span>
						<span class="CodeReviewContent">
							<xsl:value-of select="ns0:general/ns0:number-of-files/text()" />
						</span>
					</div>
					<div class="CodeReviewLine">
						<span class="CodeReviewLabel">
							<xsl:value-of select="$lang/l:lang/l:word[@key = 'Number of project libraries used']" />
						</span>
						<span class="CodeReviewContent">
							<xsl:value-of select="ns0:general/ns0:number-of-libs/text()" />
						</span>
					</div>
					<br />
					<b>
						<xsl:value-of select="$lang/l:lang/l:word[@key = 'Binaries']" />
					</b>
					<br />
					<div class="CodeReviewLine">
						<span class="CodeReviewLabel">
							<xsl:value-of select="$lang/l:lang/l:word[@key = 'Number of project libraries builder']" />
						</span>
						<span class="CodeReviewContent">
							<xsl:value-of select="ns0:general/ns0:number-of-libbuilder/text()" />
						</span>
					</div>
					<div class="CodeReviewLine">
						<span class="CodeReviewLabel">
							<xsl:value-of select="$lang/l:lang/l:word[@key = 'Number of enterprise archives']" />
						</span>
						<span class="CodeReviewContent">
							<xsl:value-of select="ns0:general/ns0:number-of-ears/text()" />
						</span>
					</div>
					<div class="CodeReviewLine">
						<span class="CodeReviewLabel">
							<xsl:value-of select="$lang/l:lang/l:word[@key = 'Number of process archives']" />
						</span>
						<span class="CodeReviewContent">
							<xsl:value-of select="ns0:general/ns0:number-of-pars/text()" />
						</span>
					</div>
					<div class="CodeReviewLine">
						<span class="CodeReviewLabel">
							<xsl:value-of select="$lang/l:lang/l:word[@key = 'Number of adapter archives']" />
						</span>
						<span class="CodeReviewContent">
							<xsl:value-of select="ns0:general/ns0:number-of-aars/text()" />
						</span>
					</div>
				</div>
			</span>
		</div>
	</xsl:template>

	<xsl:template name="render-GEN-002">
		<div class="CodeReviewSection">
			<xsl:call-template name="sectionHeaderForData">
				<xsl:with-param name="title">
					<xsl:value-of
						select="concat($lang/l:lang/l:word[@key = 'GEN-002'], ' (', count(ns0:global-variable-in-connection-definition-error/ns0:connection), ')')" />
				</xsl:with-param>
				<xsl:with-param name="data" select="ns0:global-variable-in-connection-definition-error" />
			</xsl:call-template>
			<span id="GEN002" class="NotVisible">
				<div class="CodeReviewData">
					<table>
						<tr>
							<th class="LargeInputText">
								<xsl:value-of select="$lang/l:lang/l:word[@key = 'Not well formed connection']" />
							</th>
							<th class="SmallInputText">
								<xsl:value-of select="$lang/l:lang/l:word[@key = 'XPath']" />
							</th>
						</tr>
						<xsl:for-each select="ns0:global-variable-in-connection-definition-error/ns0:connection/ns0:xpath">
							<xsl:call-template name="renderLines">
								<xsl:with-param name="line_type">
									<xsl:value-of select="position() mod 2" />
								</xsl:with-param>
								<xsl:with-param name="first">
									<xsl:value-of select="../ns0:dir/text()" />
								</xsl:with-param>
								<xsl:with-param name="second">
									<xsl:value-of select="text()" />
								</xsl:with-param>
							</xsl:call-template>
						</xsl:for-each>
					</table>
				</div>
			</span>
		</div>
	</xsl:template>

	<xsl:template name="render-GEN-003">
		<div class="CodeReviewSection">
			<xsl:call-template name="sectionHeaderForData">
				<xsl:with-param name="title">
					<xsl:value-of select="concat($lang/l:lang/l:word[@key = 'GEN-003'], ' (', count(ns0:xsdRule[@ruleId='GEN-003']/ns0:xsd), ')')" />
				</xsl:with-param>
				<xsl:with-param name="data" select="ns0:xsdRule[@ruleId='GEN-003']" />
			</xsl:call-template>
			<span id="GEN003" class="NotVisible">
				<div class="CodeReviewData">
					<table>
						<tr>
							<th class="LargeInputText">
								<xsl:value-of select="$lang/l:lang/l:word[@key = 'File']" />
							</th>
						</tr>
						<xsl:for-each select="ns0:xsdRule[@ruleId='GEN-003']/ns0:xsd">
							<xsl:call-template name="renderLines">
								<xsl:with-param name="line_type">
									<xsl:value-of select="position() mod 2" />
								</xsl:with-param>
								<xsl:with-param name="first">
									<xsl:value-of select="ns0:dir/text()" />
								</xsl:with-param>
							</xsl:call-template>
						</xsl:for-each>
					</table>
				</div>
			</span>
		</div>
	</xsl:template>

	<xsl:template name="render-GEN-004">
		<div class="CodeReviewSection">
			<xsl:call-template name="sectionHeaderForData">
				<xsl:with-param name="title">
					<xsl:value-of select="concat($lang/l:lang/l:word[@key = 'GEN-004'], ' (', count(ns0:namespace-collisions/ns0:namespace-collision), ')')" />
				</xsl:with-param>
				<xsl:with-param name="data" select="ns0:namespace-collisions" />
			</xsl:call-template>
			<span id="GEN004" class="NotVisible">
				<div class="CodeReviewData">
					<table>
						<tr>
							<th class="SmallInputText">
								<xsl:value-of select="$lang/l:lang/l:word[@key = 'Namespace']" />
							</th>
							<th class="LargeInputText">
								<xsl:value-of select="$lang/l:lang/l:word[@key = 'File']" />
							</th>
						</tr>
						<xsl:for-each select="ns0:namespace-collisions/ns0:namespace-collision">
							<xsl:sort select="ns0:namespace" />
							<tr class="even">
								<xsl:element name="td">
									<xsl:attribute name="rowspan"><xsl:value-of select="count(ns0:file)" /></xsl:attribute>
									<xsl:attribute name="style">border:1px solid #888; padding: 4px; </xsl:attribute>
									<xsl:attribute name="class"><xsl:value-of select="'even'"></xsl:value-of></xsl:attribute>
									<xsl:value-of select="ns0:namespace" />
								</xsl:element>
								<td class="odd" style="border:1px solid #888; padding: 4px;">
									<xsl:value-of select="ns0:file[1]" />
								</td>
							</tr>
							<xsl:for-each select="ns0:file[position() &gt; 1]">
								<tr class="odd">
									<td class="odd" style="border:1px solid #888; padding: 4px;">
										<xsl:value-of select="." />
									</td>
								</tr>
							</xsl:for-each>
						</xsl:for-each>
					</table>
				</div>
			</span>
		</div>
	</xsl:template>

	<!-- RELIABILITY -->
	<xsl:template name="render-REL-001">
		<div class="CodeReviewSection">
			<xsl:call-template name="sectionHeaderForData">
				<xsl:with-param name="title">
					<xsl:value-of select="concat($lang/l:lang/l:word[@key = 'REL-001'], ' (', count(ns0:recursive-calls/ns0:recursive-call), ')')" />
				</xsl:with-param>
				<xsl:with-param name="data" select="ns0:recursive-calls" />
			</xsl:call-template>
			<span id="REL001" class="NotVisible">
				<div class="CodeReviewData">
					<table>
						<tr>
							<th class="LargeInputText">
								<xsl:value-of select="$lang/l:lang/l:word[@key = 'Stack']" />
							</th>
							<th class="LargeInputText">
								<xsl:value-of select="$lang/l:lang/l:word[@key = 'Reason']" />
							</th>
						</tr>
						<xsl:for-each select="ns0:recursive-calls/ns0:recursive-call">
							<xsl:call-template name="renderLines">
								<xsl:with-param name="line_type">
									<xsl:value-of select="position() mod 2" />
								</xsl:with-param>
								<xsl:with-param name="first">
									<xsl:for-each select="ns0:process">
										<xsl:value-of select="concat(' &gt; ', .) " />
									</xsl:for-each>
								</xsl:with-param>
								<xsl:with-param name="second">
									<xsl:choose>
										<xsl:when test="ns0:checked-recursive/text() = 'true'">
											<xsl:value-of select="$lang/l:lang/l:word[@key = 'Recursion checked']" />
										</xsl:when>
										<xsl:otherwise>
											<xsl:value-of select="$lang/l:lang/l:word[@key = 'Above max process call depth for analysis']" />
										</xsl:otherwise>
									</xsl:choose>
								</xsl:with-param>
							</xsl:call-template>
						</xsl:for-each>
					</table>
				</div>
			</span>
		</div>
	</xsl:template>

	<xsl:template name="render-REL-002">
		<div class="CodeReviewSection">
			<xsl:call-template name="sectionHeaderForData">
				<xsl:with-param name="title">
					<xsl:value-of select="concat($lang/l:lang/l:word[@key = 'REL-002'], ' (', count(ns0:nested-transactions/ns0:nested-transaction), ')')" />
				</xsl:with-param>
				<xsl:with-param name="data" select="ns0:nested-transactions" />
			</xsl:call-template>
			<span id="REL002" class="NotVisible">
				<div class="CodeReviewData">
					<table>
						<tr>
							<th class="LargeInputText">
								<xsl:value-of select="$lang/l:lang/l:word[@key = 'Process']" />
							</th>
							<th class="LargeInputText">
								<xsl:value-of select="$lang/l:lang/l:word[@key = 'Group']" />
							</th>
							<th class="LargeInputText">
								<xsl:value-of select="$lang/l:lang/l:word[@key = 'Process']" />
							</th>
							<th class="LargeInputText">
								<xsl:value-of select="$lang/l:lang/l:word[@key = 'Group']" />
							</th>
						</tr>
						<xsl:for-each select="ns0:nested-transactions/ns0:nested-transaction">
							<xsl:call-template name="renderLines">
								<xsl:with-param name="line_type">
									<xsl:value-of select="position() mod 2" />
								</xsl:with-param>
								<xsl:with-param name="first">
									<xsl:value-of select="ns0:process1" />
								</xsl:with-param>
								<xsl:with-param name="second">
									<xsl:value-of select="ns0:transaction1" />
								</xsl:with-param>
								<xsl:with-param name="third">
									<xsl:value-of select="ns0:process2" />
								</xsl:with-param>
								<xsl:with-param name="fourth">
									<xsl:value-of select="ns0:transaction2" />
								</xsl:with-param>
							</xsl:call-template>
						</xsl:for-each>
					</table>
				</div>
			</span>
		</div>
	</xsl:template>

	<xsl:template name="render-REL-003">
		<xsl:call-template name="sectionForActivityRule">
			<xsl:with-param name="data" select="ns0:activityRule[@ruleId='REL-003']" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template name="render-REL-004">
		<div class="CodeReviewSection">
			<xsl:call-template name="sectionHeaderForData">
				<xsl:with-param name="title">
					<xsl:value-of select="concat($lang/l:lang/l:word[@key = 'REL-004'], ' (', count(ns0:unknown-jms-properties/ns0:unknown-jms-property), ')')" />
				</xsl:with-param>
				<xsl:with-param name="data" select="ns0:unknown-jms-properties" />
			</xsl:call-template>
			<span id="REL004" class="NotVisible">
				<div class="CodeReviewData">
					<table>
						<tr>
							<th class="LargeInputText">
								<xsl:value-of select="$lang/l:lang/l:word[@key = 'Resource']" />
							</th>
							<th class="LargeInputText">
								<xsl:value-of select="$lang/l:lang/l:word[@key = 'Property']" />
							</th>
						</tr>
						<xsl:for-each select="ns0:unknown-jms-properties/ns0:unknown-jms-property">
							<xsl:call-template name="renderLines">
								<xsl:with-param name="line_type">
									<xsl:value-of select="position() mod 2" />
								</xsl:with-param>
								<xsl:with-param name="first">
									<xsl:value-of select="ns0:resource" />
								</xsl:with-param>
								<xsl:with-param name="second">
									<xsl:value-of select="ns0:property" />
								</xsl:with-param>
							</xsl:call-template>
						</xsl:for-each>
					</table>
				</div>
			</span>
		</div>
	</xsl:template>

	<xsl:template name="render-REL-005">
		<xsl:call-template name="sectionForActivityRule">
			<xsl:with-param name="data" select="ns0:activityRule[@ruleId='REL-005']" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template name="render-REL-006">
		<xsl:call-template name="sectionForActivityRule">
			<xsl:with-param name="data" select="ns0:activityRule[@ruleId='REL-006']" />
		</xsl:call-template>
	</xsl:template>

	<!-- EFFICIENCY -->
	<xsl:template name="render-EFF-001">
		<div class="CodeReviewSection">
			<xsl:call-template name="sectionHeaderForData">
				<xsl:with-param name="title">
					<xsl:value-of
						select="concat($lang/l:lang/l:word[@key = 'EFF-001'], ns0:nested-loops/@max-depth, ' (', count(ns0:nested-loops/ns0:nested-loop), ')')" />
				</xsl:with-param>
				<xsl:with-param name="data" select="ns0:nested-loops" />
			</xsl:call-template>
			<span id="EFF001" class="NotVisible">
				<div class="CodeReviewData">
					<table>
						<tr>
							<th class="LargeInputText">
								<xsl:value-of select="$lang/l:lang/l:word[@key = 'Stack']" />
							</th>
						</tr>
						<xsl:for-each select="ns0:nested-loops/ns0:nested-loop">
							<xsl:call-template name="renderLines">
								<xsl:with-param name="line_type">
									<xsl:value-of select="position() mod 2" />
								</xsl:with-param>
								<xsl:with-param name="first">
									<xsl:for-each select="ns0:process[1]">
										<xsl:call-template name="renderStack" />
									</xsl:for-each>
								</xsl:with-param>
							</xsl:call-template>
						</xsl:for-each>
					</table>
				</div>
			</span>
		</div>
	</xsl:template>

	<xsl:template name="render-EFF-002">
		<xsl:call-template name="sectionForActivityRule">
			<xsl:with-param name="data" select="ns0:activityRule[@ruleId='EFF-002']" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template name="render-EFF-003">
		<xsl:call-template name="sectionForActivityRule">
			<xsl:with-param name="data" select="ns0:activityRule[@ruleId='EFF-003']" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template name="render-EFF-004">
		<xsl:call-template name="sectionForActivityRule">
			<xsl:with-param name="data" select="ns0:activityRule[@ruleId='EFF-004']" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template name="render-EFF-005">
		<xsl:call-template name="sectionForActivityRule">
			<xsl:with-param name="data" select="ns0:activityRule[@ruleId='EFF-005']" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template name="render-EFF-006">
		<xsl:call-template name="sectionForActivityRule">
			<xsl:with-param name="data" select="ns0:activityRule[@ruleId='EFF-006']" />
		</xsl:call-template>
	</xsl:template>

	<!-- MAINTAINABILITY -->
	<xsl:template name="render-MAI-001">
		<div class="CodeReviewSection">
			<xsl:call-template name="sectionHeaderForData">
				<xsl:with-param name="title">
					<xsl:value-of select="concat($lang/l:lang/l:word[@key = 'MAI-001'], ' (', count(ns0:transition-colors/ns0:transition-color), ')')" />
				</xsl:with-param>
				<xsl:with-param name="data" select="ns0:transition-colors" />
			</xsl:call-template>
			<span id="MAI001" class="NotVisible">
				<div class="CodeReviewData">
					<table>
						<tr>
							<th class="LargeInputText">
								<xsl:value-of select="$lang/l:lang/l:word[@key = 'Transition type']" />
							</th>
							<th class="LargeInputText" colspan="2">
								<xsl:value-of select="$lang/l:lang/l:word[@key = 'Color']" />
							</th>
						</tr>
						<xsl:for-each select="ns0:transition-colors/ns0:transition-color">
							<xsl:sort select="." data-type="text" order="ascending" />
							<xsl:variable name="line_type" select="position() mod 2" />
							<xsl:choose>
								<xsl:when test="$line_type=0">
									<tr class="even">
										<td class="even">
											<xsl:value-of select="ns0:transition-type" />
										</td>
										<td class="fixed odd">
											<xsl:value-of select="ns0:color" />
										</td>
										<xsl:element name="td">
											<xsl:attribute name="bgcolor"><xsl:value-of select="ns0:color" /></xsl:attribute>
											<xsl:attribute name="class"><xsl:value-of select="'ColorCell'"></xsl:value-of></xsl:attribute>
										</xsl:element>
									</tr>
								</xsl:when>
								<xsl:otherwise>
									<tr class="odd">
										<td class="even">
											<xsl:value-of select="ns0:transition-type" />
										</td>
										<td class="fixed odd">
											<xsl:value-of select="ns0:color" />
										</td>
										<xsl:element name="td">
											<xsl:attribute name="bgcolor"><xsl:value-of select="ns0:color" /></xsl:attribute>
											<xsl:attribute name="class"><xsl:value-of select="'ColorCell'"></xsl:value-of></xsl:attribute>
										</xsl:element>
									</tr>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:for-each>
					</table>
				</div>
			</span>
		</div>
	</xsl:template>

	<xsl:template name="render-MAI-002">
		<xsl:call-template name="sectionForActivityRule">
			<xsl:with-param name="data" select="ns0:activityRule[@ruleId='MAI-002']" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template name="render-MAI-003">
		<div class="CodeReviewSection">
			<xsl:call-template name="sectionHeaderForData">
				<xsl:with-param name="title">
					<xsl:value-of
						select="concat($lang/l:lang/l:word[@key = 'MAI-003'], ns0:process-call-depth/@max-depth, ' (', count(ns0:process-call-depth/ns0:process-call), ')')" />
				</xsl:with-param>
				<xsl:with-param name="data" select="ns0:process-call-depth" />
			</xsl:call-template>
			<span id="MAI003" class="NotVisible">
				<div class="CodeReviewData">
					<table>
						<tr>
							<th class="LargeInputText">
								<xsl:value-of select="$lang/l:lang/l:word[@key = 'Max depth']" />
							</th>
							<th class="LargeInputText">
								<xsl:value-of select="$lang/l:lang/l:word[@key = 'Stack']" />
							</th>
						</tr>
						<xsl:for-each select="ns0:process-call-depth/ns0:process-call">
							<xsl:call-template name="renderLines">
								<xsl:with-param name="line_type">
									<xsl:value-of select="position() mod 2" />
								</xsl:with-param>
								<xsl:with-param name="first">
									<xsl:value-of select="count(ns0:process)" />
								</xsl:with-param>
								<xsl:with-param name="second">
									<xsl:for-each select="ns0:process">
										<xsl:value-of select="concat(' &gt; ', .)" />
									</xsl:for-each>
								</xsl:with-param>
							</xsl:call-template>
						</xsl:for-each>
					</table>
				</div>
			</span>
		</div>
	</xsl:template>

	<xsl:template name="render-MAI-004">
		<div class="CodeReviewSection">
			<xsl:call-template name="sectionHeaderForData">
				<xsl:with-param name="title">
					<xsl:value-of select="concat($lang/l:lang/l:word[@key = 'MAI-004'], ' (', count(ns0:resourceRule[@ruleId='MAI-004']/ns0:resource), ')')" />
				</xsl:with-param>
				<xsl:with-param name="data" select="ns0:resourceRule[@ruleId='MAI-004']" />
			</xsl:call-template>
			<span id="MAI004" class="NotVisible">
				<div class="CodeReviewData">
					<table>
						<tr>
							<th class="LargeInputText">
								<xsl:value-of select="$lang/l:lang/l:word[@key = 'Resources']" />
							</th>
						</tr>
						<xsl:for-each select="ns0:resourceRule[@ruleId='MAI-004']/ns0:resource">
							<xsl:call-template name="renderLines">
								<xsl:with-param name="line_type">
									<xsl:value-of select="position() mod 2" />
								</xsl:with-param>
								<xsl:with-param name="first">
									<xsl:value-of select="." />
								</xsl:with-param>
							</xsl:call-template>
						</xsl:for-each>
					</table>
				</div>
			</span>
		</div>
	</xsl:template>

	<xsl:template name="render-MAI-005">
		<div class="CodeReviewSection">
			<xsl:call-template name="sectionHeaderForData">
				<xsl:with-param name="title">
					<xsl:value-of select="concat($lang/l:lang/l:word[@key = 'MAI-005'], ' (', count(ns0:invalid-imports/ns0:invalid-import), ')')" />
				</xsl:with-param>
				<xsl:with-param name="data" select="ns0:invalid-imports" />
			</xsl:call-template>
			<span id="MAI005" class="NotVisible">
				<div class="CodeReviewData">
					<table>
						<tr>
							<th class="LargeInputText">Projlib</th>
						</tr>
						<xsl:for-each select="ns0:invalid-imports/ns0:invalid-import">
							<xsl:sort select="text()" />
							<xsl:call-template name="renderLines">
								<xsl:with-param name="line_type">
									<xsl:value-of select="position() mod 2" />
								</xsl:with-param>
								<xsl:with-param name="first">
									<xsl:value-of select="text()" />
								</xsl:with-param>
							</xsl:call-template>
						</xsl:for-each>
					</table>
				</div>
			</span>
		</div>
	</xsl:template>

	<xsl:template name="render-MAI-006">
		<xsl:call-template name="sectionForActivityRule">
			<xsl:with-param name="additional-title" select="ns0:activityRule[@ruleId='MAI-006']/ns0:data" />
			<xsl:with-param name="data" select="ns0:activityRule[@ruleId='MAI-006']" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template name="render-MAI-007">
		<div class="CodeReviewSection">
			<xsl:call-template name="sectionHeaderForData">
				<xsl:with-param name="title">
					<xsl:value-of
						select="concat($lang/l:lang/l:word[@key = 'MAI-007'], ns0:number-of-activity-by-process-error/ns0:max-accepted/text(), ' (', count(ns0:number-of-activity-by-process-error/ns0:process-details), ')')" />
				</xsl:with-param>
				<xsl:with-param name="data" select="ns0:number-of-activity-by-process-error" />
			</xsl:call-template>
			<span id="MAI007" class="NotVisible">
				<div class="CodeReviewData">
					<table>
						<tr>
							<th class="LargeInputText">
								<xsl:value-of select="$lang/l:lang/l:word[@key = 'Process']" />
							</th>
							<th class="SmallInputText">
								<xsl:value-of select="$lang/l:lang/l:word[@key = 'Activities count']" />
							</th>
						</tr>
						<xsl:for-each select="ns0:number-of-activity-by-process-error/ns0:process-details">
							<xsl:sort select="ns0:activity-number" data-type="number" order="descending" />
							<xsl:call-template name="renderLines">
								<xsl:with-param name="line_type">
									<xsl:value-of select="position() mod 2" />
								</xsl:with-param>
								<xsl:with-param name="first">
									<xsl:value-of select="ns0:dir/text()" />
								</xsl:with-param>
								<xsl:with-param name="second">
									<xsl:value-of select="ns0:activity-number/text()" />
								</xsl:with-param>
							</xsl:call-template>
						</xsl:for-each>
					</table>
				</div>
			</span>
		</div>
	</xsl:template>

	<xsl:template name="render-MAI-008">
		<xsl:call-template name="sectionForProcessRule">
			<xsl:with-param name="data" select="ns0:processRule[@ruleId='MAI-008']" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template name="render-MAI-009">
		<xsl:call-template name="sectionForProcessRule">
			<xsl:with-param name="data" select="ns0:processRule[@ruleId='MAI-009']" />
			<xsl:with-param name="additional-title"
				select="concat(ns0:processRule[@ruleId='MAI-009']/ns0:data[1], 'x', ns0:processRule[@ruleId='MAI-009']/ns0:data[2])" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template name="render-MAI-010">
		<div class="CodeReviewSection">
			<xsl:call-template name="sectionHeaderForData">
				<xsl:with-param name="title">
					<xsl:value-of select="concat($lang/l:lang/l:word[@key = 'MAI-010'], ' (', count(ns0:unlabelled-transitions/ns0:transition), ')')" />
				</xsl:with-param>
				<xsl:with-param name="data" select="ns0:unlabelled-transitions" />
			</xsl:call-template>
			<span id="MAI010" class="NotVisible">
				<div class="CodeReviewData">
					<table>
						<tr>
							<th class="LargeInputText">
								<xsl:value-of select="$lang/l:lang/l:word[@key = 'Process']" />
							</th>
							<th class="LargeInputText">
								<xsl:value-of select="$lang/l:lang/l:word[@key = 'From']" />
							</th>
							<th class="LargeInputText">
								<xsl:value-of select="$lang/l:lang/l:word[@key = 'To']" />
							</th>
							<th class="LargeInputText">
								<xsl:value-of select="$lang/l:lang/l:word[@key = 'Condition']" />
							</th>
						</tr>
						<xsl:for-each select="ns0:unlabelled-transitions/ns0:transition">
							<xsl:call-template name="renderLines">
								<xsl:with-param name="line_type">
									<xsl:value-of select="position() mod 2" />
								</xsl:with-param>
								<xsl:with-param name="first">
									<xsl:value-of select="ns0:process" />
								</xsl:with-param>
								<xsl:with-param name="second">
									<xsl:value-of select="ns0:from" />
								</xsl:with-param>
								<xsl:with-param name="third">
									<xsl:value-of select="ns0:to" />
								</xsl:with-param>
								<xsl:with-param name="fourth">
									<xsl:value-of select="ns0:condition" />
								</xsl:with-param>
							</xsl:call-template>
						</xsl:for-each>
					</table>
				</div>
			</span>
		</div>
	</xsl:template>

	<xsl:template name="render-MAI-011">
		<div class="CodeReviewSection">
			<xsl:call-template name="sectionHeaderForData">
				<xsl:with-param name="title">
					<xsl:value-of select="concat($lang/l:lang/l:word[@key = 'MAI-011'], ' (', count(ns0:starter-with-output/ns0:process), ')')" />
				</xsl:with-param>
				<xsl:with-param name="data" select="ns0:processRule[@ruleId='MAI-011']" />
			</xsl:call-template>
			<span id="MAI011" class="NotVisible">
				<div class="CodeReviewData">
					<table>
						<tr>
							<th class="LargeInputText">
								<xsl:value-of select="$lang/l:lang/l:word[@key = 'Processes']" />
							</th>
						</tr>
						<xsl:for-each select="ns0:processRule[@ruleId='MAI-011']/ns0:process">
							<xsl:sort select="text()" />
							<xsl:call-template name="renderLines">
								<xsl:with-param name="line_type">
									<xsl:value-of select="position() mod 2" />
								</xsl:with-param>
								<xsl:with-param name="first">
									<xsl:value-of select="./text()" />
								</xsl:with-param>
							</xsl:call-template>
						</xsl:for-each>
					</table>
				</div>
			</span>
		</div>
	</xsl:template>

	<!-- PORTABILITY -->
	<xsl:template name="render-POR-001">
		<xsl:call-template name="sectionForActivityRule">
			<xsl:with-param name="data" select="ns0:activityRule[@ruleId='POR-001']" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template name="render-POR-002">
		<xsl:call-template name="sectionForActivityRule">
			<xsl:with-param name="data" select="ns0:activityRule[@ruleId='POR-002']" />
		</xsl:call-template>
	</xsl:template>

	<!-- TOOLS -->
	<xsl:template name="sectionHeaderForData">
		<xsl:param name="title" />
		<xsl:param name="data" />
		<xsl:call-template name="sectionHeader">
			<xsl:with-param name="title" select="$title" />
			<xsl:with-param name="disabled" select="$data/@disabled" />
			<xsl:with-param name="ruleId" select="$data/@ruleId" />
			<xsl:with-param name="grade" select="$data/@grade" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template name="sectionHeader">
		<xsl:param name="title" />
		<xsl:param name="disabled" />
		<xsl:param name="ruleId" select="''" />
		<xsl:param name="grade" select="''" />
		<xsl:element name="span">
			<xsl:attribute name="class">IMGFlecheBas</xsl:attribute>
			<xsl:attribute name="onclick"><xsl:value-of select="concat('codeReviewSectionMaskData(&quot;', translate($ruleId, '-', ''), '&quot;)')" />
		</xsl:attribute>
			<img src="resources/images/flechebas.png" alt="SEE"></img>
			<span class="CodeReviewTitle">
				<xsl:if test="$ruleId != 'ERR' and $ruleId != 'CONF' and $ruleId != 'FILES' and $ruleId != ''">
					<xsl:value-of select="concat($ruleId, ' - ')" />
				</xsl:if>
				<xsl:value-of select="$title" />
				<xsl:if test="$disabled = 'true'">
					<xsl:value-of select="concat(' - ', $lang/l:lang/l:word[@key = 'Disabled'])" />
				</xsl:if>
			</span>
			<xsl:if test="$grade != ''">
				<xsl:call-template name="displayGrade">
					<xsl:with-param name="grade" select="$grade" />
				</xsl:call-template>
			</xsl:if>
		</xsl:element>
	</xsl:template>

	<xsl:template name="sectionBodyForProcessRule">
		<xsl:param name="data" />
		<xsl:variable name="spanId" select="translate($data/@ruleId, '-', '')" />
		<xsl:element name="span">
			<xsl:attribute name="class">NotVisible</xsl:attribute>
			<xsl:attribute name="id"><xsl:value-of select="$spanId" /></xsl:attribute>
			<div class="CodeReviewData">
				<table>
					<tr>
						<th class="LargeInputText">
							<xsl:value-of select="$lang/l:lang/l:word[@key = 'Processes']" />
						</th>
					</tr>
					<xsl:for-each select="$data/ns0:process">
						<xsl:sort select="text()" />
						<xsl:call-template name="renderLines">
							<xsl:with-param name="line_type">
								<xsl:value-of select="position() mod 2" />
							</xsl:with-param>
							<xsl:with-param name="first">
								<xsl:value-of select="./text()" />
							</xsl:with-param>
						</xsl:call-template>
					</xsl:for-each>
				</table>
			</div>
		</xsl:element>
	</xsl:template>

	<xsl:template name="sectionBodyForActivityRule">
		<xsl:param name="data" />
		<xsl:variable name="spanId" select="translate($data/@ruleId, '-', '')" />
		<xsl:element name="span">
			<xsl:attribute name="class">NotVisible</xsl:attribute>
			<xsl:attribute name="id"><xsl:value-of select="$spanId" /></xsl:attribute>
			<div class="CodeReviewData">
				<table>
					<tr>
						<th class="LargeInputText">
							<xsl:value-of select="$lang/l:lang/l:word[@key = 'Process']" />
						</th>
						<th class="LargeInputText">
							<xsl:value-of select="$lang/l:lang/l:word[@key = 'Activity']" />
						</th>
					</tr>
					<xsl:for-each select="$data/ns0:call">
						<xsl:sort select="ns0:process" />
						<xsl:sort select="ns0:activity" />

						<xsl:call-template name="renderLines">
							<xsl:with-param name="line_type">
								<xsl:value-of select="position() mod 2" />
							</xsl:with-param>
							<xsl:with-param name="first">
								<xsl:value-of select="ns0:process" />
							</xsl:with-param>
							<xsl:with-param name="second">
								<xsl:value-of select="ns0:activity" />
							</xsl:with-param>
						</xsl:call-template>
					</xsl:for-each>
				</table>
			</div>
		</xsl:element>
	</xsl:template>

	<xsl:template name="sectionForProcessRule">
		<xsl:param name="additional-title" select="''" />
		<xsl:param name="data" />
		<div class="CodeReviewSection">
			<xsl:call-template name="sectionHeaderForData">
				<xsl:with-param name="title"
					select="concat($lang/l:lang/l:word[@key = $data/@ruleId], ' ', $additional-title, ' (', count($data/ns0:process), ')')" />
				<xsl:with-param name="data" select="$data" />
			</xsl:call-template>
			<xsl:call-template name="sectionBodyForProcessRule">
				<xsl:with-param name="data" select="$data" />
			</xsl:call-template>
		</div>
	</xsl:template>

	<xsl:template name="sectionForActivityRule">
		<xsl:param name="additional-title" select="''" />
		<xsl:param name="data" />

		<div class="CodeReviewSection">
			<xsl:call-template name="sectionHeaderForData">
				<xsl:with-param name="title"
					select="concat($lang/l:lang/l:word[@key = $data/@ruleId], ' ', $additional-title, ' (', count($data/ns0:call), ')')" />
				<xsl:with-param name="data" select="$data" />
			</xsl:call-template>
			<xsl:call-template name="sectionBodyForActivityRule">
				<xsl:with-param name="data" select="$data" />
			</xsl:call-template>
		</div>
	</xsl:template>

	<xsl:template name="renderStack">
		<xsl:value-of select="concat(., ' : ' , following::ns0:group[1])" />
		<xsl:for-each select="following-sibling::ns0:process[1]">
			<xsl:value-of select="' &gt; '" />
			<xsl:call-template name="renderStack" />
		</xsl:for-each>
	</xsl:template>

	<xsl:template name="displayGrade">
		<xsl:param name="grade" />
		<div class="noteBloc">
			<xsl:element name="span">
				<xsl:attribute name="class">noteBloc</xsl:attribute>
				<xsl:choose>
					<xsl:when test="$grade &lt;= 25">
						<xsl:attribute name="style">
							<xsl:value-of select="concat('width: ', $grade + 5, 'px; background-color: #D00000;')" />
						</xsl:attribute>
					</xsl:when>
					<xsl:when test="$grade &lt;= 50">
						<xsl:attribute name="style">
							<xsl:value-of select="concat('width: ', $grade + 5, 'px; background-color: #DE9816;')" />
						</xsl:attribute>
					</xsl:when>
					<xsl:when test="$grade &lt;= 75">
						<xsl:attribute name="style">
							<xsl:value-of select="concat('width: ', $grade + 5, 'px; background-color: #FFF48D;')" />
						</xsl:attribute>
					</xsl:when>
					<xsl:when test="$grade &lt;= 99">
						<xsl:attribute name="style">
							<xsl:value-of select="concat('width: ', $grade + 5, 'px; background-color: #C0F000;')" />
						</xsl:attribute>
					</xsl:when>
					<xsl:otherwise />
				</xsl:choose>
				<xsl:value-of select="$grade" />
			</xsl:element>
		</div>
	</xsl:template>

	<xsl:template name="displayMenuItem">
		<xsl:param name="category" />
		<u>
			<xsl:value-of select="$lang/l:lang/l:word[@key = $category]" />
		</u>
		<xsl:call-template name="displayGrade">
			<xsl:with-param name="grade" select="ns0:aggregated-grades/ns0:aggregated-grade[@category=$category]/text()" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template name="displayMenu">
		<div class="IMGMenue">
			<table>
				<tr>
					<td class="MenuButton" id="bGEN" onclick="menue('GEN')">
						<xsl:call-template name="displayMenuItem">
							<xsl:with-param name="category" select="'GEN'" />
						</xsl:call-template>
					</td>
					<td class="MenuButton" id="bREL" onclick="menue('REL')">
						<xsl:call-template name="displayMenuItem">
							<xsl:with-param name="category" select="'REL'" />
						</xsl:call-template>
					</td>
					<td class="MenuButton" id="bEFF" onclick="menue('EFF')">
						<xsl:call-template name="displayMenuItem">
							<xsl:with-param name="category" select="'EFF'" />
						</xsl:call-template>
					</td>
					<td class="MenuButton" id="bMAI" onclick="menue('MAI')">
						<xsl:call-template name="displayMenuItem">
							<xsl:with-param name="category" select="'MAI'" />
						</xsl:call-template>
					</td>
					<td class="MenuButton" id="bPOR" onclick="menue('POR')">
						<xsl:call-template name="displayMenuItem">
							<xsl:with-param name="category" select="'POR'" />
						</xsl:call-template>
					</td>
					<td class="MenuButton" id="bErrors" onclick="menue('Errors')">
						<u>
							<xsl:value-of select="concat($lang/l:lang/l:word[@key = 'ERR'], ' (', count(ns0:errors/ns0:error), ')')" />
						</u>
					</td>
					<td class="MenuButton" id="bInfos" onclick="menue('Infos')">
						<u>
							<xsl:value-of select="$lang/l:lang/l:word[@key = 'Informations']" />
						</u>
					</td>
				</tr>
			</table>
		</div>
	</xsl:template>
</xsl:stylesheet>