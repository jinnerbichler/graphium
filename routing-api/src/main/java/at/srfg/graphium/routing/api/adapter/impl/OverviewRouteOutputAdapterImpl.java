/**
 * Copyright © 2020 Salzburg Research Forschungsgesellschaft (graphium@salzburgresearch.at)
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
package at.srfg.graphium.routing.api.adapter.impl;

import java.util.Collection;

import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.operation.linemerge.LineMerger;

import at.srfg.graphium.model.IBaseWaySegment;
import at.srfg.graphium.routing.api.adapter.IRouteOutput;
import at.srfg.graphium.routing.api.adapter.IRouteOutputAdapter;
import at.srfg.graphium.routing.api.dto.impl.OverviewRouteDTOImpl;
import at.srfg.graphium.routing.model.IRoute;

public class OverviewRouteOutputAdapterImpl<T extends IBaseWaySegment>
	implements IRouteOutputAdapter<OverviewRouteDTOImpl, Float, T> {

	private IRouteOutput<OverviewRouteDTOImpl, Float> output;
	
	public OverviewRouteOutputAdapterImpl() {
		this.output = new OverviewRouteOutputImpl();
	}
	
	@Override
	public IRouteOutput<OverviewRouteDTOImpl, Float> adaptsTo() {
		return output;
	}

	@Override
	public OverviewRouteDTOImpl adapt(IRoute<T, Float> route) {
		Collection<LineString> lineStrings = null;
		if (route.getSegments() != null && !route.getSegments().isEmpty()) {
			LineMerger lineMerger = new LineMerger();
			for (T segment : route.getSegments()) {
				if (segment.getGeometry() != null) {
					lineMerger.add(segment.getGeometry());
				}
			}
			lineStrings = lineMerger.getMergedLineStrings();
		}
		
		String geometryWkt = null;
		if (lineStrings != null) {
			geometryWkt = "";
			for (LineString ls : lineStrings) {
				geometryWkt += ls.toText();
			}
		}
		
		return new OverviewRouteDTOImpl(route.getWeight(), route.getLength(), route.getDuration(), 
				route.getRuntimeInMs(), route.getGraphName(), route.getGraphVersion(), geometryWkt);
	}

}
