# POST add XInfo to connections

## Resource URL

`http://localhost/graphium/api/connections/graphs/{graph}/versions/{version}/xinfos`

## Parameters

| **Attribut** | **Datentyp**   | **Beschreibung**                         |
| ------------ | -------------- | ---------------------------------------- |
| **graph**    | String         | unique graph name                        |
| **version**  | String         | unique graph version or **current** for currently active graph version |
| **file**     | Multipart File | XInfos as JSON file                      |

## Example URL

`curl -X POST "http://localhost:8080/graphium-central-server/api/connections/graphs/osm_at/versions/170828/xinfos" -F "file=@D:/graphium/json/osm_at_connections_xinfo.json"`

## Response

No Response.