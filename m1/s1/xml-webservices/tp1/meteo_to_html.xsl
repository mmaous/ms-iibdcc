<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:template match="/">
        <html>
            <body>
                <h2>Meteo</h2>
                <xsl:for-each select="meteo/mesure">
                    <h3>Date : <xsl:value-of select="@date"/></h3>
                    <table border="1" width="300">
                        <tr style="background-color:gray;color:white">
                            <th>Ville</th>
                            <th>Température</th>
                        </tr>
                        <xsl:for-each select="ville">
                            <tr>
                                <td><xsl:value-of select="@nom"/></td>
                                <td><xsl:value-of select="@temperature"/> °C</td>
                            </tr>
                        </xsl:for-each>
                    </table>
                </xsl:for-each>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>