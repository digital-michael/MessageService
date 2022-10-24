package org.cplabs.messageservice.messaging;

/**
 * Package "messaging" contains all the implementation for the Message domain. Most entities are package public
 * and not intended for access outside the "messaging" package.
 *
 * MessageController is the primary API interface for Message business logic but only managing RESTful API details
 * and calls to MessageManager.  No business logic should be placed in a RESTful controller (separation of concerns.)
 *
 * The MessageManager is public and available for access outside the "messaging" package. It contains the business logic
 * and is intended to both server the MessageController requests, be a logical place for unit, integration, application
 * testing and SDK development.  The goal of this arrangement is other "manager" classes can orchestrate with the
 * business logic of MessageManager without having to go through the RESTful API of hunt across other elements in an
 * uncontrolled fashion.
 *
 * This package uses encapsulates all Message-domain elements (IE does not spread controllers, services, repositories
 * and other components across multiple packages for artificial reasons.) The design principle: be able to remove
 * package "messaging" with one key press with zero to low impact on other API domains.
 *
 * Highlights:
 * - MessageManager.java: contains all business logic this domain is intended to expose
 * - test/java/CLI.java: quick "smoketest" of a workflow to test the MessageServiceApplication instance
 * - RecordMessage.java: using record type for managing presentation data
 *
 *
 * TODO:
 * - Minor: using the file-backed H2 does not work the same as the embedded version, failing to create the Messages Table.
 *      Probably Fix: use resources/data.sql to create table
 * - Trivial: JPA does not support Java entity "record" types. As such, RecordMessage contains factor methods
 *      create Message (class) and RecordMessage (record) instances
 * - Research: using a single package for a domain, seems like could collapes the need for an interface and implementation
 *      for the Repository's Service implementation.
 * - Research: determine if JPA has been replaced but more modern thinking on the subject
 */