# API SPECS

## POST Pending Return
Request:
- Method: POST
- EndPoint: /pending/returns
- Header:
  - Content-Type: application/json
  - Accept: application/json
- Body
```json
{
  "orderId": "string",
  "email": "string"
}
```

- Response
```json
{
  "code": "string",
  "status": "string",
  "data" : {
    "token": "string"
  }
}
```

## Create Return
Request:
- Method: POST
- EndPoint: /returns
- Header: 
  - Content-Type: application/json
  - Accept: application/json
- Body
```json
{
  "orderId": "string",
  "token": "string",
  "itemIds": "[string]" 
}
```
itemIds: list of item id if we want to do partial return

- Response
```json
{
  "code": "number",
  "status": "string",
  "data": {
    "returnOrderId": "number",
    "refundAmount": "number"
  }
}
```

## Get Return
Request:
- Method: GET
- EndPoint: /returns/:id
- Header:
    - Content-Type: application/json
    - Accept: application/json

- Response
```json
{
  "code": "number",
  "status": "OK",
  "data": {
    "refundAmount": "number",
    "status": "string",
    "items": [
      {
        "itemId": "number",
        "quantity": "number",
        "qcStatus": "string",
        "price": "number"
      },
      {
        "itemId": "number",
        "quantity": "number",
        "qcStatus": "string",
        "price": "number"
      }
    ]
  }
}
```

## Update QC Status of Item Return
Request:
- Method: PUT
- EndPoint: /returns/:id/items/:itemId/qc/:status
- Header:
  - Content-Type: application/json
  - Accept: application/json

- Response

- status is either: ACCEPTED or REJECTED
```json
{
  "code": "number",
  "status": "string",
  "data": "string"
}
```