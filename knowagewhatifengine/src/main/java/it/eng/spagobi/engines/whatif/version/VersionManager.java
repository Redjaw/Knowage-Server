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

package it.eng.spagobi.engines.whatif.version;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.olap4j.OlapException;
import org.olap4j.metadata.Cube;
import org.olap4j.metadata.Hierarchy;
import org.olap4j.metadata.Member;
import org.pivot4j.PivotModel;
import org.pivot4j.transform.ChangeSlicer;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;

import it.eng.spagobi.engines.whatif.WhatIfEngineInstance;
import it.eng.spagobi.engines.whatif.cube.CubeUtilities;
import it.eng.spagobi.engines.whatif.exception.WhatIfPersistingTransformationException;
import it.eng.spagobi.engines.whatif.model.ModelConfig;
import it.eng.spagobi.engines.whatif.model.ModelUtilities;
import it.eng.spagobi.engines.whatif.model.SpagoBIPivotModel;
import it.eng.spagobi.tools.datasource.bo.IDataSource;
import it.eng.spagobi.utilities.engines.SpagoBIEngineRuntimeException;
import it.eng.spagobi.utilities.exceptions.SpagoBIEngineRestServiceRuntimeException;
import it.eng.spagobi.utilities.exceptions.SpagoBIRuntimeException;

public class VersionManager {

	public static transient Logger logger = Logger.getLogger(VersionManager.class);

	private final WhatIfEngineInstance instance;
	private final VersionDAO versionDAO;

	public VersionManager(WhatIfEngineInstance instance) {
		super();
		this.instance = instance;
		versionDAO = new VersionDAO(instance);
	}

	public PivotModel persistNewVersionProcedure(String name, String descr) throws WhatIfPersistingTransformationException {
		return persistNewVersionProcedure(null, name, descr);
	}

	public PivotModel persistNewVersionProcedure(Integer newVersion, String name, String descr) throws WhatIfPersistingTransformationException {
		Integer actualVersion = ((SpagoBIPivotModel) instance.getPivotModel()).getActualVersionSlicer(instance.getModelConfig());
		return persistNewVersionProcedure(actualVersion, newVersion, name, descr);
	}

	/**
	 * Creates a new version in the db and persists the modifications in the new version
	 *
	 * @param version
	 *            the actual version (the new one will be version+1)
	 * @return
	 */
	public PivotModel persistNewVersionProcedure(Integer version, Integer newVersion, String name, String descr)
			throws WhatIfPersistingTransformationException {
		logger.debug("IN");
		Monitor persistNewVersionProcedure = MonitorFactory
				.start("WhatIfEngine/it.eng.spagobi.engines.whatif.WhatIfEngineInstance.VersionDAO.increaseVersion.persistNewVersionProcedure");
		Connection connection;
		IDataSource dataSource = instance.getDataSource();

		try {
			logger.debug("Getting the connection to DB");
			connection = dataSource.getConnection();
		} catch (Exception e) {
			logger.error("Error in opening connection to datasource " + dataSource.getLabel());
			throw new SpagoBIRuntimeException("Error in opening connection to datasource " + dataSource.getLabel(), e);
		}

		try {

			if (newVersion == null) {
				logger.debug("Get last version");
				newVersion = versionDAO.getLastVersion(connection);
				newVersion = newVersion + 1;
				logger.debug("Tne new version is " + newVersion);
			}

			logger.debug("Duplicate data with new version");
			versionDAO.increaseActualVersion(connection, version, newVersion, name, descr);

			logger.debug("Apply Transformations on new version");
			applyTransformation(connection, newVersion);

			logger.debug("Reload Model");
			new ModelUtilities().reloadModel(instance, instance.getPivotModel());

			logger.debug("Set new version as actual");
			instance.getModelConfig().setActualVersion(newVersion);
			setNextVersionSlicer(newVersion);

		} catch (WhatIfPersistingTransformationException we) {
			logger.error("Error persisting the trasformations in the new version a new version", we);
			throw we;
		} catch (Exception e) {
			logger.error("Error creating a new version", e);
			throw new SpagoBIEngineRestServiceRuntimeException("versionresource.generic.error", instance.getLocale(), e);
		} finally {
			logger.debug("Closing the SAVE AS connection");
			persistNewVersionProcedure.stop();
			try {
				connection.close();
			} catch (SQLException e) {
				logger.error("Error closing the connection to the db");
				throw new SpagoBIEngineRestServiceRuntimeException(instance.getLocale(), e);
			}
			logger.debug("SAVE AS connection closed");
		}

		logger.debug("OUT");
		return instance.getPivotModel();

	}

	private void setNextVersionSlicer(Integer version) {
		logger.debug("IN");

		// get cube
		Cube cube = instance.getPivotModel().getCube();

		Hierarchy hierarchy = CubeUtilities.getVersionHierarchy(cube, instance.getModelConfig());

		logger.debug("New version is " + version);
		Member member = null;
		try {
			List<Member> members = hierarchy.getRootMembers();

			// get last member
			logger.debug("Member list has " + members.size() + " members");

			member = members.get(members.size() - 1);

		} catch (OlapException e) {
			logger.error("Error when searching for current version member among the version one");
			throw new SpagoBIEngineRuntimeException("Could not find current version member", e);
		}

		ChangeSlicer ph = instance.getPivotModel().getTransform(ChangeSlicer.class);
		List<Member> slicers = ph.getSlicer(hierarchy);

		slicers.clear();

		slicers.add(member);
		ph.setSlicer(hierarchy, slicers);

		logger.debug("Slicer is set");
	}

	private void applyTransformation(Connection connection, Integer newVersion) throws Exception {
		logger.debug("IN");

		// get member of new version
		logger.debug("New version is " + newVersion);

		try {
			((SpagoBIPivotModel) instance.getPivotModel()).persistTransformations(connection, newVersion);
		} catch (WhatIfPersistingTransformationException e) {
			logger.debug("Error persisting the modifications", e);
			throw e;
		}
		logger.debug("OUT");

	}

	public List<SbiVersion> getAllVersions() {

		Connection connection;
		IDataSource dataSource = instance.getDataSource();

		try {
			logger.debug("Getting the connection to DB");
			connection = dataSource.getConnection();
		} catch (Exception e) {
			logger.error("Error in opening connection to datasource " + dataSource.getLabel());
			throw new SpagoBIRuntimeException("Error in opening connection to datasource " + dataSource.getLabel(), e);
		}

		try {
			return versionDAO.getAllVersions(connection);

		} catch (Exception e) {
			logger.error("Error getting the list of versions", e);
			throw new SpagoBIEngineRestServiceRuntimeException("versionresource.generic.error", instance.getLocale(), e);
		} finally {
			logger.debug("Closing the connection");
			try {
				connection.close();
			} catch (SQLException e) {
				logger.error("Error closing the connection to the db");
				throw new SpagoBIEngineRestServiceRuntimeException(instance.getLocale(), e);
			}
			logger.debug("connection closed");
		}

	}

	public void deleteVersions(String versionIds) {
		Monitor deleteAction = MonitorFactory
				.start("WhatIfEngine/it.eng.spagobi.engines.whatif.WhatIfEngineInstance.VersionDAO.deleteVersion.onlyDeleteMethod");
		if (versionIds == null || versionIds.length() == 0) {
			logger.debug("No version to delete");
			return;
		}

		Connection connection;
		IDataSource dataSource = instance.getDataSource();

		try {
			logger.debug("Getting the connection to DB");
			connection = dataSource.getConnection();
		} catch (Exception e) {
			logger.error("Error in opening connection to datasource " + dataSource.getLabel());
			throw new SpagoBIRuntimeException("Error in opening connection to datasource " + dataSource.getLabel(), e);
		}

		try {
			logger.debug("Deleting versions " + versionIds);
			versionDAO.deleteVersions(connection, versionIds);
			logger.debug("Reload Model");
			new ModelUtilities().reloadModel(instance, instance.getPivotModel());

		} catch (Exception e) {
			logger.error("Error deleting the versions " + versionIds, e);
			throw new SpagoBIEngineRestServiceRuntimeException("versionresource.generic.error", instance.getLocale(), e);
		} finally {
			deleteAction.stop();
			logger.debug("Closing the connection");
			try {
				connection.close();
			} catch (SQLException e) {
				logger.error("Error closing the connection to the db");
				throw new SpagoBIEngineRestServiceRuntimeException(instance.getLocale(), e);
			}
			logger.debug("connection closed");
		}

	}

	public static Integer getActualVersion(PivotModel model, ModelConfig config) {
		logger.debug("IN");
		ChangeSlicer ph = model.getTransform(ChangeSlicer.class);
		Hierarchy hierarchy = CubeUtilities.getVersionHierarchy(model.getCube(), config);
		if (hierarchy != null) {
			List<Member> slicers = ph.getSlicer(hierarchy);
			if (slicers != null && slicers.size() > 0) {
				String name = slicers.get(0).getName();
				try {
					Integer version = new Integer(name);
					logger.debug("OUT: version" + version);
					return version;
				} catch (Exception e) {
					logger.debug("Problems getting the actual version", e);
				}
			} else {
				logger.debug("No Versionslicer found");
			}
		} else {
			logger.debug("No Version hierarchy found");
		}
		logger.debug("OUT: no version");
		return null;
	}

}
