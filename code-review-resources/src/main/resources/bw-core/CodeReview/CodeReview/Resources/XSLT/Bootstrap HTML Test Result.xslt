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
		<html xmlns="http://www.w3.org/1999/xhtml">
			<xsl:call-template name="head"/>
			<xsl:call-template name="body"/>
		</html>
	</xsl:template>

	<xsl:template name="head" xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<title>FC Code Review</title>

<!--
			<link rel="stylesheet" title="CodeReviewCSS" type="text/css" media="screen" href="resources/code-review.css" />
			<link rel="stylesheet" title="CodeReviewCSS" type="text/css" media="screen" href="resources/docs.css" />

	        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />

	        <link href="resources/bootstrap.min.css" rel="stylesheet" />
-->
	        <!--[if lt IE 9]>
	          <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	        <![endif]-->
		</head>
	</xsl:template>

	<xsl:template name="body">
		<body data-spy="scroll" data-target="#top-nav" data-twttr-rendered="true">
			<xsl:call-template name="top-menu" />
			<xsl:call-template name="main-container"/>
			<xsl:call-template name="footer"/>
			<xsl:call-template name="scripts_footer"/>
		</body>
	</xsl:template>

	<xsl:template name="main-container">
		<div class="page-container fc-cr-container">
			<div class="row row-offcanvas row-offcanvas-left">
				<!-- sidebar -->
				<div class="col-lg-2 sidebar-offcanvas" id="sidebar" role="navigation">
					<xsl:call-template name="side-menu"/>
				</div>
				<div class="col-lg-10">
					<div class="container"><!-- main area -->
						<xsl:for-each select="//rr:categories/rc:category">
							<xsl:variable name="current-category" select="./rc:category"/>
							<xsl:element name="div">
								<xsl:attribute name="id" select="rc:category"/>
								<xsl:element name="h1">
									<xsl:value-of select="rc:name"/>
								</xsl:element>
								<xsl:element name="p">
									<xsl:value-of select="rc:description"/>
								</xsl:element>
								<xsl:for-each select="//rr:rule[rc:rule/rc:category = $current-category]">
									<xsl:call-template name="renderRule">
										<xsl:with-param name="rule" select="."/>
									</xsl:call-template>
								</xsl:for-each>
							</xsl:element>
						</xsl:for-each>

						<!-- . ERRORS . -->
						<div id="Errors">
							<div class="CodeReviewSection">
								<xsl:call-template name="sectionHeader">
									<xsl:with-param name="title">
										<xsl:value-of select="concat($lang/l:lang/l:word[@key = 'ERR'], ' (', count(rr:errors/rr:error), ')')" />
									</xsl:with-param>
									<xsl:with-param name="itemsCount">0</xsl:with-param>
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
	
						<!-- . INFOS . . -->
						<div id="Infos">
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
									<xsl:with-param name="itemsCount">0</xsl:with-param>
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
									<xsl:with-param name="itemsCount">0</xsl:with-param>
									<xsl:with-param name="ruleId" select="'FILES'" />
									<xsl:with-param name="disabled" select="false()" />
								</xsl:call-template>
								<span id="FILES">
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
				</div><!-- /.col-xs-12 main -->

			</div><!--/.row -->
		</div><!--/.page-container -->
	</xsl:template>

	<xsl:template name="top-menu">
		<div id="top-nav" class="navbar navbar-default navbar-fixed-top affix">
			<div class="container">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="#">FC Code Review</a>
				</div>
				<div class="collapse navbar-collapse">
					<ul class="nav navbar-nav">
					<xsl:for-each select="//rr:categories/rc:category">
						<xsl:variable name="current-category" select="./rc:category"/>
						<xsl:element name="li">
							<xsl:element name="a">
								<xsl:attribute name="href" select="concat('#', rc:category)"/>
								<xsl:value-of select="rc:name" />
							</xsl:element>
						</xsl:element>
					</xsl:for-each>

<!--
						<li>
							<a href="#GEN"><xsl:value-of select="$lang/l:lang/l:word[@key = 'GEN']"/></a>
						</li>
						<li>
							<a href="#REL"><xsl:value-of select="$lang/l:lang/l:word[@key = 'REL']"/></a>
						</li>
						<li>
							<a href="#EFF"><xsl:value-of select="$lang/l:lang/l:word[@key = 'EFF']"/></a>
						</li>
						<li>
							<a href="#MAI"><xsl:value-of select="$lang/l:lang/l:word[@key = 'MAI']"/></a>
						</li>
						<li>
							<a href="#POR"><xsl:value-of select="$lang/l:lang/l:word[@key = 'POR']"/></a>
						</li>
-->
						<li>
							<a href="#ERR"><xsl:value-of select="$lang/l:lang/l:word[@key = 'ERR']"/></a>
						</li>
						<li>
							<a href="#CONF"><xsl:value-of select="$lang/l:lang/l:word[@key = 'Configuration']"/></a>
						</li>
						<li>
							<a href="#FILES"><xsl:value-of select="$lang/l:lang/l:word[@key = 'Files']"/></a>
						</li>
					</ul>
				</div>
			</div>
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
		<div id="footer">
			<div class="container">
				<p class="text-muted credit">
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
				</p>
			</div>
		</div>
	</xsl:template>

	<xsl:template name="scripts_footer">
<!-- 
        <script type='text/javascript' src="resources/jquery.min.js">//</script>
        <script type='text/javascript' src="resources/bootstrap.min.js">//</script>
        <script type="text/javascript" src="resources/docs.js">//</script>
-->
        <!-- JavaScript jQuery code from Bootply.com editor  -->
<!--
        <script type='text/javascript'>        
        $(document).ready(function() {
			$('body').scrollspy({ target: '.navbar-fixed-top' })
        });
        </script>
-->
	</xsl:template>

	<xsl:template name="renderErrors">
		<xsl:for-each select="rr:errors/rr:error">
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
	</xsl:template>

	<xsl:template name="renderConf">
		<xsl:for-each select="rr:additional-data/rr:config/rr:entry">
			<xsl:sort select="rr:key" />
			<xsl:call-template name="renderLines">
				<xsl:with-param name="line_type">
					<xsl:value-of select="position() mod 2" />
				</xsl:with-param>
				<xsl:with-param name="first">
					<xsl:value-of select="rr:key" />
				</xsl:with-param>
				<xsl:with-param name="second">
					<xsl:value-of select="rr:value" />
				</xsl:with-param>
			</xsl:call-template>
		</xsl:for-each>
	</xsl:template>

	<xsl:template name="renderFiles">
		<xsl:for-each select="rr:additional-data/rr:files/rr:file">
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

	<xsl:template name="renderRule">
		<xsl:param name="rule" />

		<div class="CodeReviewSection">
			<xsl:call-template name="rule-header">
				<xsl:with-param name="rule" select="$rule" />
			</xsl:call-template>
			<xsl:element name="span">
				<xsl:attribute name="id"><xsl:value-of select="$rule/rc:rule/rc:key"/></xsl:attribute>
				<xsl:for-each-group select="$rule/rr:result" group-by="rr:key">
					<table>
						<tr>
							<th class="SmallInputText">
								<xsl:value-of select="rr:key" />
							</th>
							<xsl:for-each select="rr:child">
								<th class="SmallInputText">
									<xsl:value-of select="rr:key" />
								</th>
							</xsl:for-each>
						</tr>
						<xsl:for-each select="current-group()">
							<tr>
								<xsl:element name="td">
									<xsl:attribute name="style">border:1px solid #888; padding: 4px; </xsl:attribute>
									<xsl:attribute name="class"><xsl:value-of select="'even'"></xsl:value-of></xsl:attribute>
									<xsl:value-of select="rr:value" />
								</xsl:element>
								<xsl:for-each select="rr:child">
									<xsl:element name="td">
										<xsl:attribute name="style">border:1px solid #888; padding: 4px; </xsl:attribute>
										<xsl:attribute name="class"><xsl:value-of select="'even'"></xsl:value-of></xsl:attribute>
										<xsl:value-of select="rr:value" />
									</xsl:element>
								</xsl:for-each>
							</tr>
						</xsl:for-each>
						<!--</xsl:for-each>-->
					</table>
				</xsl:for-each-group>
			</xsl:element>
		</div>
	</xsl:template>

	<!-- TOOLS -->
	<xsl:template name="rule-header">
		<xsl:param name="rule" />

		<xsl:element name="h3">
			<xsl:attribute name="id"><xsl:value-of select="$rule/rc:rule/rc:key"/></xsl:attribute>
			<xsl:value-of select="concat($rule/rc:rule/rc:key, ' - ', $rule/rc:rule/rc:infos/rc:name)" />
			<xsl:if test="$rule/@disabled = true()">
				<xsl:value-of select="' - Disabled'" />
			</xsl:if>
		</xsl:element>
		<xsl:if test="$rule/@grade != ''">
			<xsl:choose>
				<xsl:when test="$rule/@grade = '0'">
					<xsl:call-template name="display-grade">
						<xsl:with-param name="grade" select="1"/>
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

	<xsl:template name="sectionHeaderForData">
		<xsl:param name="title" />
		<xsl:param name="itemsCount" />
		<xsl:param name="data" />
		<xsl:call-template name="sectionHeader">
			<xsl:with-param name="title" select="$title" />
			<xsl:with-param name="itemsCount" select="$itemsCount" />
			<xsl:with-param name="disabled" select="$data/@disabled" />
			<xsl:with-param name="ruleId" select="$data/@ruleId" />
			<xsl:with-param name="grade" select="$data/@grade" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template name="sectionHeader">
		<xsl:param name="title" />
		<xsl:param name="itemsCount" />
		<xsl:param name="disabled" />
		<xsl:param name="ruleId" select="''" />
		<xsl:param name="grade" select="''" />
		<xsl:element name="h3">
			<xsl:attribute name="id"><xsl:value-of select="$ruleId"/></xsl:attribute>
			<xsl:if test="$ruleId != 'ERR' and $ruleId != 'CONF' and $ruleId != 'FILES' and $ruleId != ''">
				<xsl:value-of select="concat($ruleId, ' - ')" />
			</xsl:if>
			<xsl:value-of select="$title" />
			<xsl:if test="$disabled = true()">
				<xsl:value-of select="concat(' - ', $lang/l:lang/l:word[@key = 'Disabled'])" />
			</xsl:if>
		</xsl:element>
		<xsl:if test="$grade != ''">
			<xsl:choose>
				<xsl:when test="$grade = '0'">
					<xsl:call-template name="display-grade">
						<xsl:with-param name="grade" select="1"/>
					</xsl:call-template>
				</xsl:when>			
				<xsl:otherwise>				
					<xsl:call-template name="display-grade">
						<xsl:with-param name="grade" select="$grade" />
					</xsl:call-template>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:if>
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
					<xsl:for-each select="$data/rr:process">
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
					<xsl:for-each select="$data/rr:call">
						<xsl:sort select="rr:process" />
						<xsl:sort select="rr:activity" />

						<xsl:call-template name="renderLines">
							<xsl:with-param name="line_type">
								<xsl:value-of select="position() mod 2" />
							</xsl:with-param>
							<xsl:with-param name="first">
								<xsl:value-of select="rr:process" />
							</xsl:with-param>
							<xsl:with-param name="second">
								<xsl:value-of select="rr:activity" />
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
					select="concat($lang/l:lang/l:word[@key = $data/@ruleId], ' ', $additional-title)" />
				<xsl:with-param name="itemsCount" select="count($data/rr:process)" />
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
					select="concat($lang/l:lang/l:word[@key = $data/@ruleId], ' ', $additional-title)" />
				<xsl:with-param name="itemsCount" select="count($data/rr:call)" />
				<xsl:with-param name="data" select="$data" />
			</xsl:call-template>
			<xsl:call-template name="sectionBodyForActivityRule">
				<xsl:with-param name="data" select="$data" />
			</xsl:call-template>
		</div>
	</xsl:template>

	<xsl:template name="renderStack">
		<xsl:value-of select="concat(., ' : ' , following::rr:group[1])" />
		<xsl:for-each select="following-sibling::rr:process[1]">
			<xsl:value-of select="' &gt; '" />
			<xsl:call-template name="renderStack" />
		</xsl:for-each>
	</xsl:template>

	<xsl:template name="display-grade">
		<xsl:param name="grade" />
		<div class="progress" style="width: 150px;">
			<xsl:element name="div">
				<xsl:attribute name="role">progressbar</xsl:attribute>
				<xsl:attribute name="style">width: <xsl:value-of select="$grade" />%; min-width: 10px;</xsl:attribute>
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
				<!-- <xsl:value-of select="$grade" /> -->
				<xsl:element name="span">
					<xsl:attribute name="class">sr-only</xsl:attribute>
					<xsl:value-of select="$grade" />%
				</xsl:element>
			</xsl:element>
		</div>
	</xsl:template>

	<xsl:template name="displayMenuItem">
		<xsl:param name="category" />
		<u>
			<xsl:value-of select="$lang/l:lang/l:word[@key = $category]" />
		</u>
		<xsl:choose>
			<xsl:when test="rr:aggregated-grades/rr:aggregated-grade[@category=$category]/text() = '0'">
				<xsl:call-template name="display-grade">
					<xsl:with-param name="grade" select="1"/>
				</xsl:call-template>
			</xsl:when>			
			<xsl:otherwise>				
				<xsl:call-template name="display-grade">
					<xsl:with-param name="grade" select="rr:aggregated-grades/rr:aggregated-grade[@category=$category]/text()" />
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
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
							<xsl:value-of select="concat($lang/l:lang/l:word[@key = 'ERR'], ' (', count(rr:errors/rr:error), ')')" />
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