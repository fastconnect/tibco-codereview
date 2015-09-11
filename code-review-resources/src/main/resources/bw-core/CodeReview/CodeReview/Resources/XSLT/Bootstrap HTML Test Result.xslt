<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
	xmlns:rr="http://www.fastconnect.fr/FCTibcoFactory/CodeReview/ReviewResult.xsd"
	xmlns:rc="http://www.fastconnect.fr/FCTibcoFactory/CodeReview/RuleCard.xsd"
	xmlns:l="http://fastconnect.fr/Lang.xsd"
	xmlns:xhtml="http://www.w3.org/1999/xhtml">

	<!-- output HTML tree in a file -->
	<xsl:output method="html"
				encoding="UTF-8"
				media-type="text/html"
				omit-xml-declaration="yes"
				doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN"
				doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"
				indent="yes" />

	<xsl:param name="lang" select="document('lang.xml')" />
	<xsl:param name="svg-filename" />

	<!-- one result generate one <html> tree -->
	<xsl:template match="rr:review-result">
		<xsl:call-template name="html"/>
	</xsl:template>

	<!-- <html> has one <head> and one <body> -->
	<xsl:template name="html">
		<html xmlns="http://www.w3.org/1999/xhtml" >
			<xsl:call-template name="head"/>
			<xsl:call-template name="body"/>
		</html>
	</xsl:template>

	<xsl:template name="head" xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<title>FC Code Review</title>

			<meta charset="UTF-8" />
			<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />

			<xsl:call-template name="head-style"/>
		</head>
	</xsl:template>

	<xsl:template name="head-style">
		<link rel="stylesheet" title="CodeReviewCSS" type="text/css" media="screen" href="resources/code-review.css" />
		<link rel="stylesheet" title="CodeReviewCSS" type="text/css" media="screen" href="resources/docs.css" />

        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />

        <link href="resources/bootstrap.min.css" rel="stylesheet" />
        <!--[if lt IE 9]>
          <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
	</xsl:template>

	<xsl:template name="body">
		<body data-spy="scroll" data-target="#top-nav" data-twttr-rendered="true">
			<div class="container-fluid">
				<xsl:call-template name="top-menu" />
				<xsl:call-template name="main-container"/>
			</div>
			<xsl:call-template name="footer"/>
			<xsl:call-template name="footer-scripts"/>
		</body>
	</xsl:template>

	<xsl:template name="top-menu">
		<div id="top-menu-tabs">
			<!-- Nav tabs -->
			<div>
				<ul class="nav nav-tabs" role="tablist">
					<li>
						<xsl:element name="a">
							<xsl:attribute name="class" select="'brand'" />
							<xsl:if test="string-length(//rr:review-result/@home-page)>0">
								<xsl:attribute name="href" select="//rr:review-result/@home-page" />
							</xsl:if>
							<strong><span class="color-highlight">FC</span> Code Review</strong>
						</xsl:element>
					</li>
					<xsl:for-each select="//rr:categories/rc:category">
						<xsl:variable name="current-category" select="./rc:category"/>
						<xsl:element name="li">
							<xsl:attribute name="role" select="'presentation'"/>
							<xsl:element name="a">
								<xsl:attribute name="href" select="concat('#', rc:category)"/>
								<xsl:attribute name="aria-controls" select="rc:category"/>
								<xsl:attribute name="role" select="'tab'"/>
								<xsl:attribute name="data-toggle" select="'tab'"/>
								<xsl:value-of select="concat(rc:name, ' ')" />
								<xsl:variable name="rules_count" select="count(//rr:rule[rc:rule/rc:category = $current-category])" />
								<xsl:if test="$rules_count > 0">
									<xsl:variable name="issues_count" select="count(//rr:rule[rc:rule/rc:category = $current-category and count(rr:result) > 0])" />
									<xsl:element name="span">
										<xsl:attribute name="class" select="'badge'"/>
										<xsl:value-of select="concat($issues_count, ' / ', $rules_count)" />
									</xsl:element>
								</xsl:if>
							</xsl:element>
						</xsl:element>
					</xsl:for-each>
				</ul>
			</div>
		</div>
	</xsl:template>

	<xsl:template name="main-container">
		<!-- Tab panes -->
		<div class="tab-content">
			<xsl:for-each select="//rr:categories/rc:category">
				<xsl:variable name="current-category" select="./rc:category"/>
				<xsl:if test="$current-category!='ERR' and $current-category!='CONF' and $current-category!='FILES'">
					<xsl:element name="div">
						<xsl:attribute name="role" select="'tabpanel'"/>
						<xsl:attribute name="class" select="'tab-pane'"/>
						<xsl:attribute name="id" select="rc:category"/>

						<xsl:element name="h1">
							<xsl:value-of select="rc:name"/>
						</xsl:element>
						<xsl:element name="p">
							<xsl:value-of select="rc:description"/>
						</xsl:element>
						<xsl:if test="$current-category = 'GEN'">
							<xsl:call-template name="render-rule">
								<xsl:with-param name="rule" select="//rr:general"/>
							</xsl:call-template>
						</xsl:if>
						<xsl:for-each select="//rr:rule[rc:rule/rc:category = $current-category]">
							<xsl:call-template name="render-rule">
								<xsl:with-param name="rule" select="."/>
							</xsl:call-template>
						</xsl:for-each>
					</xsl:element>
				</xsl:if>
			</xsl:for-each>

			<xsl:element name="div">
				<xsl:attribute name="role" select="'tabpanel'"/>
				<xsl:attribute name="class" select="'tab-pane'"/>
				<xsl:attribute name="id" select="'ERR'"/>

				<xsl:variable name="error_category" select="//rr:categories/rc:category[rc:category = 'ERR']"/>
				<xsl:element name="h1">
					<xsl:value-of select="$error_category/rc:name"/>
				</xsl:element>
				<xsl:element name="p">
					<xsl:value-of select="$error_category/rc:description"/>
				</xsl:element>
			</xsl:element>

			<xsl:element name="div">
				<xsl:attribute name="role" select="'tabpanel'"/>
				<xsl:attribute name="class" select="'tab-pane'"/>
				<xsl:attribute name="id" select="'CONF'"/>

				<xsl:variable name="conf_category" select="//rr:categories/rc:category[rc:category = 'CONF']"/>
				<xsl:element name="h1">
					<xsl:value-of select="$conf_category/rc:name"/>
				</xsl:element>
				<xsl:element name="p">
					<xsl:value-of select="$conf_category/rc:description"/>
				</xsl:element>
				<table class="table table-striped">
					<tr>
						<th>
							<xsl:value-of select="'Key'" />
						</th>
						<th>
							<xsl:value-of select="'Value'" />
						</th>
					</tr>
					<xsl:for-each select="rr:additional-data/rr:config/rr:entry">
						<tr>
							<xsl:element name="td">
								<xsl:value-of select="rr:key" />
							</xsl:element>
							<xsl:element name="td">
								<xsl:value-of select="rr:value" />
							</xsl:element>
						</tr>
					</xsl:for-each>
				</table>
			</xsl:element>

			<xsl:element name="div">
				<xsl:attribute name="id" select="'FILES'"/>
				<xsl:attribute name="role" select="'tabpanel'"/>
				<xsl:attribute name="class" select="'tab-pane'"/>
				<xsl:variable name="files_category" select="//rr:categories/rc:category[rc:category = 'FILES']"/>
				<xsl:element name="h1">
					<xsl:value-of select="$files_category/rc:name"/>
				</xsl:element>
				<xsl:element name="p">
					<xsl:value-of select="$files_category/rc:description"/>
				</xsl:element>
				<table class="table table-striped">
					<tr>
						<th>
							<xsl:value-of select="$files_category/rc:name" />
						</th>
					</tr>
					<xsl:for-each select="rr:additional-data/rr:files/rr:file">
						<tr>
							<xsl:element name="td">
								<xsl:value-of select="." />
							</xsl:element>
						</tr>
					</xsl:for-each>
				</table>
			</xsl:element>
		</div>
	</xsl:template>

	<xsl:template name="side-menu">
		<div role="complementary" class="fc-cr-sidebar hidden-print hidden-xs hidden-sm affix affix">
			<ul class="nav fc-cr-sidenav">
				<xsl:for-each select="//rr:categories/rc:category">
					<xsl:variable name="current-category" select="./rc:category"/>
					<xsl:element name="li">
						<xsl:element name="a">
							<xsl:attribute name="href" select="concat('#', rc:category)"/>
							<xsl:value-of select="rc:name" />
						</xsl:element>

						<xsl:element name="ul">
							<xsl:attribute name="class" select="'nav'"/>
							<xsl:for-each select="//rr:rule[rc:rule/rc:category = $current-category]">
								<xsl:element name="li">
									<xsl:element name="a">
										<xsl:attribute name="href" select="concat('#', rc:rule/rc:key)"/>
										<xsl:value-of select="rc:rule/rc:key" />
									</xsl:element>
								</xsl:element>
							</xsl:for-each>
						</xsl:element>
					</xsl:element>
				</xsl:for-each>
			</ul>
		</div>
	</xsl:template>

	<xsl:template name="footer">
		<footer class="footer">
			<p class="text-muted credit">
				<i>Project</i> :
				<strong><xsl:value-of select="@project-name" /></strong>
				- <i>Review Date</i> :
				<strong><xsl:value-of select="substring-before(translate(@timestamp, 'T', ' '), '.')" /></strong>
				- <i>Review Duration</i> :
				<strong><xsl:value-of select="@duration" /></strong>
				sec.
				<br />
				<span class="color-highlight">FC</span> Code Review for TIBCO BusinessWorks - &#169; 2013-2015  - <a href="http://www.fastconnect.fr/" target="_blank">FastConnect</a>
			</p>
		</footer>
	</xsl:template>

	<xsl:template name="footer-scripts">
		<script type='text/javascript' src="resources/jquery.min.js">//</script>
		<script type='text/javascript' src="resources/bootstrap.min.js">//</script>
		<script type="text/javascript" src="resources/docs.js">//</script>
		<script type='text/javascript'>
	        $(document).ready(function() {
				$('body').scrollspy({ target: '.navbar-fixed-top' })
	        });

	        $('#top-menu-tabs li:eq(1) a').tab('show'); // Select first tab
        </script>
	</xsl:template>

	<xsl:template name="render-rule">
		<xsl:param name="rule" />

		<div class="panel panel-default">
			<xsl:attribute name="role" select="'button'" />
			<xsl:attribute name="data-toggle" select="'collapse'" />
			<xsl:attribute name="aria-expanded" select="'false'" />
			<xsl:attribute name="href" select="concat('#collapse_', $rule/rc:rule/rc:key)" />
			<xsl:attribute name="aria-controls" select="concat('collapse_', $rule/rc:rule/rc:key)" />

			<div class="panel-heading">
				<xsl:call-template name="rule-header">
					<xsl:with-param name="rule" select="$rule" />
				</xsl:call-template>
			</div>
			<xsl:element name="div">
				<xsl:choose>			
					<xsl:when test="$rule/local-name() = 'general'">
						<xsl:attribute name="class" select="'collapse in'" />
					</xsl:when>
					<xsl:otherwise>
						<xsl:attribute name="class" select="'collapse'" />
					</xsl:otherwise>
				</xsl:choose>
				<xsl:attribute name="id"><xsl:value-of select="concat('collapse_', $rule/rc:rule/rc:key)" /></xsl:attribute>
				<xsl:element name="div">
					<xsl:attribute name="class" select="'panel-body'" />
					<xsl:attribute name="id"><xsl:value-of select="$rule/rc:rule/rc:key" /></xsl:attribute>
					<div>
						<div class="row">
							<div class="col-md-3">
								<p>
									<strong><em>Key: </em></strong><tt><xsl:value-of select="$rule/rc:rule/rc:key" /></tt><br />
									<strong><em>Name: </em></strong><tt><xsl:value-of select="$rule/rc:rule/rc:infos[1]/rc:name" /></tt><br />
									<strong><em>Weight: </em></strong><tt><xsl:value-of select="$rule/rc:rule/rc:weight" /></tt><br />
									<xsl:if test="count($rule/rr:result)">
										<xsl:choose>
											<xsl:when test="$rule/@grade > 0 ">
												<strong><em>Score: </em></strong><tt><xsl:value-of select="concat('100 - ', $rule/rc:rule/rc:weight, ' * ', count($rule/rr:result), ' = ', $rule/@grade)" /></tt><br />
											</xsl:when>
											<xsl:otherwise>
												<strong><em>Score: </em></strong><tt><xsl:value-of select="concat('100 - ', $rule/rc:rule/rc:weight, ' * ', count($rule/rr:result), ' &lt;= ', $rule/@grade)" /></tt><br />
											</xsl:otherwise>
										</xsl:choose>
									</xsl:if>
								</p>
							</div>
							<xsl:choose>
								<xsl:when test="$rule/local-name() = 'general'">
									<div class="col-md-4">
										<p>
											<strong><em>Description: </em></strong><xsl:value-of select="$rule/rc:rule/rc:infos[1]/rc:descriptionHTML" /><br /><br />
											<strong><em>Number of processes: </em></strong><xsl:value-of select="$rule/rr:number-of-processes" /><br />
											<strong><em>Number of activities: </em></strong><xsl:value-of select="$rule/rr:number-of-activities" /><br />
											<strong><em>Number of files: </em></strong><xsl:value-of select="$rule/rr:number-of-files" /><br />
											<strong><em>Number of libraries (Projlibs): </em></strong><xsl:value-of select="$rule/rr:number-of-libs" /><br />
											<strong><em>Number of EARs: </em></strong><xsl:value-of select="$rule/rr:number-of-ears" /><br />
											<strong><em>Number of PARs: </em></strong><xsl:value-of select="$rule/rr:number-of-pars" /><br />
											<strong><em>Number of AARs: </em></strong><xsl:value-of select="$rule/rr:number-of-aars" /><br />
											<strong><em>Number of library builder: </em></strong><xsl:value-of select="$rule/rr:number-of-libbuilder" /><br />
										</p>
									</div>
									<div class="col-md-5">
										<xsl:element name="embed">
											<xsl:attribute name="src"><xsl:value-of select="$svg-filename" /></xsl:attribute>
											<xsl:attribute name="style" select="'float: right;'" />
											<xsl:attribute name="width">250</xsl:attribute>
											<xsl:attribute name="height">250</xsl:attribute>
										</xsl:element>
									</div>
								</xsl:when>
								<xsl:otherwise>
									<div class="col-md-9">
										<p>
											<strong><em>Description</em></strong><br />
											<pre>
												<xsl:value-of disable-output-escaping="yes" select="$rule/rc:rule/rc:infos[1]/rc:descriptionHTML" />
											</pre>
											<br />
										</p>
										<xsl:if test="exists($rule/rc:rule/rc:infos[1]/rc:correctionHTML)">
											<p>
												<strong><em>Correction</em></strong><br />
												<pre>
													<xsl:value-of disable-output-escaping="yes" select="$rule/rc:rule/rc:infos[1]/rc:correctionHTML" />
												</pre>
												<br />
											</p>
										</xsl:if>
									</div>
								</xsl:otherwise>
							</xsl:choose>
							<!-- display parameters -->
							<xsl:if test="count($rule/rr:param) > 0">
								<hr />
								<xsl:element name="div">
									<xsl:attribute name="class" select="'col-md-12'"/>
									<p><strong><em>Parameters used</em></strong></p>
									<table class="table table-striped">
										<tr>
											<th>
												<xsl:value-of select="'key'" />
											</th>
											<th>
												<xsl:value-of select="'value'" />
											</th>
										</tr>
										<xsl:for-each select="$rule/rr:param">
											<tr>
												<td>
													<xsl:value-of select="rr:key" />
												</td>
												<td>
													<xsl:value-of select="rr:value" />
												</td>
											</tr>
										</xsl:for-each>
									</table>
								</xsl:element>
							</xsl:if>
							<hr />
							<!-- display issues -->
							<xsl:element name="div">
								<xsl:attribute name="class" select="'col-md-12'"/>
								<xsl:choose>
									<xsl:when test="count($rule/rr:result) > 0">
											<p><strong><em>Output</em></strong></p>
											<xsl:for-each-group select="$rule/rr:result" group-by="rr:key">
												<xsl:element name="div">
													<xsl:attribute name="class" select="'table-responsive'"/>
													<table class="table table-striped">
														<tr>
															<th>
																<xsl:value-of select="rr:key" />
															</th>
															<xsl:for-each select="rr:child">
																<th>
																	<xsl:value-of select="rr:key" />
																</th>
															</xsl:for-each>
														</tr>
														<xsl:for-each select="current-group()">
															<tr>
																<xsl:element name="td">
																	<xsl:value-of select="rr:value" />
																</xsl:element>
																<xsl:for-each select="rr:child">
																	<xsl:element name="td">
																		<xsl:value-of select="rr:value" />
																	</xsl:element>
																</xsl:for-each>
															</tr>
														</xsl:for-each>
													</table>
												</xsl:element>
											</xsl:for-each-group>
									</xsl:when>
									<xsl:otherwise>
										<p><i>No issue detected.</i></p>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:element>
						</div>
					</div>
				</xsl:element>
			</xsl:element>
		</div>
	</xsl:template>

	<xsl:template name="rule-header">
		<xsl:param name="rule" />

		<xsl:element name="span">
			<xsl:attribute name="class" select="'label label-default'" />
			<xsl:value-of select="$rule/rc:rule/rc:key" />
		</xsl:element>

		<xsl:element name="h3">
			<xsl:attribute name="id"><xsl:value-of select="$rule/rc:rule/rc:key"/></xsl:attribute>
			<xsl:value-of select="$rule/rc:rule/rc:infos[1]/rc:name" />
			<xsl:if test="$rule/@disabled = true()">
				<xsl:value-of select="' - Disabled'" />
			</xsl:if>
		</xsl:element>
		<xsl:if test="$rule/@grade != ''">
			<xsl:choose>
				<xsl:when test="$rule/@grade = '0'">
					<xsl:call-template name="display-grade">
						<xsl:with-param name="grade" select="0"/>
					</xsl:call-template>
				</xsl:when>			
				<xsl:otherwise>				
					<xsl:call-template name="display-grade">
						<xsl:with-param name="grade" select="$rule/@grade" />
					</xsl:call-template>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:if>
	</xsl:template>

	<xsl:template name="display-grade">
		<xsl:param name="grade" />
		<div class="progress" style="width: 150px;">
			<xsl:element name="div">
				<xsl:attribute name="role">progressbar</xsl:attribute>
				<xsl:attribute name="style">width: <xsl:value-of select="$grade" />%; min-width: 25px;</xsl:attribute>
				<xsl:attribute name="aria-valuenow"><xsl:value-of select="$grade" /></xsl:attribute>
				<xsl:attribute name="aria-valuemin">0</xsl:attribute>
				<xsl:attribute name="aria-valuemax">100</xsl:attribute>
				<xsl:choose>
					<xsl:when test="$grade &lt;= 25">
						<xsl:attribute name="class">progress-bar progress-bar-danger</xsl:attribute>
					</xsl:when>
					<xsl:when test="$grade &lt;= 75">
						<xsl:attribute name="class">progress-bar progress-bar-warning</xsl:attribute>
					</xsl:when>
					<xsl:when test="$grade &lt;= 99">
						<xsl:attribute name="class">progress-bar progress-bar-info</xsl:attribute>
					</xsl:when>
					<xsl:when test="$grade &lt;= 100">
						<xsl:attribute name="class">progress-bar progress-bar-success</xsl:attribute>
					</xsl:when>
					<xsl:otherwise/>
				</xsl:choose>
				<xsl:value-of select="concat($grade, ' %')" />
				<xsl:element name="span">
					<xsl:attribute name="class">sr-only</xsl:attribute>
					<xsl:attribute name="style">display: none; visibility: hidden;</xsl:attribute>
					<xsl:value-of select="concat($grade, ' %')" />
				</xsl:element>
			</xsl:element>
		</div>
	</xsl:template>

<!--
	<xsl:template name="renderErrors">
		<div class="CodeReviewSection">
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
						<xsl:for-each select="//rr:errors/rr:error">
							<xsl:sort select="rr:rule" />
							<xsl:call-template name="renderLines">
								<xsl:with-param name="line_type">
									<xsl:value-of select="position() mod 2" />
								</xsl:with-param>
								<xsl:with-param name="first">
									<xsl:value-of select="rr:context" />
								</xsl:with-param>
								<xsl:with-param name="second">
									<xsl:value-of select="rr:rule" />
								</xsl:with-param>
								<xsl:with-param name="third">
									<xsl:value-of select="rr:error-name" />
								</xsl:with-param>
							</xsl:call-template>
						</xsl:for-each>
					</table>
				</div>
			</span>
		</div>
	</xsl:template>
-->

</xsl:stylesheet>