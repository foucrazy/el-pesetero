API JSON de El Pesetero
=======================

* Si queremos un resumen de la información de usuario vale con invocar la URL de mostrado de usuario: /user/show/1.json . El 1 se sustituirá con el id del usuario actual. Esto devolverá algo como:
{
    "topCategories": [
        {
            "name": "Vivienda",
            "subCategories": [
                {
                    "subCategories": [ ],
                    "name": "Alquiler/Hipoteca"
                }
            ]
        }
    ],
    "mail": "kortatu@gmail.com",
    "funds": [
        {
            "class": "es.elpesetero.Fund",
            "id": 9,
            "name": "Efectivo",
            "quantity": 2,
            "type": {
                "enumType": "es.elpesetero.FundType",
                "name": "cash"
            },
            "user": {
                "class": "User",
                "id": 7
            }
        },
        {
            "class": "es.elpesetero.Fund",
            "id": 13,
            "name": "Cuenta COMPARTIDA",
            "quantity": 5370,
            "type": {
                "enumType": "es.elpesetero.FundType",
                "name": "bankAccount"
            },
            "user": {
                "class": "User",
                "id": 7
            }
        },
        {
            "class": "es.elpesetero.Fund",
            "id": 12,
            "name": "Cuenta NOMINA",
            "quantity": 2995,
            "type": {
                "enumType": "es.elpesetero.FundType",
                "name": "bankAccount"
            },
            "user": {
                "class": "User",
                "id": 7
            }
        }
    ],
    "lastExpenses": [
        {
            "userId": 7,
            "expenseDate": "2012-11-08T00:00:00Z",
            "quantity": 1,
            "proRateType": null,
            "fromId": 9,
            "from": {
                "class": "es.elpesetero.Fund",
                "id": 9,
                "name": "Efectivo",
                "quantity": 2,
                "type": {
                    "enumType": "es.elpesetero.FundType",
                    "name": "cash"
                },
                "user": {
                    "class": "User",
                    "id": 7
                }
            },
            "name": "Gasto tonto",
            "categoryId": 8,
            "category": {
                "name": "Vivienda",
                "subCategories": [
                    {
                        "subCategories": [ ],
                        "name": "Alquiler/Hipoteca"
                    }
                ]
            },
            "class": "es.elpesetero.ExpenseLine"
        },
        {
            "userId": 7,
            "expenseDate": "2012-11-15T00:00:00Z",
            "quantity": 10,
            "proRateType": null,
            "from": {
                "class": "es.elpesetero.Fund",
                "id": 13,
                "name": "Cuenta COMPARTIDA",
                "quantity": 5370,
                "type": {
                    "enumType": "es.elpesetero.FundType",
                    "name": "bankAccount"
                },
                "user": {
                    "class": "User",
                    "id": 7
                }
            },
            "category": {
                "subCategories": [ ],
                "name": "Alquiler/Hipoteca"
            },
            "name": "Galletas",
            "fromId": 13,
            "categoryId": 10,
            "class": "es.elpesetero.ExpenseLine"
        }
    ],
    "totalBalance": 8367,
    "username": "kortatu"

}
