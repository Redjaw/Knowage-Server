/*
 * Knowage, Open Source Business Intelligence suite
 * Copyright (C) 2016 Engineering Ingegneria Informatica S.p.A.
 * 
 * Knowage is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Knowage is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.eng.spagobi.kpi.config.metadata;

import it.eng.spagobi.commons.metadata.SbiHibernateModel;


// Generated 17-set-2010 11.25.47 by Hibernate Tools 3.2.4.GA

/**
 * SbiKpiRel generated by hbm2java
 */
public class SbiKpiRel extends SbiHibernateModel {

	private Integer kpiRelId;
	private SbiKpi sbiKpiByKpiChildId;
	private SbiKpi sbiKpiByKpiFatherId;
	private String parameter;

	public SbiKpiRel() {
	}

	public SbiKpiRel(SbiKpi sbiKpiByKpiChildId, SbiKpi sbiKpiByKpiFatherId) {
		this.sbiKpiByKpiChildId = sbiKpiByKpiChildId;
		this.sbiKpiByKpiFatherId = sbiKpiByKpiFatherId;
	}

	public SbiKpiRel(SbiKpi sbiKpiByKpiChildId, SbiKpi sbiKpiByKpiFatherId,
			String parameter) {
		this.sbiKpiByKpiChildId = sbiKpiByKpiChildId;
		this.sbiKpiByKpiFatherId = sbiKpiByKpiFatherId;
		this.parameter = parameter;
	}

	public Integer getKpiRelId() {
		return this.kpiRelId;
	}

	public void setKpiRelId(Integer kpiRelId) {
		this.kpiRelId = kpiRelId;
	}

	public SbiKpi getSbiKpiByKpiChildId() {
		return this.sbiKpiByKpiChildId;
	}

	public void setSbiKpiByKpiChildId(SbiKpi sbiKpiByKpiChildId) {
		this.sbiKpiByKpiChildId = sbiKpiByKpiChildId;
	}

	public SbiKpi getSbiKpiByKpiFatherId() {
		return this.sbiKpiByKpiFatherId;
	}

	public void setSbiKpiByKpiFatherId(SbiKpi sbiKpiByKpiFatherId) {
		this.sbiKpiByKpiFatherId = sbiKpiByKpiFatherId;
	}

	public String getParameter() {
		return this.parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

}
