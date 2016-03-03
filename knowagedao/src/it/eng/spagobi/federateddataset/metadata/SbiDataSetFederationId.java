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

package it.eng.spagobi.federateddataset.metadata;

// Generated 4-giu-2009 12.50.24 by Hibernate Tools 3.1.0 beta3

/**
 * SbiExportersId generated by hbm2java
 */

public class SbiDataSetFederationId implements java.io.Serializable {

	// Fields
	private int federationId;
	private int dsId;
	private int versionNum;
	private String organization;

	public SbiDataSetFederationId() {
		super();
	}

	public int getFederationId() {
		return federationId;
	}

	public void setFederationId(int federationId) {
		this.federationId = federationId;
	}

	public int getDsId() {
		return dsId;
	}

	public void setDsId(int dsId) {
		this.dsId = dsId;
	}

	public int getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(int versionNum) {
		this.versionNum = versionNum;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public SbiDataSetFederationId(int federationId, int dsId, int versionNum, String organization) {
		super();
		this.federationId = federationId;
		this.dsId = dsId;
		this.versionNum = versionNum;
		this.organization = organization;
	}

}
