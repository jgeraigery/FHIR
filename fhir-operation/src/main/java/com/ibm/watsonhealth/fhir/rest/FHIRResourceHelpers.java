/**
 * (C) Copyright IBM Corp. 2017,2018,2019
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.ibm.watsonhealth.fhir.rest;

import javax.ws.rs.core.MultivaluedMap;

import com.ibm.watsonhealth.fhir.model.Bundle;
import com.ibm.watsonhealth.fhir.model.Resource;
import com.ibm.watsonhealth.fhir.operation.context.FHIROperationContext;
import com.ibm.watsonhealth.fhir.persistence.FHIRPersistenceTransaction;

/**
 * This interface describes the set of helper methods from the FHIR REST layer that are used by custom operation
 * implementations.
 */
public interface FHIRResourceHelpers {

    /**
     * Performs the heavy lifting associated with a 'create' interaction.
     * @param resource the Resource to be stored
     * @return a FHIRRestOperationResponse object containing the results of the operation
     * @throws Exception
     */
    public FHIRRestOperationResponse doCreate(String type, Resource resource, String ifNoneExist) throws Exception;

    /**
     * Performs an update operation (a new version of the Resource will be stored).
     * @param type the type of the resource to be updated
     * @param id the id of the Resource being updated
     * @param newResource the new resource to be stored
     * @param ifMatchValue an optional "If-Match" header value to request a version-aware update
     * @param searchQueryString an optional search query string to request a conditional update
     * @return a FHIRRestOperationResponse that contains the results of the operation
     * @throws Exception
     */
    public FHIRRestOperationResponse doUpdate(String type, String id, Resource newResource, String ifMatchValue, String searchQueryString) throws Exception;

    /**
     * Performs a 'delete' operation on the specified resource.
     * @param type the resource type associated with the Resource to be deleted
     * @param id the id of the Resource to be deleted
     * @return a FHIRRestOperationResponse that contains the results of the operation
     * @throws Exception
     */
    public FHIRRestOperationResponse doDelete(String type, String id, String searchQueryString) throws Exception;

    /**
     * Performs a 'read' operation to retrieve a Resource.
     * @param type the resource type associated with the Resource to be retrieved
     * @param id the id of the Resource to be retrieved
     * @return the Resource
     * @throws Exception
     */
    public Resource doRead(String type, String id, boolean throwExcOnNull, boolean includeDeleted) throws Exception;

    /**
     * Performs a 'vread' operation by retrieving the specified version of a Resource.
     * @param type the resource type associated with the Resource to be retrieved
     * @param id the id of the Resource to be retrieved
     * @param versionId the version id of the Resource to be retrieved
     * @return the Resource
     * @throws Exception
     */
    public Resource doVRead(String type, String id, String versionId) throws Exception;

    /**
     * Performs the work of retrieving versions of a Resource.
     * 
     * @param type
     *            the resource type associated with the Resource to be retrieved
     * @param id
     *            the id of the Resource to be retrieved
     * @param queryparameters
     *            a Map containing the query parameters from the request URL
     * @return a Bundle containing the history of the specified Resource
     * @throws Exception
     */
    public Bundle doHistory(String type, String id, MultivaluedMap<String, String> queryParameters, String requestUri) throws Exception;

    /**
     * Performs heavy lifting associated with a 'search' operation.
     * @param type the resource type associated with the search
     * @param queryParameters a Map containing the query parameters from the request URL
     * @return a Bundle containing the search result set
     * @throws Exception
     */
    public Bundle doSearch(String type, String compartment, String compartmentId, MultivaluedMap<String, String> queryParameters, String requestUri)
        throws Exception;

    
    /**
     * Helper method which invokes a custom operation.
     * @param operationContext the FHIROperationContext associated with the request
     * @param resourceTypeName the resource type associated with the request
     * @param logicalId the resource logical id associated with the request
     * @param versionId the resource version id associated with the request
     * @param operationName the name of the custom operation to be invoked
     * @param resource the input resource associated with the custom operation to be invoked
     * @return a Resource that represents the response to the custom operation
     * @throws Exception
     */
    public Resource doInvoke(FHIROperationContext operationContext, String resourceTypeName, String logicalId, String versionId, String operationName,
        Resource resource) throws Exception;

    /**
     * Processes a bundled request (batch or transaction type).
     * 
     * @param bundle
     *            the request Bundle
     * @return the response Bundle
     */
    public Bundle doBundle(Bundle bundle) throws Exception;
    
    public FHIRPersistenceTransaction getTransaction() throws Exception;
}