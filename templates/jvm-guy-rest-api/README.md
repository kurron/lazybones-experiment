#Overview
Magni provides a temporary store for binary assets.  The idea is that Magni will be used as a
'post office box' that coordinating applications can use to drop off and pick up assets.

#Push-E Deployment Notes
Magni is a JDK 8 based application and has been tested against version 1.8.0_31 under Ubuntu Linux 14.04.  The recommended launch command
is `java -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:FlightRecorderOptions=dumponexit=true,defaultrecording=true,dumponexitpath=/<some filder>/magni.jfr -Dspring.profiles.active=test -server -jar magni-1.2.3.RELEASE.jar`.
This will enable the Java Flight Recorder and provide us with information in the event of a crash. It also activates the `test` profile,
using resources on the test network.

#Launch Scripts
As a convenience, there are some scripts to launch Magni from the command line.  Typical usage is:
`bin/launch-test.sh`.  There is a launch script for each profile that Magni supports.

#Requirements

##REST client
* has the option of asking for the data to be represented as either JSON or XML using content negotiation
* is synchronous and cannot wait around for more than one-half second for the service to return
* should be returned data even if a failure with the data source occurs -- the data should contain a failure indicator
* hypermedia control should be used to guide the caller to the next available states

##Magni Media Type

The Magni Media Type is a JSON-based hypermedia type designed to support
the management and querying of assets stored in the Magni system.  It is
based on the [Hypertext Application Language (HAL)](http://stateless.co/hal_specification.html)
and seeks to guide the API user through the various states of the resource.  The Magni service
treats all assets as binary and does not attempt to interpret the bytes.
Once an asset is inserted into the system, it remain there until its
expiration.  Meta-data about an existing asset can obtained until the
asset's expiration.

The key words "MUST", "MUST NOT", "REQUIRED", "SHALL", "SHALL NOT", "SHOULD", "SHOULD NOT", "RECOMMENDED",
"MAY", and "OPTIONAL" in this document are to be interpreted as described in RFC 2119.

###General Concepts

The Magni hypermedia type is designed to support limited read/write capability
of assets.  The standard application semantics supported by this media
type are restricted to Create and Read. Update and Delete are not supported.

The Magni hypermedia type has a limited set of predefined link relation
values which may expand in future revisions of the control.

The following sections describe the process of reading and writing data
using the Magni hypermedia type as well as how to parse and execute
Query Templates.

###Reading and Writing Data
The Magni media type supports a limited form of read/write semantics.
Read operations are guided by the Magni media type through the use Query Templates.
These templates can be used by the client to dynamically form their queries,
allowing for future changes in the api without breaking clients.

This section describes the details of how clients can recognize and
implement reads and writes found within the Magni responses.

####Discovery

The first time a client connects to the service, it should GET the
magni resource which describes all of the operations that are currently
available.

```
*** REQUEST ***
GET /magni HTTP/1.1
Host: api.example.com
Accept: application/json;type=magni;version=1.0.0

*** RESPONSE ***
200 OK HTTP/1.1
Content-Type: application/json;type=magni;version=1.0.0
Content-Length: xxx
Allow: GET, POST
ETag: "f29e908b-e002-4447-8bc6-1e0ce27d8bfd"

{
    "_links": {
        "self": { "href": "http://api.example.com/magni", "templated": false },
        "create": { "href": "http://api.example.com/magni", "templated": false },
        "read": { "href": "http://api.example.com/magni/{asset-id}", "templated": true }
    },
    "required-http-headers" : {
        "POST" : [
            { 
                "header": "Content-Type", 
                "example": "image/png;name=cat.png" 
            },
            { 
                "header": "Content-Length", 
                "example": "2048" 
            },
            { 
                "header": "Accept", 
                "example": "application/json;type=magni" 
            },
            { 
                "header": "X-Correlation-Id", 
                "example": "de305d54-75b4-431b-adb2-eb6b9e546013" 
            },
            {
                "header": "X-Expiration-Minutes", 
                "example": "19" 
            }
        ],
        "GET" : [
            {
                "header": "Accept",
                "example": "image/png;name=cat.png"
            },
            {
                "header": "X-Correlation-Id",
                "example": "de305d54-75b4-431b-adb2-eb6b9e546013"
            }
        ]
    }
}
```

####Create
To avoid requiring mixing meta-data with the actual binary payload, Magni 
uses a combination of standard and custom HTTP headers to carry the required
information.  The Mangni media type provides a template describing the
required headers needed to add a new resource to the system.

```
*** REQUEST ***
POST /magni/ HTTP/1.1
Host: api.example.com
Accept: application/json;type=magni;version=1.0.0
Content-Type: image/png;width=1024;height:768
Content-Length: xxx
X-Correlation-Id: de305d54-75b4-431b-adb2-eb6b9e546013
X-Expiration-Minutes: 10

<the image bytes>

*** RESPONSE ***
201 Created HTTP/1.1
Location: http://api.example.com/magni/f29e908b-e002-4447-8bc6-1e0ce27d8bfd
Content-Type: application/json;type=magni;version=1.0.0
Content-Length: xxx
ETag: "f29e908b-e002-4447-8bc6-1e0ce27d8bfd"

{
    "_links": {
        "self": { "href": "http://api.example.com/magni/f29e908b-e002-4447-8bc6-1e0ce27d8bfd", "templated": false },
        "create": { "href": "http://api.example.com/magni", "templated": false }
    },
    "meta-data" : {
        "mime-type": "image/png;width=1024;height:768",
        "content-length": xxx,
        "expires": "2015-01-27T16:17:57Z"
    },
    "error": {
        "code": 9010,
        "message" : "some details about the failure"
    }
}
```
In the context of a POST, the service MUST return an entity of type `application/json;type=magni;version=1.0.0`. Other mime-types
are not supported.  In the context of a successful call, a `201 Created` status code, the entity MUST contain links to the newly
created asset and the creation resource.  The entity MUST also contain a `meta-data` attribute containing the mime-type and
content length of the newly created resource.  The `meta-data` attribute MAY also contain an ISO 8601 timestamp of when the resource
 will expire, depending on the service implementation.  The `error` attribute MUST NOT be included.

In the context of a failed call, a non-201 status code, the entity will only contain a link to the creation resource.  The
`meta-data` attribute MUST NOT be included. The `error` attribute MUST be included and MUST contain both a numeric error code and
string message explaining the service failure.

####Read (asset bytes)
Through the use of the GET verb, the service provides access to raw bytes of the asset.

```
*** REQUEST ***
GET /magni/f29e908b-e002-4447-8bc6-1e0ce27d8bfd HTTP/1.1
Host: api.example.com
Accept: image/png
X-Correlation-Id: de305d54-75b4-431b-adb2-eb6b9e546013

*** RESPONSE ***
200 Ok HTTP/1.1
Content-Type: image/png;width=1024;height:768
Content-Length: xxx
ETag: "f29e908b-e002-4447-8bc6-1e0ce27d8bfd"

<the image bytes>
```
In this example, the Magni hypermedia control is not involved in the retrieval of the images bytes and cannot guide
the caller to the next action.  Note that the `Accept` header MUST match the type and sub-type provided when the asset
was originally uploaded. A mime-type mismatch MUST result in a `406 Not Acceptable` status code being returned. In
the event of the asset having expired, a status code of `404 Not Found` will be returned but a status of `410 Gone` MAY
be returned if the server can determine that the asset cannot be located due to an expiration and not some other reason.

####Read (asset meta-data)
Through the use of the GET verb, the service provides access to asset's meta-data.

```
*** REQUEST ***
GET /magni/f29e908b-e002-4447-8bc6-1e0ce27d8bfd HTTP/1.1
Host: api.example.com
Accept: application/json;type=magni;version=1.0.0
X-Correlation-Id: de305d54-75b4-431b-adb2-eb6b9e546013

*** RESPONSE ***
200 Ok HTTP/1.1
Content-Type: application/json;type=magni;version=1.0.0
Content-Length: xxx
ETag: "f29e908b-e002-4447-8bc6-1e0ce27d8bfd"

{
    "_links": {
        "self": { "href": "http://api.example.com/magni/f29e908b-e002-4447-8bc6-1e0ce27d8bfd", "templated": false },
        "create": { "href": "http://api.example.com/magni", "templated": false }
    },
    "meta-data" : {
        "mime-type": "image/png;width=1024;height:768",
        "content-length": xxx,
        "expires": "2015-01-27T16:17:57Z"
    },
    "error": {
        "code": 9010,
        "message" : "some details about the failure"
    }
}
```
In the context of a successful call, a `200 OK` status code, the entity MUST contain links to the
created asset and the creation resource.  The entity MUST also contain a `meta-data` attribute containing the mime-type and
content length of the resource.  The `meta-data` attribute MAY also contain an ISO 8601 timestamp of when the resource
 will expire, depending on the service implementation.  The `error` attribute MUST NOT be included.

In the context of a failed call, a non-200 status code, the entity will only contain a link to the creation resource.  The
`meta-data` attribute MUST NOT be included. The `error` attribute MUST be included and MUST contain both a numeric error code and
string message explaining the service failure.