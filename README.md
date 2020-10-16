## Online Pharmacy
This project represents the online pharmacy.  

Main entities are User, Operation, Product. User can have many credit cards and one role. User can perform operations such as procurement and order.  

Every operation contains list of operation positions. Operation position contains information about product and its quantity.
Procurement adds positions to stock. Order takes positions from stock.  

```mermaid
classDiagram
User "1" <-- "many" CreditCard
User "1" <-- "1" Role
User "1" <-- "many" Operation
Operation <|-- Order
Operation <|-- Procurement
Position <|-- OperationPosition
Operation "1" *-- "many" OperationPosition
Product "1" *-- "1" Position
OperationPosition "1" o-- "1" Recipe
Product "1" *-- "1" Recipe
ProductCategory "1" o-- "many" Product
Procurement *-- ProcurementStatus
Order *-- OrderStatus

  class User{

  }
  class CreditCard{
  }
  class Role{
    <<enumeration>>
  }
  class Operation{
  <<abstract>>
  }
  class Order{
  }
  class OrderStatus{
      <<enumeration>>
  }
  class Procurement{
  }
   class ProcurementStatus{
      <<enumeration>>
  }
  class Position{
  }
  class OperationPosition{
  }
  class Recipe{
  }
  class Product{
  }
  class ProductCategory{
  }

            
```