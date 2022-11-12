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
  "token": "string"
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
  "itemIds": "[string]"
}
```

- Response
```json
{
  "code": "string",
  "returnId": "string",
  "refundAmount": "number",
  "items": [
    {
        "id": "string",
        "itemName": "string",
        "quantity": "number",
        "price": "number"
    },
    {
        "id": "string",
        "itemName": "string",
        "quantity": "number",
        "price": "number"
    }
  ]
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
  "code": "string",
  "returnId": "string",
  "refundAmount": "number",
  "status": "string",
  "items": [
    {
        "id": "string",
        "itemName": "string",
        "quantity": "number",
        "price": "number",
        "qcStatus": "string"
    },
    {
        "id": "string",
        "itemName": "string",
        "quantity": "number",
        "price": "number",
        "qcStatus": "string"
    }
  ]
}
```

## Update QC Status of Item Return
Request:
- Method: PUT
- EndPoint: /returns/:id/items/:itemId/qc/status
- Header:
  - Content-Type: application/json
  - Accept: application/json

- Body
```json
{
  "qcStatus": "string"
}
```

- Response
```json
{
  "code": "string",
  "message": "string",
  "item": {
        "id": "string",
        "itemName": "string",
        "quantity": "number",
        "price": "number",
        "qcStatus": "string"
    }
}
```