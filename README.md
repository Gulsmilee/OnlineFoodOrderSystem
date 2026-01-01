# ONLINE FOOD ORDERING SYSTEM

## Proje Tasarımı (UML)

```mermaid
classDiagram
    %% --- Interfaces ---
    class Orderable {
        <<interface>>
        +getPrice() : double
        +getName() : String
    }

    class PaymentMethod {
        <<interface>>
        +pay(amount: double) : boolean
    }

    %% --- Classes ---
    class User {
        -String id
        -String name
        -String email
        -String password
        -double balance
        +addBalance(amount: double)
        +deductBalance(amount: double) : boolean
        +getId() : String
        +getName() : String
    }

    class Customer {
        -String address
        -String phoneNumber
        +Customer(id, name, email, pass, balance, addr, phone)
        +toString() : String
    }

    class MenuItem {
        -String name
        -String description
        -double price
        +MenuItem(name, description, price)
        +toString() : String
    }

    class Restaurant {
        -String name
        -List~MenuItem~ menu
        +addMenuItem(item: MenuItem)
        +getMenu() : List~MenuItem~
    }

    class Order {
        -Customer customer
        -List~MenuItem~ items
        -PaymentMethod paymentMethod
        +addItem(item: MenuItem)
        +calculateTotal() : double
        +setPaymentMethod(pm: PaymentMethod)
        +completeOrder()
        +printReceipt()
    }

    class CashPayment {
        +pay(amount: double) : boolean
    }

    class CreditCardPayment {
        -String cardNumber
        -String cvv
        +CreditCardPayment(cardNo, cvv)
        +pay(amount: double) : boolean
        -isValidLuhn(cardNo: String) : boolean
    }

    class FileHelper {
        +loadUsers(fileName: String) : List~User~
        +saveUser(fileName: String, user: User)
        +loadMenu(fileName: String) : List~MenuItem~
    }

    class Main {
        +main(args: String[])
        +login(email, pass) : User
        +register()
        +showMenu()
        +placeOrder()
        +balanceOperations()
    }

    %% --- Relationships ---
    %% Inheritance
    Customer --|> User : Extends

    %% Realization (Interface Implementation)
    MenuItem ..|> Orderable : Implements
    CashPayment ..|> PaymentMethod : Implements
    CreditCardPayment ..|> PaymentMethod : Implements

    %% Associations & Composition
    Restaurant *-- MenuItem : Contains (Menu)
    Order o-- MenuItem : Aggregates
    Order --> Customer : Belongs to
    Order --> PaymentMethod : Uses Strategy
    
    %% Main Dependencies
    Main --> Restaurant : Manages
    Main --> User : Manages
    Main --> FileHelper : Uses
    Main --> Order : Creates
