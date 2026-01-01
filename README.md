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
        +addBalance(amount)
        +deductBalance(amount) : boolean
    }

    class Customer {
        -String address
        -String phoneNumber
        +Customer(...)
    }

    class MenuItem {
        -String name
        -String description
        -double price
        +MenuItem(...)
    }

    class Restaurant {
        -String name
        -List~MenuItem~ menu
        +addMenuItem(item)
        +getMenu()
    }

    class Order {
        -Customer customer
        -List~MenuItem~ items
        -PaymentMethod paymentMethod
        +addItem(item)
        +calculateTotal() : double
        +completeOrder()
        +printReceipt()
    }

    class CashPayment {
        +pay(amount) : boolean
    }

    class CreditCardPayment {
        -String cardNumber
        +pay(amount) : boolean
        -isValidLuhn(cardNo) : boolean
    }

    class FileHelper {
        +loadUsers() : List~User~
        +saveUser()
        +loadMenu() : List~MenuItem~
    }

    class Main {
        +main()
        +login()
        +register()
    }

    %% --- İLİŞKİLER (RELATIONSHIPS) ---

    %% 1. KALITIM (Inheritance)
    Customer --|> User : Extends (Kalıtım)

    %% 2. POLİMORFİZM (Realization / Interface)
    %% Kesik çizgili oklar Polimorfizmi temsil eder
    MenuItem ..|> Orderable : Implements
    CashPayment ..|> PaymentMethod : Implements
    CreditCardPayment ..|> PaymentMethod : Implements

    %% 3. BİRE-ÇOK İLİŞKİLER (One-to-Many)
    %% "1" restoranın "*" (çok) yemeği olur.
    Restaurant "1" *-- "0..*" MenuItem : Composition (Menü)
    
    %% "1" siparişin "*" (çok) yemeği olur.
    Order "1" o-- "1..*" MenuItem : Aggregation (Sepet)

    %% "1" Müşterinin "Çok" Siparişi olabilir (Sistemde tutulmasa da mantıken)
    Order "0..*" --> "1" Customer : Belongs to

    %% 4. STRATEJİ (Polimorfizm Kullanımı)
    %% Sipariş, 1 tane ödeme yöntemi kullanır (Interface üzerinden)
    Order --> "1" PaymentMethod : Uses (Polimorfizm)

    %% 5. DİĞER BAĞLANTILAR
    Main --> Restaurant : Manages
    Main --> User : Manages
    Main --> FileHelper : Uses
    Main ..> Order : Creates
