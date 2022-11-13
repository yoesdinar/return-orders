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
  "emailAddress": "string"
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
  "emailAddress": "string",
  "token": "string",
  "itemIds": "[number]" 
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

# EXAMPLE

## pending
POST: localhost:8080/pending/returns
```json
{
  "orderId": "RK-478",
  "emailAddress": "john@example.com"
}
```

## create return order
POST: localhost:8080/returns
```json
{
  "orderId": "RK-478",
  "emailAddress": "john@example.com",
  "token": "DEFAULT_TOKEN",
  "items": [123]
}

```

## get return order by id
GET: localhost:8080/returns/1

## update status for item
PUT: localhost:8080/returns/1/items/123/qc/REJECTED