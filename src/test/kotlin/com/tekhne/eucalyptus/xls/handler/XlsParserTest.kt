package com.tekhne.eucalyptus.xls.handler

import com.tekhne.eucalyptus.xls.model.*
import org.assertj.core.api.Assertions.assertThat
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

object XlsParserTest: Spek({

    Feature("Parse XLS file") {
        Scenario("When I parse a well formated file") {
            Then("BatchFile should contains 3 items") {
                val xls = this.javaClass.classLoader.getResourceAsStream("normal.xlsx")
                val batch = XlsParser.parse(xls, "normal")
                assertThat(batch.cases).containsExactly(
                        Case("Paris Saint Ouen", Format.MARKET, IFLG.I, "FRPC43", 1153, null, 394, 7646, 7646, 3021080370903, 6, AdosseSat.ADOSSE, Environment.PROD, "PARIS", "CARREFOUR MARKET PARIS ST OUEN", "102 AVENUE DE ST OUEN", null, "75018", "PARIS", null, null, null, null, "PROX_7646_BL", null, null, null),
                        Case("PARIS DEMOURS", Format.MARKET, IFLG.I, "FRPC35", 1154, null, 212, 7577, 7577, 3020180037488, 6, AdosseSat.ADOSSE, Environment.PROD, "PARIS", "CARREFOUR MARKET PARIS DEMOURS", "3 RUE PIERRE DEMOURS", null, "75017", "PARIS", null, null, null, null, "PROX_7577_BL", null, null, null),
                        Case("PARIS AVRON", Format.HYPER, IFLG.F, "FRQCJ8", 1155, null, null, 6429, 6429, 3020180215466, 6, AdosseSat.ADOSSE, Environment.PROD, "PARIS", "CARREFOUR PROXI PARIS AVRON", "45-47 RUE D'AVRON", null, "75020", "PARIS", null, null, null, null, "PROX_6429_BL", null, null, null),
                        Case("MORZINE", Format.MARKET, IFLG.LG, "FRPC03", 1159, "1234a", 432, 8430, 8430, 3020180036184, 6, AdosseSat.ADOSSE, Environment.PROD, "RHONE ALPES", "CARREFOUR MARKET MORZINE", "141 B ROUTE DU PLAN", "Adresse 3", "74110", "MORZINE", "IM6-0432", "10.224.135.183", "HP", "LOWER", "IM6-0432", "10.224.135.183", "HP", "UPPER")
                )
                assertThat(batch.name).isEqualTo("normal")
            }
        }
    }
})