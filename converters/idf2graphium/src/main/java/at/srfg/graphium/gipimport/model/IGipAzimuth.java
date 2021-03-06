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
package at.srfg.graphium.gipimport.model;

/**
 * Created by shennebe on 27.04.2015.
 */
public interface IGipAzimuth {

    double getAzimuthTo();

    void setAzimuthTo(double azimuthTo);

    long getNodeId();

    void setNodeId(long nodeId);


    IGipLink getGipLinkFrom();

    void setGipLinkFrom(IGipLink gipLinkFrom);

    IGipLink getGipLinkTo();

    void setGipLinkTo(IGipLink gipLinkTo);

    double getAzimuthFrom();

    void setAzimuthFrom(double azimuth);

    double getReduceFactor();

    void setReduceFactor(double reduceFactor);
}
