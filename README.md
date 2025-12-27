# ONLINE FOOD ORDERING SYSTEM

## Proje Tasarımı (UML)

```mermaid
classDiagram
    %% Arayüzler (Interfaces)
    class Orderable {
        <<interface>>
        +getPrice() double
        +getName() String
    }

    class PaymentMethod {
        <<interface>>
        +pay(double amount) boolean
    }

    %% Ana Sınıf (Base Class - Kapsülleme içerir)
    class User {
        -String id
        -String name
        -String email
        -String password
        -String phoneNumber
        -String address
        +login() boolean
        +register() boolean
        +updateProfile() void
    }

    %% Kalıtım (Inheritance) - Customer User'dan türer
    class Customer {
        -List~Order~ orderHistory
        +viewMenu() void
        +addToCart(MenuItem item) void
        +placeOrder(Order order) void
    }

    %% Diğer Sınıflar
    class MenuItem {
        -String name
        -String description
        -double price
        -String category
        +getDetails() String
    }

    class Restaurant {
        -String name
        -double rating
        -List~MenuItem~ menu
        +addMenuItem(MenuItem item) void
        +removeMenuItem(MenuItem item) void
        +getMenu() List~MenuItem~
    }

    class Order {
        -String orderId
        -Date orderDate
        -String status
        -List~MenuItem~ items
        -double totalAmount
        -PaymentMethod paymentMethod
        +calculateTotal() double
        +completeOrder() void
    }

    %% Polimorfizm Sınıfları
    class CreditCardPayment {
        -String cardNumber
        -String cvv
        +pay(double amount) boolean
    }

    class CashPayment {
        +pay(double amount) boolean
    }

    %% İlişkiler
    User <|-- Customer : Inheritance (Kalıtım)
    Orderable <|.. MenuItem : Implements (Arayüz)
    PaymentMethod <|.. CreditCardPayment : Implements
    PaymentMethod <|.. CashPayment : Implements
    
    Restaurant "1" *-- "*" MenuItem : Composition
    Customer "1" --> "*" Order : Creates
    Order "*" o-- "*" MenuItem : Aggregation
    Order "1" --> "1" PaymentMethod : Uses (Polimorfizm)
