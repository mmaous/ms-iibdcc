<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml" media-type="image/svg+xml" indent="yes" />
    <xsl:template match="/">
        <svg xmlns:xlink="http://www.w3.org/1999/xlink" xmlns="http://www.w3.org/2000/svg">
            
            <line x1="20" y1="20" x2="20" y2="400" stroke="blue" stroke-width="2"></line>
            
            <line x1="20" y1="400" x2="800" y2="400" stroke="blue" stroke-width="2"></line>
            
            <xsl:for-each select="meteo/mesure[@date='2006-01-01']">
                <xsl:for-each select="ville">
                    
                    <xsl:variable name="temp" select="@temperature"> </xsl:variable>
                    <xsl:variable name="H" select="$temp*10"></xsl:variable>
                    <xsl:variable name="XR" select="position()*80"></xsl:variable>
                    <xsl:variable name="YR" select="400-$H"> </xsl:variable>
                    
                    <text x="{$XR}" y="420" stroke="blue">
                        <xsl:value-of select="@nom"/>
                    </text>
                    
                    <rect width="10" height="{$H}" x="{$XR}" y="{$YR}" stroke="blue" stroke-width="2" fill="yellow">
                        <animate attributeName="height" dur="3s" from="0" to="{$H}"></animate>
                        <animate attributeName="y" dur="3s" from="400" to="{$YR}"></animate>
                    </rect>
                    
                </xsl:for-each>
            </xsl:for-each>
        </svg>
    </xsl:template>
</xsl:stylesheet>