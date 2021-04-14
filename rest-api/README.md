# Rest API
The source code of the Rest API is in this directory.

## Endpoints
### The only Endpoint
URL: `/v1/`
#### Parameters:

| Parameter | Default Value | Optional | Possible Values |
| --------- | ------------- | -------- | --------------- |
| format    | message       | ✅       | message, format |

#### Examples:
`/v1/?format=message`
````json
{
  "message": "Java 8 is 7 years and 27 days old today"
}
````
`/v1/?format=period`
````json
{
  "years": 7,
  "days": 27,
  "hours": 11,
  "minutes": 45,
  "seconds": 31,
  "nanoseconds": 32900100
}
````
