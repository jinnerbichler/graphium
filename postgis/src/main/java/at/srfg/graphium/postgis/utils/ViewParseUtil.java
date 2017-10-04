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
package at.srfg.graphium.postgis.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import at.srfg.graphium.core.service.impl.GraphReadOrder;
import at.srfg.graphium.model.IBaseSegment;
import at.srfg.graphium.model.ISegmentXInfo;
import at.srfg.graphium.model.view.IWayGraphView;
import at.srfg.graphium.postgis.persistence.ISegmentResultSetExtractor;

/**
 * @author mwimmer
 *
 */
public class ViewParseUtil {
	
	private final static List<String> fromClausekeyWords = new ArrayList<>(Arrays.asList("INNER", "OUTER", "LEFT", "RIGHT", "FULL", "JOIN", "AS", "ON", "="));
	private final static List<String> fromClauseFinishedkeyWords = new ArrayList<>(Arrays.asList("WHERE", "GROUP", "HAVING", "WINDOW", "ORDER", "LIMIT", "OFFSET", "FETCH", "FOR"));
	private final static String GRAPHNAME_PLACEHOLDER = "%GRAPHNAME%";
	private final static String VERSION_PLACEHOLDER = "%VERSION%";
	
	public static Set<String> parseTableAliases(String filter) {
		Set<String> tableAliases = new HashSet<>();
		String[] tokens = filter.split("[ ,]");
		boolean fromClause = false;
		boolean finished = false;
		
		int i = 0;
		while (!finished && i < tokens.length) {
			if (tokens[i].toUpperCase().contains("FROM")) {
				fromClause = true;
			} else if (fromClauseFinishedkeyWords.contains(tokens[i].toUpperCase())) {
				fromClause = false;
				finished = true;
			} else {
				if (fromClause) {
					if (!fromClausekeyWords.contains(tokens[i].toUpperCase()) &&
						((i+1) == tokens.length || !tokens[i+1].toUpperCase().equals("AS")) &&
						tokens[i].length() > 0 &&
						!tokens[i].contains(".")) {
						tableAliases.add(tokens[i]);
					}
				}
			}
			i++;
		}
		
		return tableAliases;
	}

	public static String prepareViewFilterQuery(IWayGraphView view, String graphVersion, String schema, 
			ISegmentResultSetExtractor<? extends IBaseSegment, ? extends ISegmentXInfo> rsExtractor,
			GraphReadOrder order) {
		return prepareViewFilterQuery(view, graphVersion, schema, rsExtractor, order, null);
	}
	
	public static String prepareViewFilterQuery(IWayGraphView view, String graphVersion, String schema,
			ISegmentResultSetExtractor<? extends IBaseSegment, ? extends ISegmentXInfo> rsExtractor,
			GraphReadOrder order, Map<String, ? extends Object> additionalFilter) {
		
		String query = "SELECT *" + 
					(view.isWaySegmentsIncluded() ? ", st_asewkb(wayseg_geometry) AS wayseg_geometry_ewkb" : "") +
					" FROM " + schema + view.getDbViewName();
		StringBuilder filters = new StringBuilder();
		
		if (view.isWaySegmentsIncluded() && graphVersion != null) {
			filters.append("wayseg_graphversion_id = (SELECT id FROM " + schema +"waygraphmetadata WHERE graphname = '" + view.getGraph().getName() + "'" +
						   " AND version = '" + graphVersion + "')");
		}
		
		if (additionalFilter != null && !additionalFilter.isEmpty()) {
			
			for (Entry<String, ? extends Object> entry : additionalFilter.entrySet()) {
				if (filters.length() > 0) {
					filters.append(" AND ");
				}
				if (entry.getValue() instanceof Collection) {
					filters.append(entry.getKey() + " in (" + StringUtils.join((Collection) entry.getValue(), ", ") + ")");
				} else {
					filters.append(entry.getKey() + " = " + entry.getValue());
				}
			}
		}
		
		if (filters.length() > 0) {
			query += " WHERE " + filters.toString();
		}
		
		if (order != null) {
			query += " ORDER BY " + order.getAttribute() + " " + order.getOrder();
		}

		return query;
	}
	
	public static boolean hasWhereClause(String query) {
		String[] querySplit = query.split(" AS WAYSEG ");
		if (querySplit.length > 1) {
			if (querySplit[1].contains("WHERE ")) {
				return true;
			}
		}
		return false;
	}

}