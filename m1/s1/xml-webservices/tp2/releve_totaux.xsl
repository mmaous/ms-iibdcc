<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    version="1.0">
    <xsl:template match="/">
        <html>
            <head>
                <title>Relevé Bancaire</title>
                <style>
                    table { border-collapse: collapse; width: 60%;}
                    th, td { border: 1px solid black; padding: 8px; text-align: center; }
                    th { background-color: #e0e0e0; }
                    h2 { text-align: center; }

                </style>
            </head>
            <body>
                <div>
                    <h2>Relevé de Compte</h2>
                    <p><strong>RIB :</strong> <xsl:value-of select="releve/@RIB"/></p>
                    <p><strong>Date du relevé :</strong> <xsl:value-of select="releve/dateReleve"/></p>
                    <p><strong>Solde actuel :</strong> <xsl:value-of select="releve/solde"/></p>
                    <p>
                        Période : Du <xsl:value-of select="releve/operations/@dateDebut"/> 
                        au <xsl:value-of select="releve/operations/@dateFin"/>
                    </p>
                </div>
                
                <table>
                    <tr>
                        <th>Date</th>
                        <th>Description</th>
                        <th>Type</th>
                        <th>Montant</th>
                    </tr>
                    <xsl:for-each select="releve/operations/operation">
                        <tr>
                            <td><xsl:value-of select="@date"/></td>
                            <td><xsl:value-of select="@description"/></td>
                            <td><xsl:value-of select="@type"/></td>
                            <td><xsl:value-of select="@montant"/></td>
                        </tr>
                    </xsl:for-each>
                </table>
                
                <div>
                    <h3>Totaux</h3>
                    <p><strong>
                            Total Crédit : 
                            <xsl:value-of select="sum(releve/operations/operation[@type='CREDIT']/@montant)"/></strong>
                    </p>
                    <p>
                        Total Débit : 
                        <xsl:value-of select="sum(releve/operations/operation[@type='DEBIT']/@montant)"/>
                    </p>
                </div>
            </body>
        </html>
    </xsl:template>    
</xsl:stylesheet>