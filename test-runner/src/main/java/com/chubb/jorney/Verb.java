package com.chubb.jorney;

/**
 * This enum defines all verbs that a journey file should use.
 * <p>
 * Created by ichistruga on 10/17/2016.
 */
public enum Verb {

    /* ========================== Send request ========================== */

    /**
     * Verb for sending a REST request.
     */
    SendRestRequest,
    /**
     * Verb for sending a SOAP request.
     */
    SendSoapRequest,

    /* ========================== generic Http Response validations ========================== */

    /**
     * Verb for validating the status code from a Http response. E.g. 200, 401, etc.
     */
    ValidateResponseHttpStatusCode,
    /**
     * Verb for validating the header(name, value) from a Http response. Validates if header name exists and header value is as expected.
     * E.g.  "Content-Type": "application/json".
     */
    ValidateResponseHttpHeaderValues,
    /**
     * Verb for validating the status code and header values by template in JSON format.
     */
    ValidateResponseHttpStatusAndHeaderByTemplate,
    /**
     * Same as @see ValidateResponseHttpStatusAndHeaderByTemplate but some nodes could miss in template and the validation is ignored on missing or empty nodes.
     */
    ValidateResponseHttpStatusAndHeaderByTemplateSoftly,

    /* ========================== REST Response body validations ========================== */

    /**
     * Verb for validating REST response body by a template in JSON format.
     */
    ValidateRestResponseByTemplate,
    /**
     * Same as @see ValidateRestResponseByTemplate but some nodes could miss and the validation is ignored on missing or empty nodes.
     */
    ValidateRestResponseByTemplateSoftly,
    /**
     * Verb for validating REST response body with child nodes defined in a template in JSON format.
     * The child nodes could be defined as part of node tree separated by comma (,).
     */
    ValidateRestResponseByChildNodesTemplate,
    /**
     * Same as @see ValidateRestResponseByChildNodesTemplate but some nodes could miss and the validation is ignored on missing or empty nodes.
     */
    ValidateRestResponseByChildNodesTemplateSoftly,
    /**
     * Verb for validating REST response body fields in property access format. E.g. user.address.street
     */
    ValidateRestResponseByPath,



    /* ========================== SOAP Response body validations ========================== */

    /**
     * Verb for validating SOAP response body by a template in XML format.
     */
    ValidateSoapResponseByTemplate,
    /**
     * Same as @see ValidateSoapResponseByTemplate but some nodes could miss and the validation is ignored on missing or empty nodes.
     */
    ValidateSoapResponseByTemplateSoftly,
    /**
     * Verb for validating SOAP response body fields in property access format. E.g. user.address.street
     */
    ValidateSoapResponseByPath,

    ValidateSoapResponseByChildNodesTemplate,

    ValidateSoapResponseByChildNodesTemplateSoftly,


    /* ========================== Utils ========================== */

    /**
     * Verb for storing data in storage context in property access format. E.g. user.address.street
     */
    StoreParam


}
