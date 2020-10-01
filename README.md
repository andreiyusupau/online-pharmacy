```mermaid
classDiagram
User <-- CreditCard
User <-- Role
User <-- Operation
Operation o-- Position
Position o-- Product
Position o-- Recipe
Recipe o--Product
Product o-- ProductCategory

  class User{

  }
  class CreditCard{
  }
  class Role{
  }
  class Operation{
  }
  class Position{
  }
  class Recipe{
  }
  class Product{
  }
  class ProductCategory{
  }
            
```