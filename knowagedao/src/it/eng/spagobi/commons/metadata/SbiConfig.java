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
package it.eng.spagobi.commons.metadata;

import it.eng.spagobi.commons.bo.Config;



/**
 * SbiConfig generated by hbm2java
 */

public class SbiConfig  extends SbiHibernateModel {


    // Fields    

     private Integer id;
     private SbiDomains sbiDomains;
     private String label;
     private String name;
     private String description;
     private boolean isActive;
     private String valueCheck;
     private String category;


    // Constructors

    /** default constructor */
    public SbiConfig() {
    }

	/** minimal constructor */
    public SbiConfig(Integer id, String label) {
        this.id = id;
        this.label = label;
    }
    
    /** full constructor */
    public SbiConfig(Integer id, SbiDomains sbiDomains, String label, String name, String description, boolean isActive, String valueCheck, String category) {
        this.id = id;
        this.sbiDomains = sbiDomains;
        this.label = label;
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        this.valueCheck = valueCheck;
        this.category = category;
    }
    

   
    // Property accessors

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public SbiDomains getSbiDomains() {
        return this.sbiDomains;
    }
    
    public void setSbiDomains(SbiDomains sbiDomains) {
        this.sbiDomains = sbiDomains;
    }

    public String getLabel() {
        return this.label;
    }
    
    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isIsActive() {
        return this.isActive;
    }
    
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getValueCheck() {
        return this.valueCheck;
    }
    
    public void setValueCheck(String valueCheck) {
        this.valueCheck = valueCheck;
    }
    
	/**
	 * @return the category to get
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * @param category. 
	 * The category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

    /**
	 * From the Hibernate SbiConfig object at input, gives the corrispondent
	 * <code>Config</code> object.
	 * 
	 * @param hibConf The Hibernate Config object
	 * 
	 * @return the corrispondent output <code>Config</code>
	 */
	public Config toConfig(){

		Config config = new Config();
		config.setId(getId());
		config.setLabel(getLabel());
		config.setName(getName());
		config.setDescription(getDescription());
		config.setActive(isActive);
		config.setValueCheck(getValueCheck());
		config.setCategory(getCategory());
		SbiDomains tmpDom = getSbiDomains();
		if (tmpDom != null) { 
			config.setValueTypeId(tmpDom.getValueId().intValue());
		}
		return config;
	}



}
