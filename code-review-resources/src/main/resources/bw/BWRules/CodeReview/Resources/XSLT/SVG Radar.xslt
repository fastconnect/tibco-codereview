<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:res="http://www.fastconnect.fr/FCTibcoFactory/CodeReview/ReviewResult.xsd">

	<xsl:output method="xml" indent="yes" media-type="image/svg+xml" doctype-public="-//W3C//DTD SVG 1.1//EN" doctype-system="http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd" />

	<xsl:template match="/">
		<svg width="5cm" height="5cm" viewBox="0 0 800 800" preserveAspectRatio="none" xmlns="http://www.w3.org/2000/svg" version="1.1"
			xmlns:xlink="http://www.w3.org/1999/xlink">
			<style type="text/css">
        <![CDATA[
        svg {
            fill: none;
            stroke: #000;
            stroke-opacity: 1;
            stroke-width: 1pt;
        }
        
        text {
            fill: #000;
            font-family: arial;
            font-size: 30pt;
            font-weight: bold;
			stroke: none;
            text-anchor: middle;
        }
        
        circle {
        	fill: #FFF;
        	stroke: none;
        }
        
        polygon.review { 
        	stroke-width: 5pt;
        	stroke: #216a8e;
        	fill: #2f98cc;
        }
        
        line.axis {
            stroke-width: 2pt;
        }
        
        line.gradiant {
        	stroke: #333;
            stroke-width: 1pt;
        }
        ]]>
			</style>

			<title>FCCodeReview</title>
			<script>
		    <![CDATA[
		    
		    	function calculatePoint(value, angle) {
					return "" + (Math.round(3* Math.sin(angle * Math.PI / 180) * value)) + "," + (0-Math.round(3*Math.cos(angle * Math.PI / 180) * value));
		    	}
		    	
				function values(gen,rel,eff,mai,por) {
					var reviewPoints = calculatePoint(gen, 0);
					reviewPoints += " " + calculatePoint(rel, 72);
					reviewPoints += " " + calculatePoint(eff, 144);
					reviewPoints += " " + calculatePoint(mai, 216);
					reviewPoints += " " + calculatePoint(por, 288);
					var rev = document.getElementsByClassName("review")[0];
					rev.setAttribute('points', reviewPoints);
		        }
			]]>
			</script>
			<defs>
				<g id="grid">
					<line class="gradiant" x1="-10" y1="-60" x2="10" y2="-60" />
					<line class="gradiant" x1="-10" y1="-120" x2="10" y2="-120" />
					<line class="gradiant" x1="-10" y1="-180" x2="10" y2="-180" />
					<line class="gradiant" x1="-10" y1="-240" x2="10" y2="-240" />
					<line class="gradiant" x1="-10" y1="-300" x2="10" y2="-300" />

					<line class="axis" x1="0" y1="0" y2="-300" />
				</g>
			</defs>

			<g transform="translate(400 400)">
				<circle cx="0" cy="0" r="400" />

				<polygon class="review" points="100,100 -100,100 100,-100, -100,-100" />

				<use xlink:href="#grid" transform="rotate(0)" />
				<use xlink:href="#grid" transform="rotate(-72)" />
				<use xlink:href="#grid" transform="rotate(-144)" />
				<use xlink:href="#grid" transform="rotate(-216)" />
				<use xlink:href="#grid" transform="rotate(-288)" />

				<g id="labels">
					<text x="0" y="-320">GEN</text>
					<text x="320" y="-110">REL</text>
					<text x="220" y="280">EFF</text>
					<text x="-220" y="280">MAI</text>
					<text x="-320" y="-110">POR</text>
				</g>
			</g>

			<script>
				<xsl:variable name="gen" select="//res:aggregated-grade[@category='GEN']/text()" />
				<xsl:variable name="rel" select="//res:aggregated-grade[@category='REL']/text()" />
				<xsl:variable name="eff" select="//res:aggregated-grade[@category='EFF']/text()" />
				<xsl:variable name="mai" select="//res:aggregated-grade[@category='MAI']/text()" />
				<xsl:variable name="por" select="//res:aggregated-grade[@category='POR']/text()" />
				<xsl:text disable-output-escaping="yes">&lt;![CDATA[</xsl:text>
				<xsl:value-of select="concat('values(',$gen,',',$rel,',',$eff,',',$mai,',',$por,');')" />
				<xsl:text disable-output-escaping="yes">]]&gt;</xsl:text>
			</script>

		</svg>

	</xsl:template>

	<xsl:template match="*" />
</xsl:stylesheet>