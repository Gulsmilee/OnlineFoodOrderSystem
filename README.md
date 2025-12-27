# ONLINE FOOD ORDERING SYSTEM

## Proje Tasarımı (UML)

```mermaid
classDiagram
    %% İlişkiler
    User <|-- Customer : Inheritance
    Orderable <|.. Order : Realization
    Customer "1" --> "*" Order : Association
    Order *-- MenuItem : Composition

    %% Sınıflar
    class User {
        -String name
        -String email
        +User(name, email)
    }

    class Customer {
        -String address
        -String phone
        +Customer(name, email, address, phone)
        +placeOrder() void
    }

    class Orderable {
        <<interface>>
        +placeOrder() void
        +calculateTotal() double
    }

    class Order {
        -List items
        -Customer customer
        +addItem(MenuItem)
        +calculateTotal() double
    }

    class MenuItem {
        -String name
        -double price
        +MenuItem(name, price)
        +getPrice() double
    }