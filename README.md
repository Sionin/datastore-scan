# datastore-scan
GAE datastore db scan

### Create 

POST /data?from=1&to10000

Created [1 : 10000]

### Delete

DELETE /data?from=1&to10000

Deleted [1 : 10000]

### Iterate

GET /scan?limit=1000&scrollId=CiESG2oHcmRtLWRldnIJC

{
 "scrollId" : "CiESG2oHcmRtLWRldnIJCxIDRFRPGBkMogEEdGVzdBgAIAA",
 "values" : 1000,
 "time" : 1000
}
