/**
 * Copyright © 2017 Salzburg Research Forschungsgesellschaft (graphium@salzburgresearch.at)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * (C) 2013 Salzburg Research Forschungsgesellschaft m.b.H.
 *
 * All rights reserved.
 *
 * @author anwagner
 **/
package at.srfg.graphium.core.persistence;

import java.util.List;

import at.srfg.graphium.model.IWaySegment;

public interface ICompositeWayGraphWriteDao<T extends IWaySegment> extends IWayGraphWriteDao<T> {

	public void setGraphWriteDaos(List<IWayGraphWriteDao<T>> writeDaos);
	public List<IWayGraphWriteDao<T>> getGraphWriteDaos();
}
