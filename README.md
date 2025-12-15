# OnlineFoodOrderSystem



\## Proje Tasarımı (UML)



```mermaid

classDiagram

&nbsp;   %% İlişkiler

&nbsp;   User <|-- Customer : Inheritance

&nbsp;   Orderable <|.. Order : Realization

&nbsp;   Customer "1" --> "\*" Order : Association

&nbsp;   Order \*-- MenuItem : Composition



&nbsp;   %% Sınıflar

&nbsp;   class User {

&nbsp;       -String name

&nbsp;       -String email

&nbsp;       +User(name, email)

&nbsp;   }



&nbsp;   class Customer {

&nbsp;       -String address

&nbsp;       -String phone

&nbsp;       +Customer(name, email, address, phone)

&nbsp;       +placeOrder() void

&nbsp;   }



&nbsp;   class Orderable {

&nbsp;       <<interface>>

&nbsp;       +placeOrder() void

&nbsp;       +calculateTotal() double

&nbsp;   }



&nbsp;   class Order {

&nbsp;       -List items

&nbsp;       -Customer customer

&nbsp;       +addItem(MenuItem)

&nbsp;       +calculateTotal() double

&nbsp;   }



&nbsp;   class MenuItem {

&nbsp;       -String name

&nbsp;       -double price

&nbsp;       +MenuItem(name, price)

&nbsp;       +getPrice() double

&nbsp;   }

'''

