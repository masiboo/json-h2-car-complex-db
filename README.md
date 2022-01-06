# json-h2-car-complex-db

It has complex Json data structure  

Model following table relationship 

Warehouse has one-to-one relation with Car
Warehouse has one-to-one relation with Location

Car has one-to-many relationship with Vehicle 
Vehicle has many-to-one relationship with Car.

It has 2 endpoints  GET and POST

For the GET try
http://localhost:8080/api/v1/revisions/warehouse

For the POST try
http://localhost:8080/api/v1/revisions/save   
With json body:-   
{

                "name": "Warehouse test",
                "location": {
                  "lat": "47.13111",
                  "long": "-61.54801"
                },
                "cars": {
                  "location": "West wing test",
                  "vehicles": [
                    {
                      "make": "Volkswagen test",
                      "model": "Jetta III test",
                      "year_model": 1995,
                      "price": 12947.52,
                      "licensed": true,
                      "date_added": "2018-09-18"
                    },
                    {

                      "make": "Chevrolet test",
                      "model": "Corvette test",
                      "year_model": 2004,
                      "price": 20019.64,
                      "licensed": true,
                      "date_added": "2018-01-27"
                    }
                  ]
                }
}  

Also A and B are defined  with one-to-one relationship
