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
// Generated 13-gen-2009 11.26.29 by Hibernate Tools 3.1.0 beta3



/**
 * SbiKpiInstPeriod generated by hbm2java
 */

public class SbiKpiInstPeriod  extends SbiHibernateModel {


    // Fields    

     private Integer kpiInstPeriodId;
     private SbiKpiInstance sbiKpiInstance;
     private SbiKpiPeriodicity sbiKpiPeriodicity;
     private Boolean default_;


    // Constructors

    /** default constructor */
    public SbiKpiInstPeriod() {
    }

    
    /** full constructor */
    public SbiKpiInstPeriod(Integer kpiInstPeriodId, SbiKpiInstance sbiKpiInstance, SbiKpiPeriodicity sbiKpiPeriodicity, Boolean default_) {
        this.kpiInstPeriodId = kpiInstPeriodId;
        this.sbiKpiInstance = sbiKpiInstance;
        this.sbiKpiPeriodicity = sbiKpiPeriodicity;
        this.default_ = default_;
    }
    

   
    // Property accessors

    public Integer getKpiInstPeriodId() {
        return this.kpiInstPeriodId;
    }
    
    public void setKpiInstPeriodId(Integer kpiInstPeriodId) {
        this.kpiInstPeriodId = kpiInstPeriodId;
    }

    public SbiKpiInstance getSbiKpiInstance() {
        return this.sbiKpiInstance;
    }
    
    public void setSbiKpiInstance(SbiKpiInstance sbiKpiInstance) {
        this.sbiKpiInstance = sbiKpiInstance;
    }

    public SbiKpiPeriodicity getSbiKpiPeriodicity() {
        return this.sbiKpiPeriodicity;
    }
    
    public void setSbiKpiPeriodicity(SbiKpiPeriodicity sbiKpiPeriodicity) {
        this.sbiKpiPeriodicity = sbiKpiPeriodicity;
    }
   
    public Boolean isDefault_() {
        return this.default_;
    }
    
    public void setDefault_(Boolean default_) {
        this.default_ = default_;
    }







}
