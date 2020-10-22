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
   long id;
    String firstName;
    String middleName;
    String lastName;
    LocalDate dateOfBirth;
   String email;
     String password;
     Role role;
  }
  class CreditCard{
   long id;
     String cardNumber;
     String ownerName;
     LocalDate validThru;
     int cvv;
     User owner;
  }
  class Role{
    <<enumeration>>
       CONSUMER,
    MODERATOR,
    PROCUREMENT_SPECIALIST,
    ADMINISTRATOR
  }
  class Operation{
  <<abstract>>
   long id;
     Instant date;
     User owner;
  }
  class Order{
   OrderStatus status;
  }
  class OrderStatus{
      <<enumeration>>
      PREPARATION,
    PAID,
    IN_PROGRESS,
    COMPLETE,
    CANCELED
  }
  class Procurement{
   ProcurementStatus procurementStatus;
  }
   class ProcurementStatus{
      <<enumeration>>
       PREPARATION,
    APPROVED,
    PAID,
    COMPLETE,
    CANCELED
  }
  class Position{
   long id;
     int quantity;
     Product product;
  }
  class OperationPosition{
   Operation operation;
  }
  class Recipe{
   long id;
     String description;
     int quantity;
     Product product;
     Instant validThru;
     OperationPosition operationPosition;
  }
  class Product{
   long id;
     String name;
     BigDecimal price;
     ProductCategory productCategory;
     boolean recipeRequired;
  }
  class ProductCategory{
       long id;
     String name;
     String description;
  }

            
```