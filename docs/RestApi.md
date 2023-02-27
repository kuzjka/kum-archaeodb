# REST API

## Common objects

##### Page object

Page object is usually returned for table views with paginations. Type of the content is specific for each request. Page object adds total count of the elements and pages to the returned list.

|Field|Type|Description|
|---|---|---|
|**totalCount**|number|Total number of elements|
|**totalPages**|number|Total number of pages for current settings|
|**content**|Array<?>|List of elements for this page|

##### Location

Represents geographic coordinates

|Field|Type|Description|
|---|---|---|
|**latitude**|number|Latitude, degrees|
|**longitude**|number|Longitude, degrees|

## Items

### GET /api/items

Gets a list of items with pagination

#### Request

##### Query parameters

|Parameter|Type|Description|
|---|---|---|
|**page**|number|Page number|
|**size**|number|Number of items on a page|
|**categories**|string|Comma-separated list of categories to filter. Optional, if omitted, no filtering is made.|
|**sort**|string|Field to sort by. Optional, default sort is by point number, ascending|
|**order**|`asc` \| `desc`|Sort order: ascending or descending. Optional, default order is ascending|

#### Response

**Content-Type: application/json**

Returns a [Page](#page-object) of items.

##### Item

|Field|Type|Description|
|---|---|---|
|**name**|string|Item name|
|**year**|number|Year of the aquisition|
|**pointNumber**|string|Point number (inventory list number)|
|**hectare**|number|Number of the hectare|
|**depth**|number|Discovery depth in cm|
|**location**|[Location](#location)|Geographic coordinates of the discovery point|
|**description**|string|Item description|
|**material**|string|Item material|
|**technique**|string|Item manufacturing technique|
|**condition**|string|Item condition|
|**dimensions**|string|Item dimensions in mm|
|**weight**|number|Item weight in grams|
|**category**|string|Item category|
|**remarks**|string|Remarks|
|**context**|number|Item collocation context number|
|**image**|string|Number of the figure in the official report|
|**museumNumber**|string|Museum number of the item|
|**gpsPoint**|string|Service name of the GPS point|

##### Item additional fields (bullets only)

|Field|Type|Description|
|---|---|---|
|**caliber**|number|Bullet caliber|
|**approximate**|boolean|Bullet caliber is approximate|
|**standard**|string|Bullet manufacture 'standard' (cossacks/poland/etc.)|
|**deformation**|`NONE` \| `LIGHT` \| `HARD`|Level of deformation (NONE/LIGHT/HARD)|
|**imprints**|boolean|Shows if a bullet has imprints of collision|

### POST /api/items/parse

Uploads a CSV file with item list for parsing and returns preliminary parsing result for verification.

#### Request

**Content-Type: application/x-www-form-urlencoded**

This variant is used for copy&paste from Excel

##### Query parameters

|**delimiter**|string|Delimiter char|
|**hasHeaders**|boolean|Indicates if the content has a header row|
|**content**|string|CSV content of the file|

**Content-Type: multipart/form-data**

This variant is used to upload CSV files

##### Query parameters

|**delimiter**|string|Delimiter char|
|**hasHeaders**|boolean|Indicates if the content has a header row|
|**content**|file|CSV file|

#### Response

**Content-Type: application/json**

Returns an array of ItemParsing objects

##### ItemParsing

|Field|Type|Description|
|---|---|---|
|**name**|string|Item name|
|**pointNumber**|string|Point number (inventory list number)|
|**location**|[Location](#location)|Item discovery location|
|**hectare**|number|Hectare number (if autodetected)|
|**dimensions**|string|Item dimensions, mm|
|**weight**|number|Item weight, g|
|**remarks**|string|Remarks|
|**gpsPoint**|string|Service name of the GPS point|
|**category**|string|Item category (if autodetected)|
|**caliber**|number|Bullet caliber, parsed or autodetected (bullets only)|
|**caliberApproximate**|boolean|Indicates if caliber is approximate|
|**deformation**|`NONE` \| `LIGHT` \| `HARD`|Level of deformation (bullets only, autodetected)|
|**numberExists**|boolean|`true` indicates error that point number already exists|
|**categoryAutodetected**|boolean|Shows if the category was autodetected|
|**caliberAutodetected**|boolean|Shows if the caliber was autodetected (bullets only)|
|**deformationAutodetected**|boolean|Shows if the deformation level was autodetected (bullets only)|
|**hectareAutodetected**|boolean|Shows if the hectare number was autodetected|
|**save**|boolean|Defines if this item should be saved|

### POST /api/items/addParsed

Adds parsed and verified items.

#### Request

**Content-Type: application/json**

Request body is an array of [ItemParsing](#itemparsing) objects (see above)

#### Response

HTTP 200 (empty)

#### Error response

HTTP 400 if request body does not meet object schema specifications
HTTP 500 in case of server error

|Field|Type|Description|
|---|---|---|
|message|string|Error message|